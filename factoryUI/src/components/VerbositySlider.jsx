// src/components/VerbositySlider.jsx
import React, { useState } from 'react';
import styled from 'styled-components';

const SliderContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 10px;
  width: 200px;
`;

const SliderLabel = styled.label`
  font-size: 14px;
  margin-bottom: 5px;
  color: #333;
`;

const SliderControls = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`;

const StyledSlider = styled.input`
  flex-grow: 1;
  height: 5px;
  background: #ddd;
  outline: none;
  -webkit-appearance: none;
  
  &::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 15px;
    height: 15px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
  }
  
  &::-moz-range-thumb {
    width: 15px;
    height: 15px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
  }
`;

const SliderValue = styled.span`
  font-size: 14px;
  min-width: 20px;
  text-align: center;
  color: black; /* Changed to black as requested */
`;

const VerbositySlider = ({ onChange }) => {
  const [verbosity, setVerbosity] = useState(0);
  
  const handleChange = (e) => {
    const value = parseInt(e.target.value);
    setVerbosity(value);
    onChange(value);
  };
  
  return (
    <SliderContainer>
      <SliderLabel>Verbosity Level</SliderLabel>
      <SliderControls>
        <StyledSlider 
          type="range" 
          min="0" 
          max="2" 
          step="1" 
          value={verbosity}
          onChange={handleChange}
        />
        <SliderValue>{verbosity}</SliderValue>
      </SliderControls>
    </SliderContainer>
  );
};

export default VerbositySlider;