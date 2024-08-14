package hymnusstrategiespiel24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import team.Team;
import team.TeamsCity;
import team.TeamsGraph;
import path.Path;
import path.Road;
import city.City;
import tradeUnit.Donkey;
import tradeUnit.TradeUnit;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamsGraphTest {

    private Team team;
    private TeamsGraph teamsGraph;

    @BeforeEach
    public void setup() {
        team = new Team("TestTeam");
        teamsGraph = team.getTeamsGraph();
        
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
        

        // Städte 
        team.createTradePost(rome);
        team.createTradePost(cityA);
        team.createTradePost(cityB);
        team.createTradePost(cityC);
        team.createTradePost(cityD);

        // Pfade hinzufügen
        Path pathRA = new Road(rome, cityA);
        Path pathAB = new Road(cityA, cityB);
        Path pathBC = new Road(cityB, cityC);
        Path pathCD = new Road(cityC, cityD);
        Path pathDR = new Road(cityD, rome);

        // Pfade mit Kapazitäten hinzufügen
        new Donkey(team, pathRA);
        new Donkey(team, pathAB);
        new Donkey(team, pathBC);
        new Donkey(team, pathCD);
        new Donkey(team, pathDR);


        City.refreshDistancesToRome();
        teamsGraph.refreshDistanceToRome();
    }

    @Test
    public void testMaxFlowToRome() {
        // Teste die Berechnung des maximalen Flusses
        double maxFlow = teamsGraph.calculateMaxFlowToRome();

        Collection<City> asd = City.getNonRomeCities();
        City romeeee = City.getRome();

        Collection<TeamsCity> asd2 = team.getTeamsGraph().getTeamCities();
        TeamsCity romeeee2 = team.getTeamsGraph().getTeamsCity(City.getRome());

        // Konsolenausgabe für das Ergebnis
        System.out.println("Maximaler Fluss nach Rom: " + maxFlow);
        
        // Konsolenausgabe der Städte und ihrer Eigenschaften
        System.out.println("\nStädte:");
        System.out.printf("%-15s%-20s%-25s%-20s\n", "Stadtname", "Distanz nach Rom", "Team Distanz nach Rom", "Genutzte Kapazität");
        System.out.println("--------------------------------------------------------------------------");
        Collection<TeamsCity> tea = teamsGraph.getTeamCities();
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
