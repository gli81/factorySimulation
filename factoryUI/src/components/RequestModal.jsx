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
    background-color: #4a90e2;
    color: white;
    border: none;
  }
  
  &.secondary {
    background-color: white;
    color: #333;
    border: 1px solid #ccc;
  }
`;

const RequestModal = ({ isOpen, onClose, onSubmit, sources = [], recipes = [] }) => {
  const [selectedSource, setSelectedSource] = useState('');
  const [selectedRecipe, setSelectedRecipe] = useState('');
  
  if (!isOpen) return null;
  
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(selectedSource, selectedRecipe);
    // Reset form
    setSelectedSource('');
    setSelectedRecipe('');
    onClose();
  };
  
  return (
    <ModalOverlay onClick={onClose}>
      <ModalContent onClick={e => e.stopPropagation()}>
        <ModalHeader>
          <ModalTitle>Request Item</ModalTitle>
          <CloseButton onClick={onClose}>&times;</CloseButton>
        </ModalHeader>
        
        <Form onSubmit={handleSubmit}>
          <FormGroup>
            <Label htmlFor="source">Source Building:</Label>
            <Select 
              id="source"
              value={selectedSource}
              onChange={(e) => setSelectedSource(e.target.value)}
              required
            >
              <option value="">-- Select Source --</option>
              {sources.map((source, index) => (
                <option key={index} value={source.name || source}>
                  {source.name || source}
                </option>
              ))}
            </Select>
          </FormGroup>
          
          <FormGroup>
            <Label htmlFor="recipe">Recipe:</Label>
            <Select 
              id="recipe"
              value={selectedRecipe}
              onChange={(e) => setSelectedRecipe(e.target.value)}
              required
            >
              <option value="">-- Select Recipe --</option>
              {recipes.map((recipe, index) => (
                <option key={index} value={recipe.name || recipe}>
                  {recipe.name || recipe}
                </option>
              ))}
            </Select>
          </FormGroup>
          
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
              disabled={!selectedSource || !selectedRecipe}
            >
              Submit
            </Button>
          </ButtonGroup>
        </Form>
      </ModalContent>
    </ModalOverlay>
  );
};

export default RequestModal;