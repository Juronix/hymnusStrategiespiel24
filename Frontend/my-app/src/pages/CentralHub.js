import React from 'react';
import CentralTeams from './centralHub/CentralTeams';
import CentralSenate from './centralHub/CentralSenate';

function CentralHub() {
  return (
    <div>
      <h1>Senat</h1>
        <CentralSenate />
      <h1>Central Hub</h1>
        <CentralTeams />
    </div>
  );
}

export default CentralHub;
