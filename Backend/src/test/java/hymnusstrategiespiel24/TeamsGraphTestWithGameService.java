package hymnusstrategiespiel24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo.GameService;

import city.City;
import group.Family;
import group.Team;
import group.TeamsCity;
import group.TeamsGraph;
import tradeUnit.TradeUnit;


public class TeamsGraphTestWithGameService {

    private GameService gameService;
    private Team team;
    private TeamsGraph teamsGraph;
    


    @BeforeEach
    public void setup() {
        gameService = new GameService();
        gameService.setupGame();
        team = gameService.getFamilies().iterator().next().getTeams().iterator().next();
        int id = team.getId();
        teamsGraph = team.getTeamsGraph();

        /*
        for(Family family : gameService.getFamilies()){
            family.giveReputationForTrade(gameService.getCityMap());
            family.giveHymnenForTrade();
        }
        */

        // für jede Familie
        team.createTradePost(gameService.getCityMap().get(1));
        team.createTradePost(gameService.getCityMap().get(2));
        team.createTradePost(gameService.getCityMap().get(3));
        team.createTradePost(gameService.getCityMap().get(4));
        team.createTradePost(gameService.getCityMap().get(5));
        team.createTradePost(gameService.getCityMap().get(6));
        team.createTradePost(gameService.getCityMap().get(7));
        team.createTradePost(gameService.getCityMap().get(8));
        team.createTradePost(gameService.getCityMap().get(9));
        team.createTradePost(gameService.getCityMap().get(10));
        team.createTradePost(gameService.getCityMap().get(11));
        team.createTradePost(gameService.getCityMap().get(12));
        


        gameService.createNewTradeUnit(true, 0, id, 0, 1);


        for(Family family : gameService.getFamilies()){
            family.giveReputationForTrade(gameService.getCityMap());
            family.giveHymnenForTrade();
        }
        
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

        System.out.println("");

        System.out.println(team.getReputation());
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

        System.out.println("\nHandelsposten (Rome & Land):");
        teamsGraph.getCitiesToTradeTo(true, gameService.getCityMap().get(0)).forEach(city -> {
            System.out.print(city.getName()+", ");
        });

        System.out.println("\nHandelsposten (Rome & See):");
        teamsGraph.getCitiesToTradeTo(false, gameService.getCityMap().get(0)).forEach(city -> {
            System.out.print(city.getName()+", ");
        });

    }
}
