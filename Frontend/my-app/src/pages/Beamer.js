import React, { useEffect, useState } from 'react';
import './beamer/beamer.css';

function Beamer() {
  const [families, setFamilies] = useState([]);
  const [senate, setSenate] = useState([]);

  useEffect(() => {
    // Funktion zum Laden der Daten
    const fetchFamiliesData = () => {
      fetch('http://localhost:8080/getFamilies')
        .then(response => response.json())
        .then(data => {
          setFamilies(data);
        })
        .catch(error => console.error('Fehler beim Abrufen der Daten:', error));
    };

    const fetchSenateData = () => {
      fetch('http://localhost:8080/getSenate')
        .then(response => response.json())
        .then(data => {
          console.log('Daten aktualisiert:', data);
          setSenate(data);
        })
        .catch(error => console.error('Fehler beim Abrufen der Daten:', error));
    };

  
    // Initialer Datenabruf
    fetchFamiliesData();
    fetchSenateData();
  
    // Intervall zum regelmäßigen Abrufen der Daten
    const interval = setInterval(fetchFamiliesData, 5000); // Alle 5 Sekunden
    const interval2 = setInterval(fetchSenateData, 5000);
  
    // Aufräumen des Intervalls beim Beenden der Komponente
    return () => {clearInterval(interval);clearInterval(interval2)};
  }, []);
  

  // Funktion, um den höchsten Reputationswert zu berechnen
  const maxInfluence = Math.max(...families.map(family => family.reputation));

  const colors = ['#4caf50', '#ff9800', '#2196f3', '#9c27b0', '#f44336', '#00bcd4'];

  return (
    <div className='background-image'>
  <div className="container">
    <div className="senators-left">
      {(senate.nameList || []).slice(0, 4).map((senator, index) => {
        const familyKey = `familyOfPolitician${index + 1}`;
        const family = senate[familyKey] || {};
        return (
          <div key={index} className="senator-item">
            <h3>{senator}</h3>
            {family && family.name && (
              <p>{family.name}</p>
            )}
          </div>
        );
      })}
    </div>

    <div className="central-content">
      {families.length > 0 ? (
        families.map((family, index) => {
          const influence = family.reputation;
          return (
            <div key={index} className="family-item">
              <h2>{family.name}</h2>
              <div className="influence-bar">
                <div>Einfluss: {influence.toFixed(0)}</div>
                <div className="family-bar" style={{ width: `${(family.reputation / maxInfluence) * 100}%` }}>
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
                        {team.name}
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
                    +{family.additionalReputation.toFixed(0)}
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

    <div className="senators-right">
    {(senate.nameList || []).slice(4, 8).map((senator, index) => {
        const familyKey = `familyOfPolitician${index + 5}`;
        const family = senate[familyKey] || {};
        return (
          <div key={index} className="senator-item">
            <h3>{senator}</h3>
            {family && family.name && (
              <p>{family.name}</p>
            )}
          </div>
        );
      })}
    </div>
  </div>
    </div>
  );
}

export default Beamer;
