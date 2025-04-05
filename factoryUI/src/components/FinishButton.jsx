// src/components/FinishButton.jsx
import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`
  background-color: #e74c3c;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  margin: 10px;
  
  &:hover {
    background-color: #c0392b;
  }
`;

const FinishButton = ({ onClick }) => {
  return (
    <StyledButton onClick={onClick}>
      Finish
    </StyledButton>
  );
};

export default FinishButton;