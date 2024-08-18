package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.Team;
import team.TeamsCity;
import team.TeamsGraph;
import path.Path;
import path.Road;
import city.City;
import tradeUnit.Donkey;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamsGraphTest {

    private Team team;
    private TeamsGraph teamsGraph;

    @BeforeEach
    public void setup() {
        
        
        // Städte erstellen
        City.createNewCity("Rome", 0, Optional.empty());
        City.createNewCity("CityA", 1, Optional.empty());
        City.createNewCity("CityB", 1, Optional.empty());
        City.createNewCity("CityC", 1, Optional.empty());
        City.createNewCity("CityD", 1, Optional.empty());

        City rome = City.getCity("Rome");
        City cityA = City.getCity("CityA");
        City cityB = City.getCity("CityB");
        City cityC = City.getCity("CityC");
        City cityD = City.getCity("CityD");

        // Pfade hinzufügen
        Path pathRA = new Road(rome, cityA);
        Path pathAB = new Road(cityA, cityB);
        Path pathBC = new Road(cityB, cityC);
        Path pathCD = new Road(cityC, cityD);
        Path pathDR = new Road(cityD, rome);

        team = new Team("TestTeam");
        teamsGraph = team.getTeamsGraph();

        // Handelsposten erstellen 
        team.createTradePost(cityA);
        team.createTradePost(cityB);
        team.createTradePost(cityC);
        team.createTradePost(cityD);

        // Handelseinheiten erstellen
        new Donkey(team, pathRA);
        new Donkey(team, pathAB);
        new Donkey(team, pathBC);
        new Donkey(team, pathCD);
        new Donkey(team, pathDR);

        City.refreshDistancesToRome();
        teamsGraph.refreshDistancesToRome();

        teamsGraph.setRomeTo0();
    }

    @Test
    public void testMaxFlowToRome() {
        // Teste die Berechnung des maximalen Flusses
        double maxFlow = teamsGraph.calculateMaxFlowToRome();

        // Konsolenausgabe für das Ergebnis
        System.out.println("Maximaler Fluss nach Rom: " + maxFlow);

        teamsGraph.setRomeTo0();
        
        // Konsolenausgabe der Städte und ihrer Eigenschaften
        System.out.println("\nStaedte:");
        System.out.printf("%-15s%-20s%-25s%-20s\n", "Stadtname", "Distanz nach Rom", "Team Distanz nach Rom", "Genutzte Kapazität");
        System.out.println("--------------------------------------------------------------------------");
        for (TeamsCity city : teamsGraph.getTeamCities()) {
            System.out.printf("%-15s%-20d%-25d%-20.2f\n", 
                city.getCity().getName(), 
                city.getCity().getDistanceToRome(), 
                city.getDistanceToRome(), 
                city.getCapacityUsed()
            );
        }
    }
}
