import React, { useState } from 'react'
import './App.css'

function Cell() {
  const [active, setActive] = useState(false);
  return (
    <div
      className={`grid-cell ${active ? 'active' : ''}`}
      onClick={() => setActive(!active)}
    />
  );
}

export default Cell
