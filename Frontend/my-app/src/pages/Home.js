import React from 'react';
import { Link } from 'react-router-dom';
import SetupButton from './home/SetupButton';

function Home() {
  return (
    <div>
      <h1>pages</h1>
      <li><Link to="/beamer">Beamer</Link></li>
      <li><Link to="/teams">Teams</Link></li>
      <li><Link to="/centralhub">CentralHub</Link></li>
      <h1>functions</h1>
      <SetupButton />

    </div>
  );
}

export default Home;