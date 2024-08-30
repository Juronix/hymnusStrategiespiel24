import React, { useState } from 'react';

function CloseGame() {
  const [status, setStatus] = useState('');

  const handleSetup = async () => {
    try {
      setStatus('Sending...');
      const response = await fetch(`http://${window.location.hostname}:8080/closeGame`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          // Hier kannst du den Body des Requests anpassen, falls nötig
        }),
      });

      if (response.ok) {
        setStatus('Setup successful!');
      } else {
        const errorText = await response.text();
        setStatus(`Setup failed: ${response.status} - ${errorText}`);
      }
    } catch (error) {
        setStatus(`An error occurred: ${error.message}`);
    }
  };

  return (
    <div>
      <button onClick={handleSetup}>Close Game</button>
      <p>{status}</p>
    </div>
  );
}

export default CloseGame;
