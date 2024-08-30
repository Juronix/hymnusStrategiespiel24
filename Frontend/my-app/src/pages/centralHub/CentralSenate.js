import React, { useState, useEffect } from 'react';

function CentralSenate() {
  const [families, setFamilies] = useState([]);
  const [senate, setSenate] = useState(null);

  useEffect(() => {
    const fetchFamilies = async () => {
      try {
        const response = await fetch(`http://${window.location.hostname}:8080/getFamilies`);
        if (!response.ok) throw new Error('Failed to fetch data');
        const data = await response.json();
        setFamilies(data);
      } catch (error) {
        console.error('Error fetching families:', error);
      }
    };

    const fetchSenate = async () => {
      try {
        const response = await fetch(`http://${window.location.hostname}:8080/getSenate`);
        if (!response.ok) throw new Error('Failed to fetch data');
        const data = await response.json();
        setSenate(data);
      } catch (error) {
        console.error('Error fetching senate:', error);
      }
    };

    fetchFamilies();
    fetchSenate();

    const interval = setInterval(() => {
      fetchFamilies();
      fetchSenate();
    }, 5000); // Polling alle 5 Sekunden

    return () => clearInterval(interval);
  }, []);

  const handleFamilySelection = async (index, familyId) => {
    try {
      await fetch(`http://${window.location.hostname}:8080/setFamilyOfPolitician`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ familyId, politicianId: index + 1 }), // Politikerindex beginnt bei 1
      });

      // Nach erfolgreichem Update den Senat neu laden
    //   const response = await fetch(`http://${window.location.hostname}:8080/getSenate`);
    //   const updatedSenate = await response.json();
    //   setSenate(updatedSenate);
    } catch (error) {
      console.error('Error setting family for politician:', error);
    }
  };

  const renderPoliticianAssignment = (index) => {
    const familyId = senate[`familyOfPolitician${index + 1}`];
    if (familyId === null) {
      return <span style={{ color: 'grey' }}>Not Assigned</span>;
    }
    const family = families.find((family) => family.id === familyId.id);
    return family ? <span style={{ color: 'green' }}>Assigned to: {family.name}</span> : <span style={{ color: 'red' }}>Assigned to another Family</span>;
  };

  if (!senate || families.length === 0) {
    return <div>Loading...</div>;
  }

  const half = Math.ceil(senate.nameList.length / 2);
  const firstHalf = senate.nameList.slice(0, half);
  const secondHalf = senate.nameList.slice(half);

  return (
    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
      <div style={{ width: '45%' }}>
        {firstHalf.map((name, index) => (
          <div key={index} style={{ marginBottom: '10px' }}>
            <strong>{name}</strong>
            <div>{renderPoliticianAssignment(index)}</div>
            <select
              onChange={(e) => handleFamilySelection(index, e.target.value)}
              defaultValue={senate[`familyOfPolitician${index + 1}`] || ''}
            >
              <option value="" disabled>Select Family</option>
              {families.map((family) => (
                <option key={family.id} value={family.id}>
                  {family.name}
                </option>
              ))}
            </select>
          </div>
        ))}
      </div>
      <div style={{ width: '45%' }}>
        {secondHalf.map((name, index) => (
          <div key={index + half} style={{ marginBottom: '10px' }}>
            <strong>{name}</strong>
            <div>{renderPoliticianAssignment(index + half)}</div>
            <select
              onChange={(e) => handleFamilySelection(index + half, e.target.value)}
              defaultValue={senate[`familyOfPolitician${index + 1}`] || ''}
            >
              <option value="" disabled>Select Family</option>
              {families.map((family) => (
                <option key={family.id} value={family.id}>
                  {family.name}
                </option>
              ))}
            </select>
          </div>
        ))}
      </div>
    </div>
  );
}

export default CentralSenate;
