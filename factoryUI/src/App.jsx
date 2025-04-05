import { useState, useEffect } from 'react';
import Cell from './Cell';
import './App.css'

function App() {
  const [rows, setRows] = useState(0);
  const [cols, setCols] = useState(0);
  // const [rows, setRows] = useState(Array(64).fill(false))
  useEffect(() => {
    setRows(8);setCols(8);
  }, [])
  const total = rows * cols;
  return (
    <div className="page">
      <button className="request-button">Request</button>
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

export default App
