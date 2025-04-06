import { useState, useEffect } from 'react';
import styled from 'styled-components';
import RequestButton from './components/RequestButton';
import RequestModal from './components/RequestModal';
import ConnectionButton from './components/ConnectionButton'; 
import ConnectionModal from './components/ConnectionModal'; 
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

const TimeStepDisplay = styled.div`
  font-size: 16px;
  font-weight: bold;
  margin: 10px 0;
  color: #333;
`;

const MainContent = styled.div`
  display: flex;
  gap: 20px;
`;

const GridSection = styled.div`
  flex: 2;
`;

const ConsoleSection = styled.div`
  flex: 1;
`;

const SectionTitle = styled.h3`
  margin-top: 0;
  color: #333;
`;

// Dummy data 
const dummyBuildings = [
  { name: 'Factory 1', type: 'factory', x: 0, y: 0 },
  { name: 'Mine 1', type: 'mine', x: 0, y: 3 },
  { name: 'Storage 1', type: 'storage', x: 3, y: 2 }
];

const dummySources = ['Factory 1', 'Mine 1', 'Storage 1'];
const dummyRecipes = ['door', 'window', 'bolt', 'screw', 'metal'];


function App() {
  const [isRequestModalOpen, setIsRequestModalOpen] = useState(false);
  const [isConnectionModalOpen, setIsConnectionModalOpen] = useState(false);
  const [currentStep, setCurrentStep] = useState(0);
  const [verbosityLevel, setVerbosityLevel] = useState(0);
  const [consoleMessages, setConsoleMessages] = useState([]);
  
  // Helper function to add messages to the console
  const addConsoleMessage = (text, type = 'normal') => {
    setConsoleMessages(prev => [...prev, { text, type }]);
  };
  
  const handleRequestSubmit = (source, recipe) => {
    console.log(`Requested ${recipe} from ${source}`);
    
    addConsoleMessage(`Requested ${recipe} from ${source}`);
    
  };
  
  const handleConnectionSubmit = (source, target) => {
    console.log(`Connecting ${source} to ${target}`);
    
    addConsoleMessage(`Connecting ${source} to ${target}`, 'info');
    
    // Example:
    // api.connectBuildings(source, target)
    //   .then(response => {
    //     if (response.success) {
    //       addConsoleMessage(`Connection created between ${source} and ${target}`, 'success');
    //     } else {
    //       addConsoleMessage(`Failed to connect: ${response.message}`, 'error');
    //     }
    //   });
  };
  
  const handleStep = (steps) => {
    console.log(`Stepping ${steps} times`);
    
    addConsoleMessage(`Stepping ${steps} time${steps > 1 ? 's' : ''}`);
    
    setCurrentStep(currentStep + steps);
    
    // call backend API
  };
  
  const handleFinish = async () => {
    try {
      console.log('Finishing the simulation');
      const response = await fetch('/api/finish', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ finish: true }),
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      console.log(data.output);
    } catch (error) {
      console.error('Error finishing:', error);
    }
    console.log('Finished');
  };
  
  const handleVerbosityChange = (level) => {
    console.log(`Setting verbosity to ${level}`);
    setVerbosityLevel(level);
    
    addConsoleMessage(`Verbosity level set to ${level}`, 'info');
    
    // call backend API
  };
  
  const clearConsole = () => {
    setConsoleMessages([]);
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
          <RequestButton onClick={() => setIsRequestModalOpen(true)} />
          <ConnectionButton onClick={() => setIsConnectionModalOpen(true)} />
        </ControlGroup>
        
        <VerbositySlider onChange={handleVerbosityChange} />
      </ControlBar>
      
      <TimeStepDisplay>Current Time Step: {currentStep}</TimeStepDisplay>
      
      <MainContent>
        <GridSection>
          <SectionTitle>Factory Layout</SectionTitle>
          <SimulationGrid buildings={dummyBuildings} />
        </GridSection>
        
        <ConsoleSection>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <SectionTitle>Output Console (Verbosity: {verbosityLevel})</SectionTitle>
            <button 
              onClick={clearConsole}
              style={{ 
                background: 'none', 
                border: '1px solid #ccc', 
                cursor: 'pointer',
                padding: '4px 8px',
                fontSize: '12px',
                borderRadius: '4px'
              }}
            >
              Clear
            </button>
          </div>
          <OutputConsole messages={consoleMessages} />
        </ConsoleSection>
      </MainContent>
      
      <RequestModal 
        isOpen={isRequestModalOpen}
        onClose={() => setIsRequestModalOpen(false)}
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