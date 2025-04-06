// src/components/OutputConsole.jsx
import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';

const ConsoleContainer = styled.div`
  margin-top: 20px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #f8f8f8;
  height: 200px;
  overflow-y: auto;
  padding: 10px;
  font-family: monospace;
  text-align: left;
  color: #333;
`;

const ConsoleMessage = styled.div`
  margin-bottom: 5px;
  color: ${props => {
    switch(props.type) {
      case 'error': return '#e74c3c';
      case 'success': return '#2ecc71';
      case 'info': return '#3498db';
      default: return '#333';
    }
  }};
`;

const OutputConsole = ({ messages = [] }) => {
  const consoleRef = useRef(null);
  
  // Auto-scroll to bottom when new messages arrive
  useEffect(() => {
    if (consoleRef.current) {
      consoleRef.current.scrollTop = consoleRef.current.scrollHeight;
    }
  }, [messages]);
  
  return (
    <ConsoleContainer ref={consoleRef}>
      {messages.length === 0 ? (
        <ConsoleMessage>No output to display. Try running a command.</ConsoleMessage>
      ) : (
        messages.map((message, index) => (
          <ConsoleMessage key={index} type={message.type}>
            {message.text}
          </ConsoleMessage>
        ))
      )}
    </ConsoleContainer>
  );
};

export default OutputConsole;