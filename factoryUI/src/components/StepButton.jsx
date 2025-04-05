// src/components/StepButton.jsx
import React, { useState } from 'react';
import styled from 'styled-components';

const StepContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 10px;
`;

const StyledButton = styled.button`
  background-color: #4a90e2;
  color: white;
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  
  &:hover {
    background-color: #357ae8;
  }
`;

const StepInput = styled.input`
  width: 60px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  text-align: center;
`;

const StepButton = ({ onStep }) => {
  const [steps, setSteps] = useState(1);
  
  const handleClick = () => {
    onStep(Number(steps));
  };
  
  return (
    <StepContainer>
      <StyledButton onClick={handleClick}>
        Step
      </StyledButton>
      <StepInput 
        type="number" 
        min="1" 
        value={steps}
        onChange={(e) => setSteps(e.target.value)}
      />
    </StepContainer>
  );
};

export default StepButton;