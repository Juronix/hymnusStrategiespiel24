import React, { useState, useEffect } from 'react';

function Teams() {
  const [families, setFamilies] = useState([]);
  const [currentTeam, setCurrentTeam] = useState(null);

  useEffect(() => {
    // Fetch data from the server
    const fetchFamilies = async () => {
      try {
        const response = await fetch(`http://${window.location.hostname}:8080/getFamilies`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const data = await response.json();
        setFamilies(data);
        // Set the initial team to the first one
        if (data.length > 0 && data[0].teams.length > 0) {
          setCurrentTeam(data[0].teams[0]);
        }
      } catch (error) {
        console.error('Error fetching families:', error);
      }
    };

    fetchFamilies();
  }, []);

  const handleTeamChange = (team) => {
    setCurrentTeam(team);
  };

  return (
    <div>
      {families.length > 0 ? (
        <div>
          <div style={{ marginBottom: '20px' }}>
            {families.map((family, familyIdx) =>
              family.teams.map((team, teamIdx) => (
                <button
                  key={`${familyIdx}-${teamIdx}`}
                  onClick={() => handleTeamChange(team)}
                  style={{
                    marginRight: '10px',
                    padding: '10px',
                    backgroundColor: currentTeam?.name === team.name ? '#4caf50' : '#e0e0e0',
                    color: currentTeam?.name === team.name ? 'white' : 'black',
                    border: 'none',
                    borderRadius: '5px',
                    cursor: 'pointer',
                  }}
                >
                  {team.name}
                </button>
              ))
            )}
          </div>
          {currentTeam && (
            <div>
              <h2>{currentTeam.name}</h2>
              <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                  <tr>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>Stadt</th>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>Kapazität</th>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>Ansehen</th>
                  </tr>
                </thead>
                <tbody>
                {currentTeam.teamsGraph.teamCities.map((teamCity, idx) => (
  teamCity.id > 0 && (
    <tr key={idx}>
      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.city.name}</td>
      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.capacityUsedInPercent.toFixed(0)}%</td>
      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.city.reputation.toFixed(0)}</td>
    </tr>
  )
))}

                </tbody>
              </table>
              <h3>Handelwege</h3>
              {currentTeam.teamsGraph.teamPaths && currentTeam.teamsGraph.teamPaths.map((teamPath, index) => (
                teamPath ? (
                  <div key={index}>
                    <div style={{width: '16%', float:'left'}}>{teamPath.path.city1?.name || 'Unbekannte Stadt'} - {teamPath.path.city2?.name || 'Unbekannte Stadt'}</div>
  
                    {/* Berechnung und Anzeige des Balkendiagramms */}
                    <div style={{ marginTop: '5px', marginBottom: '10px', border: '1px solid',                             display: 'inline-block',
                            width: '53.5%'}}>
                      <div
                        style={{
                          width: `${(teamPath.utilisation / teamPath.tradeCapacity) * 100}%`,
                          height: '20px',
                          backgroundColor: 
                            teamPath.utilisation / teamPath.tradeCapacity <= 0.6 ? 'green' :
                            teamPath.utilisation / teamPath.tradeCapacity <= 0.8 ? 'yellow' : 'red',
                        }}
                      />
                    </div>
                  </div>
                ) : (
                  <div key={index}>Pfad ist nicht verfügbar</div>
                )
              ))}
            </div>
          )}
        </div>
      ) : (
        <p>Loading teams...</p>
      )}
    </div>
  );
}

export default Teams;
