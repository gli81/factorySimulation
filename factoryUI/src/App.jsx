import { useState, useEffect } from 'react';
import styled from 'styled-components';
import RequestButton from './components/RequestButton';
import RequestModal from './components/RequestModal';
import ConnectionButton from './components/ConnectionButton'; 
import ConnectionModal from './components/ConnectionModal'; 
import StepButton from './components/StepButton';
import FinishButton from './components/FinishButton';
import VerbositySlider from './components/VerbositySlider';
import OutputConsole from './components/OutputConsole';
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
  position: relative;
  height: calc(100vh - 150px);
`;

const GridSection = styled.div`
  flex: 2;
  position: relative;
`;

const ConsoleSection = styled.div`
  flex: 1;
  min-width: 300px;
  max-width: 400px;
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
  
  /**
   * handle request submit
   * @param {*} source 
   * @param {*} recipe 
   */
  const handleRequestSubmit = (source, recipe) => {
    try {
      console.log(`requested '${recipe}' from '${source}'`);
      console.log("Calling API");
      const response = fetch("/api/request", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ source: source, recipe: recipe }),
      });
      addConsoleMessage(`Requested ${recipe} from ${source}`);
    } catch (error) {
      console.error("Error requesting recipe:", error);
    }
  };
  
  const handleConnectionSubmit = (source, target) => {
    console.log(`Connecting ${source} to ${target}`);
    
    addConsoleMessage(`Connecting ${source} to ${target}`, 'info');
  };
  /**
   * function that handles step change
   * @param {} steps 
   */
  const handleStep = async (steps) => {
    try {
      console.log(`Stepping ${steps} times`);
      console.log("Calling API");
      const response = await fetch("/api/step", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ step: steps }),
      });
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      console.log(data.status);
      if (data.status === "ok") {
        addConsoleMessage(`Stepping ${steps} time${steps > 1 ? 's' : ''}`);
        addConsoleMessage(data.output, 'info');
        setCurrentStep(currentStep + steps);
      }
    } catch (error) {
      console.error("Error stepping:", error);
    }
  };
  
  /**
   * function that handles finish
   */
  const handleFinish = async () => {
    try {
      console.log('Finishing the simulation');
      console.log("Calling API");
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
      addConsoleMessage(data.output, 'info');
    } catch (error) {
      console.error('Error finishing:', error);
    }
    console.log('Finished');
  };
  
  /**
   * function that handles verbosity change
   * @param {*} level 
   */
  const handleVerbosityChange = async (level) => {
    try {
      console.log(`Setting verbosity to ${level}`);
      console.log("Calling API");
      // call backend API
      const response = await fetch('/api/verbosity', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ level: level }),
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      console.log(data.status);
      if (data.status === 'ok') {
        setVerbosityLevel(level);
        addConsoleMessage(`Verbosity level set to ${level}`, 'info');
      } else {
        throw new Error("Failed to set verbosity level");
      }
    } catch (error) {
      console.error('Error setting verbosity:', error);
    }
  };
  
  const clearConsole = () => {
    setConsoleMessages([]);
  };
  
  const [rows, setRows] = useState(0);
  const [cols, setCols] = useState(0);
  const [sources, setSources] = useState([]);
  useEffect(() => {
    const getSources = async () => {
      try {
        console.log("Calling API");
        const response = await fetch("/api/sources", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();
        console.log(data.status);
        if (data.status === "ok") {
          setSources(data.sources);
        } else {
          throw new Error("Failed to fetch sources");
        }
      } catch (error) {
        console.error("Error fetching sources:", error);
      }
    }
    getSources();
    setRows(10);setCols(10);
  }, []);
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
          <div
            className="grid-wrapper"
            style={{
              position: "relative",
              width: "min(40vw, 500px)",
              height: "min(40vw, 500px)"
            }}
          >
            <div
              className="grid-container"
              style={{
                position: "static",
                gridTemplateColumns: `repeat(${cols}, 1fr)`,
                gridTemplateRows: `repeat(${rows}, 1fr)`,
              }}
            >
              {Array.from({ length: total }).map((_, i) => (
                <Cell key={i} />
              ))}
            </div>
          </div>
        </GridSection>
        
        <ConsoleSection>
          <div style={
            {
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center'
            }
          }>
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
        sources={sources}
        // recipes={dummyRecipes}
      />
      
      <ConnectionModal 
        isOpen={isConnectionModalOpen}
        onClose={() => setIsConnectionModalOpen(false)}
        onSubmit={handleConnectionSubmit}
        buildings={dummyBuildings}
      />
    </div>
  );
}

export default App;