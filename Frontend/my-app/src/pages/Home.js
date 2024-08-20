import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      <h1>Home Page</h1>
      <li><Link to="/">Home</Link></li>
      <li><Link to="/beamer">Beamer</Link></li>
    </div>
  );
}

export default Home;