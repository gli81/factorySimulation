// src/components/ConnectionButton.jsx
import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`
  background-color: #27ae60;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  margin: 10px;
  
  &:hover {
    background-color: #219653;
  }
`;

const ConnectionButton = ({ onClick, label }) => {
  return (
    <StyledButton onClick={onClick}>
      {label || 'Connect Buildings'}
    </StyledButton>
  );
};

export default ConnectionButton;