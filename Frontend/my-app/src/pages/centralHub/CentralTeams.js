import React, { useState, useEffect } from 'react';

function CentralTeams() {
  const [families, setFamilies] = useState([]);
  const [newTeamNames, setNewTeamNames] = useState({});
  const [reputation, setReputation] = useState({});
  const [reputationMultiplier, setReputationMultiplier] = useState({});
  const [tradeUnits, setTradeUnits] = useState({});
  const [hymns, setHymns] = useState({});

  useEffect(() => {
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

    fetchFamilies();

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
      console.log(...reputationMultiplier);
      setReputationMultiplier({ ...reputationMultiplier, [id]: 0 });
    } catch (error) {
      console.error('Error setting reputation multiplier:', error);
    }
  };

  const handleSetPath = async (teamName, city1, city2) => {
    try {
      await fetch('http://localhost:8080/setPath', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ teamName, city1, city2, tradeUnits: tradeUnits[teamName] }),
      });
      setTradeUnits({ ...tradeUnits, [teamName]: 0 });
    } catch (error) {
      console.error('Error setting path:', error);
    }
  };

  const handleBuildTradePost = async (teamName, cityId) => {
    try {
      await fetch('http://localhost:8080/buildTradePost', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ teamName, cityId }),
      });
    } catch (error) {
      console.error('Error building trade post:', error);
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
                  value={reputationMultiplier[team.id]}
                  onChange={(e) =>
                    setReputationMultiplier({ ...reputationMultiplier, [team.id]: parseFloat(e.target.value) })
                  }
                />
                <button onClick={() => handleReputationMultiplier(team.id)}>Set Multiplier</button>
              </label>
            </div>

            {/* Set Path for Trade Units */}
            <div>
              <h3>Set Path for Trade Units</h3>
              {team.teamsGraph.teamCities.map((city1) =>
                team.teamsGraph.teamCities.map(
                  (city2) =>
                    city1.city.id !== city2.city.id && (
                      <div key={`${city1.city.id}-${city2.city.id}`}>
                        <span>{city1.city.name} - {city2.city.name}</span>
                        <input
                          type="number"
                          value={tradeUnits[team.name] || 0}
                          onChange={(e) => setTradeUnits({ ...tradeUnits, [team.name]: parseFloat(e.target.value) })}
                        />
                        <button onClick={() => handleSetPath(team.name, city1.city.name, city2.city.name)}>
                          Set Path
                        </button>
                      </div>
                    )
                )
              )}
            </div>

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
