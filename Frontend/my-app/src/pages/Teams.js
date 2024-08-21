import React, { useState, useEffect } from 'react';

function Teams() {
  const [families, setFamilies] = useState([]);
  const [currentTeam, setCurrentTeam] = useState(null);

  useEffect(() => {
    // Fetch data from the server
    const fetchFamilies = async () => {
      try {
        const response = await fetch('http://localhost:8080/getFamilies');
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
      <h1>Teams Overview</h1>
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
              <h2>{currentTeam.name} Details</h2>
              <h3>Cities</h3>
              <table style={{ width: '100%', borderCollapse: 'collapse' }}>
                <thead>
                  <tr>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>City Name</th>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>Capacity Used</th>
                    <th style={{ border: '1px solid #ddd', padding: '8px' }}>Reputation</th>
                  </tr>
                </thead>
                <tbody>
                  {currentTeam.teamsGraph.teamCities.map((teamCity, idx) => (
                    <tr key={idx}>
                      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.city.name}</td>
                      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.capacityUsed.toFixed(2)}</td>
                      <td style={{ border: '1px solid #ddd', padding: '8px' }}>{teamCity.city.reputation.toFixed(2)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
              <h3>Team Paths</h3>
              {currentTeam.teamsGraph.teamPaths.length > 0 ? (
                <ul>
                  {currentTeam.teamsGraph.teamPaths.map((path, idx) => (
                    <li key={idx}>Path ID: {path.id}</li>
                  ))}
                </ul>
              ) : (
                <p>No paths available for this team.</p>
              )}
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
