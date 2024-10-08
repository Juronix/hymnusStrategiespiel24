import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Beamer from './pages/Beamer';
import Teams from './pages/Teams';
import CentralHub from './pages/CentralHub';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/beamer" element={<Beamer />} />
        <Route path="/teams" element={<Teams />} />
        <Route path="/centralhub" element={<CentralHub />} />
      </Routes>
    </Router>
  );
}

export default App;
