import React from 'react';
import { Link } from 'react-router-dom';
import SetupButton from './home/SetupButton';
import LaodGame from './home/LoadGame';
import CloseGame from './home/CloseGame';
import SaveGame from './home/SaveGame';
import ResumeGame from './home/ResumeGame';
import PauseGame from './home/PauseGame';

function Home() {
  return (
    <div>
      <h1>pages</h1>
      <li><Link to="/beamer">Beamer</Link></li>
      <li><Link to="/teams">Teams</Link></li>
      <li><Link to="/centralhub">CentralHub</Link></li>
      <h1>functions</h1>
      <SetupButton />
      <LaodGame />
      <CloseGame />
      <SaveGame />
      <ResumeGame />
      <PauseGame />
    </div>
  );
}

export default Home;