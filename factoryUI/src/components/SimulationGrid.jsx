import React from 'react';
import styled from 'styled-components';

const GridContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  grid-template-rows: repeat(4, 1fr);
  gap: 2px;
  background-color: #d5e8b0;
  padding: 10px;
  border: 1px solid white;
  height: 70vh;
`;

const Cell = styled.div`
  background-color: ${props => props.occupied ? 'transparent' : '#d5e8b0'};
  border: 1px solid white;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
`;

const BuildingIcon = styled.div`
  width: 80%;
  height: 80%;
  background-color: ${props => {
    switch(props.type) {
      case 'factory': return '#a2c8e8';
      case 'mine': return '#e8c8a2';
      case 'storage': return '#a2e8c0';
      default: return 'gray';
    }
  }};
  border-radius: 5px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
`;

const BuildingName = styled.div`
  font-size: 0.8em;
  margin-top: 5px;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100%;
  color: #333;
`;

const SimulationGrid = ({ buildings = [] }) => {
  // Create a 7x4 grid
  const gridCells = Array(28).fill(null).map((_, index) => {
    const x = index % 7;
    const y = Math.floor(index / 7);
    
    // Find if there's a building at this position
    const building = buildings.find(b => b.x === x && b.y === y);
    
    return (
      <Cell 
        key={index} 
        occupied={!!building}
      >
        {building && (
          <BuildingIcon type={building.type}>
            <BuildingName>{building.name}</BuildingName>
          </BuildingIcon>
        )}
      </Cell>
    );
  });
  
  return (
    <GridContainer>
      {gridCells}
    </GridContainer>
  );
};

export default SimulationGrid;