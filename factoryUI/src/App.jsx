// src/App.jsx
import { useState } from 'react';
import styled from 'styled-components';
import SimulationGrid from './components/SimulationGrid';
import RequestButton from './components/RequestButton';
import RequestModal from './components/RequestModal';

const AppContainer = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
`;

const ControlBar = styled.div`
  display: flex;
  margin-bottom: 20px;
`;

// Dummy data for demonstration
const dummyBuildings = [
  { name: 'Factory 1', type: 'factory', x: 0, y: 0 },
  { name: 'Mine 1', type: 'mine', x: 0, y: 3 },
  { name: 'Storage 1', type: 'storage', x: 3, y: 2 }
];

// Dummy data for dropdown options
const dummySources = ['Factory 1', 'Mine 1', 'Storage 1'];
const dummyRecipes = ['door', 'window', 'bolt', 'screw', 'metal'];

function App() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  
  const handleRequestSubmit = (source, recipe) => {
    console.log(`Requested ${recipe} from ${source}`);
    // In a real implementation, this would call your backend API
  };
  
  return (
    <AppContainer>
      <ControlBar>
        <RequestButton onClick={() => setIsModalOpen(true)} />
      </ControlBar>
      
      <SimulationGrid buildings={dummyBuildings} />
      
      <RequestModal 
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleRequestSubmit}
        sources={dummySources}
        recipes={dummyRecipes}
      />
    </AppContainer>
  );
}

export default App;