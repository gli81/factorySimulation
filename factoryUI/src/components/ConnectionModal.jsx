// src/components/ConnectionModal.jsx
import React, { useState } from 'react';
import styled from 'styled-components';

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
`;

const ModalContent = styled.div`
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  width: 400px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
`;

const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
`;

const ModalTitle = styled.h2`
  margin: 0;
  font-size: 18px;
`;

const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 20px;
  cursor: pointer;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 15px;
`;

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

const Label = styled.label`
  font-weight: bold;
  color: #333;
`;

const Select = styled.select`
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  color: #333;
`;

const Info = styled.div`
  margin-top: 10px;
  padding: 10px;
  background-color: #f0f0f0;
  border-radius: 4px;
  color: #333;
  font-size: 14px;
`;

const ButtonGroup = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 10px;
`;

const Button = styled.button`
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  
  &.primary {
    background-color: #27ae60;
    color: white;
    border: none;
  }
  
  &.secondary {
    background-color: white;
    color: #333;
    border: 1px solid #ccc;
  }
`;

const ConnectionModal = ({ isOpen, onClose, onSubmit, buildings = [] }) => {
  const [sourceBuilding, setSourceBuilding] = useState('');
  const [targetBuilding, setTargetBuilding] = useState('');
  
  if (!isOpen) return null;
  
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(sourceBuilding, targetBuilding);
    // Reset form
    setSourceBuilding('');
    setTargetBuilding('');
    onClose();
  };
  
  return (
    <ModalOverlay onClick={onClose}>
      <ModalContent onClick={e => e.stopPropagation()}>
        <ModalHeader>
          <ModalTitle>Connect Buildings</ModalTitle>
          <CloseButton onClick={onClose}>&times;</CloseButton>
        </ModalHeader>
        
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label htmlFor="source">Source Building:</Label>
            <Select 
              id="source"
              value={sourceBuilding}
              onChange={(e) => setSourceBuilding(e.target.value)}
              required
            >
              <option value="">-- Select Source --</option>
              {buildings.map((building, index) => (
                <option key={index} value={building.name || building}>
                  {building.name || building}
                </option>
              ))}
            </Select>
          </FormGroup>
          
          <FormGroup>
            <Label htmlFor="target">Destination Building:</Label>
            <Select 
              id="target"
              value={targetBuilding}
              onChange={(e) => setTargetBuilding(e.target.value)}
              required
            >
              <option value="">-- Select Destination --</option>
              {buildings.map((building, index) => (
                building.name !== sourceBuilding && (
                  <option key={index} value={building.name || building}>
                    {building.name || building}
                  </option>
                )
              ))}
            </Select>
          </FormGroup>
          
          <Info>
            The system will automatically find the optimal path between these buildings,
            minimizing total distance and reusing existing roads where possible.
          </Info>
          
          <ButtonGroup>
            <Button 
              type="button" 
              className="secondary" 
              onClick={onClose}
            >
              Cancel
            </Button>
            <Button 
              type="submit" 
              className="primary" 
              disabled={!sourceBuilding || !targetBuilding}
            >
              Connect
            </Button>
          </ButtonGroup>
        </Form>
      </ModalContent>
    </ModalOverlay>
  );
};

export default ConnectionModal;