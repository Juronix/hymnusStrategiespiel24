package hymnusstrategiespiel24;

import java.util.HashMap;
import java.util.Map;

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

    private Map<Integer, City> cityMap;

    @BeforeEach
    public void setup() {
        
        Province province = new Province("TestProvince");
        
        // Städte erstellen
        City rome = City.getNewCity("Rome", 0, 0, province, false);
        City cityA = City.getNewCity("CityA", 1, 1, province, false);
        City cityB = City.getNewCity("CityB", 2, 1, province, false);
        City cityC = City.getNewCity("CityC", 3, 1, province, false);
        City cityD = City.getNewCity("CityD", 4, 1, province, false);

        cityMap = new HashMap<>();
        cityMap.put(0, rome);
        cityMap.put(1, cityA);
        cityMap.put(2, cityB);
        cityMap.put(3, cityC);
        cityMap.put(4, cityD);


        // Pfade hinzufügen
        Path pathRA = new Road(rome, cityA);
        Path pathAB = new Road(cityA, cityB);
        Path pathBC = new Road(cityB, cityC);
        Path pathCD = new Road(cityC, cityD);
        Path pathDR = new Road(cityD, rome);

        Family family = new Family(1, "TestFamily");

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

        City.refreshDistancesToRome(cityMap.values(), rome);
    }

    @Test
    public void testMaxFlowToRome() {
        // Teste die Berechnung des maximalen Flusses
        double maxFlow = teamsGraph.calculateMaxFlowToRome(cityMap);

        // Konsolenausgabe für das Ergebnis
        System.out.println("Maximaler Fluss nach Rom: " + maxFlow);

        
        // Konsolenausgabe der Städte und ihrer Eigenschaften
        System.out.println("\nStaedte:");
        System.out.printf("%-15s%-20s%-20s\n", "Stadtname", "Distanz nach Rom", "Genutzte Kapazität");
        System.out.println("--------------------------------------------------------------------------");
        for (TeamsCity city : teamsGraph.getTeamCities()) {
            System.out.printf("%-15s%-20d%-20.2f\n", 
                city.getCity().getName(), 
                city.getCity().getDistanceToRome(), 
                city.getCapacityUsed()
            );
        }
    }

    @Test
    public void testCitiesToTradeTo() {
        
        System.out.println("\nHandelsposten:");
        teamsGraph.getCitiesToTradeTo().forEach(city -> {
            System.out.print(city.getName()+", ");
        });

        System.out.println("\nHandelsposten (Land):");
        teamsGraph.getCitiesToTradeTo(true).forEach(city -> {
            System.out.print(city.getName()+", ");
        });

        System.out.println("\nHandelsposten (See):");
        teamsGraph.getCitiesToTradeTo(false).forEach(city -> {
            System.out.print(city.getName()+", ");
        });

        System.out.println("\nHandelsposten (Rome):");
        teamsGraph.getCitiesToTradeTo(true, cityMap.get(0)).forEach(city -> {
            System.out.print(city.getName()+", ");
        });

    }

}
