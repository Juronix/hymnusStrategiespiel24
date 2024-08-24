package hymnusstrategiespiel24;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import path.Path;
import path.Road;
import city.City;
import city.Province;
import group.Family;
import group.Team;
import group.TeamsCity;
import group.TeamsGraph;
import tradeUnit.Donkey;


public class TeamsGraphTest {

    private Team team;
    private TeamsGraph teamsGraph;

    @BeforeEach
    public void setup() {
        
        Province province = new Province("TestProvince");
        
        // Städte erstellen
        City rome = City.getNewCity("Rome", 0, 0, province, false);
        City cityA = City.getNewCity("CityA", 1, 1, province, false);
        City cityB = City.getNewCity("CityB", 2, 1, province, false);
        City cityC = City.getNewCity("CityC", 3, 1, province, false);
        City cityD = City.getNewCity("CityD", 4, 1, province, false);

        Set<City> cities = new HashSet<>();
        cities.add(rome);
        cities.add(cityA);
        cities.add(cityB);
        cities.add(cityC);
        cities.add(cityD);


        // Pfade hinzufügen
        Path pathRA = new Road(rome, cityA);
        Path pathAB = new Road(cityA, cityB);
        Path pathBC = new Road(cityB, cityC);
        Path pathCD = new Road(cityC, cityD);
        Path pathDR = new Road(cityD, rome);

        Family family = new Family("TestFamily");

        team = new Team("TestTeam", 1, family, rome);
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

        City.refreshDistancesToRome(cities, rome);
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
