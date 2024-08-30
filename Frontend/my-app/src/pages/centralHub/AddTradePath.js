import React, { useState, useEffect } from 'react';

function AddTradePath({ teamId }) {
  const [isLandTradeUnit, setIsLandTradeUnit] = useState(null); // Auswahl Landroute oder Seeroute
  const [unitLevel, setUnitLevel] = useState(''); // Auswahl des Transportmittels
  const [availableCities1, setAvailableCities1] = useState([]); // Verfügbare Städte für das erste Select
  const [selectedCity1, setSelectedCity1] = useState(''); // Ausgewählte erste Stadt
  const [availableCities2, setAvailableCities2] = useState([]); // Verfügbare Städte für das zweite Select
  const [selectedCity2, setSelectedCity2] = useState(''); // Ausgewählte zweite Stadt

  // Update availableCities1 when unitLevel changes
  useEffect(() => {
    const fetchCitiesToTradeTo2 = async () => {
      if (unitLevel) {
        try {
          const response = await fetch(
            `http://${window.location.hostname}:8080/getCitiesToTradeTo2?teamId=${teamId}&isLandTradeUnit=${isLandTradeUnit}`
          );
          if (!response.ok) throw new Error('Failed to fetch cities');
          const data = await response.json();
          setAvailableCities1(data);
        } catch (error) {
          console.error('Error fetching cities for trade:', error);
        }
      }
    };

    fetchCitiesToTradeTo2();
  }, [unitLevel, teamId, isLandTradeUnit]);

  // Update availableCities2 when selectedCity1 changes
  useEffect(() => {
    const fetchCitiesToTradeTo3 = async () => {
      if (selectedCity1) {
        try {
          const response = await fetch(
            `http://${window.location.hostname}:8080/getCitiesToTradeTo3?teamId=${teamId}&isLandTradeUnit=${isLandTradeUnit}&cityId=${selectedCity1}`
          );
          if (!response.ok) throw new Error('Failed to fetch cities');
          const data = await response.json();
          setAvailableCities2(data);
        } catch (error) {
          console.error('Error fetching cities for trade:', error);
        }
      }
    };

    fetchCitiesToTradeTo3();
  }, [selectedCity1, teamId, isLandTradeUnit]);

  const handleAddTradePath = async () => {
    try {
      await fetch(`http://${window.location.hostname}:8080/createNewTradeUnit`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          isLandTradeUnit: isLandTradeUnit,
          unitLevel: unitLevel,
          teamId: teamId,
          cityId1: selectedCity1,
          cityId2: selectedCity2,
        }),
      });
      console.log('Trade path added successfully');
    } catch (error) {
      console.error('Error adding trade path:', error);
    }
  };

  return (
    <div>
      <h3>Handelspfad hinzufügen</h3>

      {/* Auswahl: Landroute oder Seeroute */}
      <div>
        <label>
          Route wählen:
          <select
            value={isLandTradeUnit !== null ? (isLandTradeUnit ? 'land' : 'see') : ''}
            onChange={(e) => {
              setIsLandTradeUnit(e.target.value === 'land');
              setUnitLevel(''); // Reset unitLevel when changing the route type
              setSelectedCity1(''); // Reset city selections when changing the route type
              setSelectedCity2('');
            }}
          >
            <option value="" disabled>
              Route auswählen
            </option>
            <option value="land">Landroute</option>
            <option value="see">Seeroute</option>
          </select>
        </label>
      </div>

      {/* Auswahl des Transportmittels */}
      {isLandTradeUnit !== null && (
        <div>
          <label>
            Transportmittel wählen:
            <select
              value={unitLevel}
              onChange={(e) => setUnitLevel(e.target.value)}
            >
              <option value="" disabled>
                Transportmittel auswählen
              </option>
              {isLandTradeUnit ? (
                <>
                  <option value="0">Donkey (Level 0)</option>
                  <option value="1">BullockCart (Level 1)</option>
                  <option value="2">HorseCart (Level 2)</option>
                  <option value="3">Caravan (Level 3)</option>
                </>
              ) : (
                <>
                  <option value="0">Triere (Level 0)</option>
                  <option value="1">Corbita (Level 1)</option>
                  <option value="2">Dodekaere (Level 2)</option>
                </>
              )}
            </select>
          </label>
        </div>
      )}

      {/* Auswahl der ersten Stadt */}
      {unitLevel !== '' && (
        <div>
          <label>
            Erste Stadt wählen:
            <select
              value={selectedCity1}
              onChange={(e) => setSelectedCity1(e.target.value)}
            >
              <option value="" disabled>
                Stadt auswählen
              </option>
              {availableCities1.map((city) => (
                <option key={city.id} value={city.id}>
                  {city.name}
                </option>
              ))}
            </select>
          </label>
        </div>
      )}

      {/* Auswahl der zweiten Stadt */}
      {selectedCity1 && (
        <div>
          <label>
            Zweite Stadt wählen:
            <select
              value={selectedCity2}
              onChange={(e) => setSelectedCity2(e.target.value)}
            >
              <option value="" disabled>
                Stadt auswählen
              </option>
              {availableCities2.map((city) => (
                <option key={city.id} value={city.id}>
                  {city.name}
                </option>
              ))}
            </select>
          </label>
        </div>
      )}

      {/* Button zum Hinzufügen des Handelspfads */}
      {selectedCity2 && (
        <div>
          <button onClick={handleAddTradePath}>Handelsweg hinzufügen</button>
        </div>
      )}
    </div>
  );
}

export default AddTradePath;
