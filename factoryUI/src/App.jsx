// src/App.jsx
import { useState } from 'react';
import styled from 'styled-components';
import SimulationGrid from './components/SimulationGrid';
import RequestButton from './components/RequestButton';
import RequestModal from './components/RequestModal';
import StepButton from './components/StepButton';
import FinishButton from './components/FinishButton';
import VerbositySlider from './components/VerbositySlider';

const AppContainer = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
`;

const ControlBar = styled.div`
  display: flex;
  margin-bottom: 20px;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 8px;
`;

const ControlGroup = styled.div`
  display: flex;
  align-items: center;
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
  const [currentStep, setCurrentStep] = useState(0);
  const [verbosityLevel, setVerbosityLevel] = useState(0);
  
  const handleRequestSubmit = (source, recipe) => {
    console.log(`Requested ${recipe} from ${source}`);
    // In a real implementation, this would call your backend API
  };
  
  const handleStep = (steps) => {
    console.log(`Stepping ${steps} times`);
    setCurrentStep(currentStep + steps);
    // In a real implementation, this would call your backend API
  };
  
  const handleFinish = () => {
    console.log('Finishing simulation');
    // In a real implementation, this would call your backend API
  };
  
  const handleVerbosityChange = (level) => {
    console.log(`Setting verbosity to ${level}`);
    setVerbosityLevel(level);
    // In a real implementation, this would call your backend API
  };
  
  return (
    <AppContainer>
      <ControlBar>
        <ControlGroup>
          <StepButton onStep={handleStep} />
          <FinishButton onClick={handleFinish} />
        </ControlGroup>
        
        <ControlGroup>
          <RequestButton onClick={() => setIsModalOpen(true)} />
        </ControlGroup>
        
        <VerbositySlider onChange={handleVerbosityChange} />
      </ControlBar>
      
      <div>Current Time Step: {currentStep}</div>
      
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