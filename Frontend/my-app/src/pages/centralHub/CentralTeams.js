import React, { useState, useEffect } from 'react';
import AddTradePath from './AddTradePath'; // Importiere die neue Komponente

function CentralTeams() {
  const [families, setFamilies] = useState([]);
  const [cities, setCities] = useState([]);
  const [selectedCityId, setSelectedCityId] = useState('');
  const [newTeamNames, setNewTeamNames] = useState({});
  const [reputation, setReputation] = useState({});
  const [reputationMultiplier, setReputationMultiplier] = useState({});

  useEffect(() => {
    // Der Code für fetchFamilies und fetchCities bleibt gleich
    const fetchFamilies = async () => {
      try {
        const response = await fetch('http://localhost:8080/getFamilies');
        if (!response.ok) throw new Error('Failed to fetch data');
        const data = await response.json();
        setFamilies(data);
      } catch (error) {
        console.error('Error fetching families:', error);
      }
    };

    const fetchCities = async () => {
      try {
        const response = await fetch('http://localhost:8080/getCities');
        if (!response.ok) throw new Error('Failed to fetch data');
        const data = await response.json();
        setCities(data);
      } catch (error) {
        console.error('Error fetching cities:', error);
      }
    };

    fetchFamilies();
    fetchCities();

    const interval = setInterval(() => {
      fetchFamilies();
    }, 5000); // Polling alle 5 Sekunden

    return () => clearInterval(interval);
  }, []);

  const handleNameChange = async (id) => {
    try {
      await fetch('http://localhost:8080/setTeamName', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id, newName: newTeamNames[id] }),
      });
      setNewTeamNames({ ...newTeamNames, [id]: '' });
    } catch (error) {
      console.error('Error updating team name:', error);
    }
  };

  const handleAddReputation = async (id) => {
    try {
      await fetch('http://localhost:8080/addReputation', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id, reputation: reputation[id] }),
      });
      setReputation({ ...reputation, [id]: 0 });
    } catch (error) {
      console.error('Error adding reputation:', error);
    }
  };

  const handleReputationMultiplier = async (id) => {
    try {
      await fetch('http://localhost:8080/setReputationMultiplier', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id, multiplier: reputationMultiplier[id] }),
      });
      setReputationMultiplier({ ...reputationMultiplier, [id]: 0 });
    } catch (error) {
      console.error('Error setting reputation multiplier:', error);
    }
  };

  const handleBuild = async (id) => {
    try {
      await fetch('http://localhost:8080/buildCity', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id, cityId: selectedCityId}),
      });
    } catch (error) {
      console.error('Error building city:', error);
    }
  };

  const handleResetHymns = async (id) => {
    try {
      await fetch('http://localhost:8080/resetHymnen', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id }),
      });
    } catch (error) {
      console.error('Error resetting hymns:', error);
    }
  };

  return (
    <div>
      <h1>Central Teams Management</h1>
      {families.map((family) =>
        family.teams.map((team) => (
          <div key={team.name} style={{ border: '1px solid #ccc', padding: '10px', marginBottom: '10px' }}>
            <h2>{team.name}</h2>

            {/* Name Change */}
            <div>
              <label>
                New Team Name:
                <input
                  type="text"
                  value={newTeamNames[team.id] || ''}
                  onChange={(e) => setNewTeamNames({ ...newTeamNames, [team.id]: e.target.value })}
                />
                <button onClick={() => handleNameChange(team.id)}>Update Name</button>
              </label>
            </div>

            {/* Add Reputation */}
            <div>
              <label>
                Add Reputation:
                <input
                  type="number"
                  value={reputation[team.id] || 0}
                  onChange={(e) => setReputation({ ...reputation, [team.id]: parseFloat(e.target.value) })}
                />
                <button onClick={() => handleAddReputation(team.id)}>Add</button>
              </label>
            </div>

            {/* Set Reputation Multiplier */}
            <div>
              <label>
                <div>Reputation Multiplier: {team.additionalReputationMultiplier} </div>
                <input
                  type="number"
                  value={reputationMultiplier[team.id] || 0}
                  onChange={(e) =>
                    setReputationMultiplier({ ...reputationMultiplier, [team.id]: parseFloat(e.target.value) })
                  }
                />
                <button onClick={() => handleReputationMultiplier(team.id)}>Set Multiplier</button>
              </label>
            </div>

            {/* Add city */}
            <div>
              <h3>Handelsposten</h3>
                {team.teamsGraph.teamCities.map((city) => <div key={city.city.id}>{city.city.name}</div>)}
                <select
                  value={selectedCityId}
                  onChange={(e) => setSelectedCityId(e.target.value)}
                >
                <option value="" disabled>
                  Stadt auswählen
                </option>
                {cities.map((city) => (
                  <option key={city.id} value={city.id}>
                    {city.name}
                  </option>
                ))}
              </select>
              <button onClick={() => handleBuild(team.id)}>Bauen</button>
            </div>

            {/* Set Path for Trade Units */}
            <h3>Trade Units</h3>
            {team.teamsGraph.teamPaths && team.teamsGraph.teamPaths.map((teamPath, index) => (
              teamPath ? (
                <div key={index}>
                  {teamPath.path.city1?.name || 'Unbekannte Stadt'} - {teamPath.path.city2?.name || 'Unbekannte Stadt'}

                  {/* Berechnung und Anzeige des Balkendiagramms */}
                  <div style={{ marginTop: '5px', marginBottom: '10px', border: '1px solid' }}>
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
                  <div>
                    {`Auslastung: ${(teamPath.utilisation / teamPath.tradeCapacity * 100).toFixed(2)}%`}
                  </div>
                </div>
              ) : (
                <div key={index}>Pfad ist nicht verfügbar</div>
              )
            ))}

            {/* Hier die neue Komponente für das Hinzufügen eines Handelspfads */}
            <AddTradePath teamId={team.id} />

            {/* Hymns Reset */}
            <div>
              <h3>Hymns</h3>
              <span>Ready Hymns: {team.hymnen}</span>
              <button onClick={() => handleResetHymns(team.id)}>Reset Hymns</button>
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default CentralTeams;
