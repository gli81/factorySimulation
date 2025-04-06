import { useState, useEffect } from 'react';
import styled from 'styled-components';
import RequestButton from './components/RequestButton';
import RequestModal from './components/RequestModal';
import StepButton from './components/StepButton';
import FinishButton from './components/FinishButton';
import VerbositySlider from './components/VerbositySlider';
import Cell from './Cell';
import './App.css'

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
  
  const handleFinish = async () => {
    try {
      const response = awaitfetch('/api/finish', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      console.log('Finished successfully');
    } catch (error) {
      console.error('Error finishing:', error);
    }
  };
  
  const handleVerbosityChange = (level) => {
    console.log(`Setting verbosity to ${level}`);
    setVerbosityLevel(level);
    // In a real implementation, this would call your backend API
  };
  
  const [rows, setRows] = useState(0);
  const [cols, setCols] = useState(0);
  useEffect(() => {
    setRows(8);setCols(8);
  }, [])
  const total = rows * cols;
  return (
    <div className="page">
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
      
      <RequestModal 
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleRequestSubmit}
        sources={dummySources}
        recipes={dummyRecipes}
      />
    
      <div
        className="grid-container"
        style={{
          gridTemplateColumns: `repeat(${cols}, 1fr)`,
          gridTemplateRows: `repeat(${rows}, 1fr)`,
        }}
      >
        {Array.from({ length: total }).map((_, i) => (
          <Cell key={i} />
        ))}
      </div>
    </div>
  );
}

export default App;
