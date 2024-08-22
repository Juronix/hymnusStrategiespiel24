import React, { useState, useEffect } from 'react';

function CentralTeams() {
  const [families, setFamilies] = useState([]);
  const [cities, setCities] = useState([]);
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

    const fetchCities = async () => {
      try {
        const response = await fetch('http://localhost:8080/getCities');
        if (!response.ok) throw new Error('Failed to fetch cities data');
        const citiesData = await response.json();
        setCities(citiesData);
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

  const handleNameChange = async (team, oldName) => {
    try {
      await fetch('http://localhost:8080/setTeamName', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ oldName, newName: newTeamNames[oldName] }),
      });
      setNewTeamNames({ ...newTeamNames, [oldName]: '' });
    } catch (error) {
      console.error('Error updating team name:', error);
    }
  };

  const handleAddReputation = async (teamName) => {
    try {
      await fetch('http://localhost:8080/addReputation', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ teamName, reputation: reputation[teamName] }),
      });
      setReputation({ ...reputation, [teamName]: 0 });
    } catch (error) {
      console.error('Error adding reputation:', error);
    }
  };

  const handleReputationMultiplier = async (teamName) => {
    try {
      await fetch('http://localhost:8080/setReputationMultiplier', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ teamName, multiplier: reputationMultiplier[teamName] }),
      });
      setReputationMultiplier({ ...reputationMultiplier, [teamName]: 1 });
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

  const handleResetHymns = async (teamName) => {
    try {
      await fetch('http://localhost:8080/resetHymnen', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ teamName }),
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
                  value={newTeamNames[team.name] || ''}
                  onChange={(e) => setNewTeamNames({ ...newTeamNames, [team.name]: e.target.value })}
                />
                <button onClick={() => handleNameChange(team.name, team.name)}>Update Name</button>
              </label>
            </div>

            {/* Add Reputation */}
            <div>
              <label>
                Add Reputation:
                <input
                  type="number"
                  value={reputation[team.name] || 0}
                  onChange={(e) => setReputation({ ...reputation, [team.name]: parseFloat(e.target.value) })}
                />
                <button onClick={() => handleAddReputation(team.name)}>Add</button>
              </label>
            </div>

            {/* Set Reputation Multiplier */}
            <div>
              <label>
                Reputation Multiplier:
                <input
                  type="number"
                  value={reputationMultiplier[team.name] || 1}
                  onChange={(e) =>
                    setReputationMultiplier({ ...reputationMultiplier, [team.name]: parseFloat(e.target.value) })
                  }
                />
                <button onClick={() => handleReputationMultiplier(team.name)}>Set Multiplier</button>
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

            {/* Build Trade Post */}
            <div>
              <h3>Build Trade Post</h3>
              {cities.map((city) => (
                <div key={city.id}>
                  <span>{city.name}</span>
                  <button onClick={() => handleBuildTradePost(team.name, city.id)}>Build Trade Post</button>
                </div>
              ))}
            </div>

            {/* Hymns Reset */}
            <div>
              <h3>Hymns</h3>
              <span>Ready Hymns: {team.hymnen}</span>
              <button onClick={() => handleResetHymns(team.name)}>Reset Hymns</button>
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default CentralTeams;
