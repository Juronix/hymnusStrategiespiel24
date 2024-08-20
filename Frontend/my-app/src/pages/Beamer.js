import React from 'react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const Beamer = () => {
  // Beispielhafte Daten für die Familien und Teams
  const families = ['Familie A', 'Familie B', 'Familie C', 'Familie D', 'Familie E', 'Familie F', 'Familie G', 'Familie H', 'Familie I'];
  const teams = [
    'Team A1', 'Team A2', 'Team A3',
    'Team B1', 'Team B2', 'Team B3',
    'Team C1', 'Team C2', 'Team C3',
    'Team D1', 'Team D2', 'Team D3',
    'Team E1', 'Team E2', 'Team E3',
    'Team F1', 'Team F2', 'Team F3',
    'Team G1', 'Team G2', 'Team G3',
    'Team H1', 'Team H2', 'Team H3',
    'Team I1', 'Team I2', 'Team I3'
  ];

  // Beispielhafte Daten für das Ansehen der Teams und Familien
  const teamReputation = [
    45, 56, 72,  // Familie A
    33, 49, 64,  // Familie B
    50, 61, 70,  // Familie C
    44, 57, 66,  // Familie D
    38, 60, 74,  // Familie E
    47, 55, 69,  // Familie F
    43, 52, 67,  // Familie G
    46, 53, 62,  // Familie H
    40, 54, 65   // Familie I
  ];

  const familyReputation = families.map((_, index) => {
    const startIndex = index * 3;
    return teamReputation[startIndex] + teamReputation[startIndex + 1] + teamReputation[startIndex + 2];
  });

  // Daten für das Diagramm
  const data = {
    labels: [...teams, ...families],
    datasets: [
      {
        label: 'Ansehen der Teams',
        data: [...teamReputation, ...new Array(9).fill(0)], // Teams mit Ansehen
        backgroundColor: 'rgba(75,192,192,0.6)',
      },
      {
        label: 'Ansehen der Familien',
        data: [...new Array(27).fill(0), ...familyReputation], // Familien mit Ansehen
        backgroundColor: 'rgba(153,102,255,0.6)',
      },
    ],
  };

  const options = {
    scales: {
      y: {
        beginAtZero: true,
      },
    },
    plugins: {
      legend: {
        position: 'top',
      },
      title: {
        display: true,
        text: 'Ansehen der Teams und Familien',
      },
    },
  };

  return (
    <div>
      <h1>Beamer: Übersicht der Familien und Teams</h1>
      <Bar data={data} options={options} />
    </div>
  );
};

export default Beamer;
