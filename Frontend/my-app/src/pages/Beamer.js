import React, { useEffect, useState } from 'react';

function Beamer() {
  const [families, setFamilies] = useState([]);

  useEffect(() => {
    const interval = setInterval(() => {
      fetch('http://localhost:8080/getFamilies')
        .then(response => response.json())
        .then(data => {
          console.log('Daten aktualisiert:', data);
          setFamilies(data);
        })
        .catch(error => console.error('Fehler beim Abrufen der Daten:', error));
    }, 5000); // Alle 5 Sekunden
  
    return () => clearInterval(interval);
  }, []);

    // Funktion zur Berechnung des Gesamteinflusses einer Familie
    const calculateInfluence = (family) => {
      const teamReputationSum = family.teams.reduce((acc, team) => acc + team.reputation, 0);
      return teamReputationSum + family.additionalReputation;
    };

    const colors = ['#4caf50', '#ff9800', '#2196f3', '#9c27b0', '#f44336', '#00bcd4'];

  return (
    <div>
      <h1>Families Influence Overview</h1>
      {families.length > 0 ? (
        families.map((family, index) => {
          const influence = calculateInfluence(family);
          return (
            <div key={index} style={{ marginBottom: '20px', padding: '10px', border: '1px solid #ccc' }}>
              <h2>{family.name}</h2>
              <div style={{ display: 'flex', alignItems: 'center' }}>
                <div style={{ width: '200px' }}>Influence: {influence.toFixed(2)}</div>
                <div style={{ width: '100%', backgroundColor: '#eee', marginLeft: '10px', display: 'flex' }}>
                  {family.teams.map((team, idx) => {
                    const teamInfluence = team.reputation;
                    const teamWidth = (teamInfluence / influence) * 100;
                    return (
                      <div
                        key={idx}
                        style={{
                          width: `${teamWidth}%`,
                          backgroundColor: colors[idx % colors.length],
                          padding: '5px',
                          color: 'white',
                          textAlign: 'center',
                          display: 'flex',
                          justifyContent: 'center',
                          alignItems: 'center',
                        }}
                      >
                        {teamInfluence.toFixed(2)}
                      </div>
                    );
                  })}
                  <div
                    style={{
                      width: `${(family.additionalReputation / influence) * 100}%`,
                      backgroundColor: '#607d8b',
                      padding: '5px',
                      color: 'white',
                      textAlign: 'center',
                      display: 'flex',
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}
                  >
                    +{family.additionalReputation.toFixed(2)}
                  </div>
                </div>
              </div>
            </div>
          );
        })
      ) : (
        <p>Loading families...</p>
      )}
    </div>
  );
}

export default Beamer;
