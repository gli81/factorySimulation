import React from 'react';
import styled from 'styled-components';

const StyledButton = styled.button`
  background-color: #4a90e2;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  margin: 10px;
  
  &:hover {
    background-color: #357ae8;
  }
`;

const RequestButton = ({ onClick, label }) => {
  return (
    <StyledButton onClick={onClick}>
      {label || 'Request Item'}
    </StyledButton>
  );
};

export default RequestButton;