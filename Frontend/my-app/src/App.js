import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Beamer from './pages/Beamer';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/beamer" element={<Beamer />} />
      </Routes>
    </Router>
  );
}

export default App;
