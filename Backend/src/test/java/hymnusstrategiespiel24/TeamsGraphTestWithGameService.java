package hymnusstrategiespiel24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.GameService;

import group.Team;
import group.TeamsCity;
import group.TeamsGraph;


public class TeamsGraphTestWithGameService {

    private GameService gameService;
    private Team team;
    private TeamsGraph teamsGraph;

    @BeforeEach
    public void setup() {
        gameService = new GameService();
        gameService.setupGame();
        team = gameService.getFamilies().iterator().next().getTeams().iterator().next();
        teamsGraph = team.getTeamsGraph();

        
    }

    @Test
    public void testMaxFlowToRome() {
        // Teste die Berechnung des maximalen Flusses
        double maxFlow = teamsGraph.calculateMaxFlowToRome(gameService.getCityMap());

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
}
