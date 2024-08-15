package team;

import java.io.Serializable;

import city.City;
import path.Path;
import tradeUnit.TradeUnit;

public class Team implements Serializable {
    
    private String name;
    private double reputation = 0.0;
    private double reputationMultiplier = 1.0;
    
    private TeamsGraph teamsGraph;

    public Team(String name) {
        this.name = name;
        this.teamsGraph = new TeamsGraph();
        City.getRome().addTradePost(this);
    }


    public void giveReputationForTrade() {
        teamsGraph.giveReputationForTrade();
    }


    public void addReputation(double reputation) {
        this.reputation += reputation;
    }

    public void createTradePost(City city) {
        teamsGraph.addCity(city);
        city.addTradePost(this);
    }

    public void addTradeUnit(Path path, TradeUnit tradeUnit) {
        teamsGraph.addTradeUnit(path, tradeUnit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReputation() {
        return reputation;
    }

    public double getReputationMultiplier() {
        return reputationMultiplier;
    }

    public void setReputationMultiplier(double reputationMultiplier) {
        this.reputationMultiplier = reputationMultiplier;
    }

    public TeamsGraph getTeamsGraph() {
        return teamsGraph;
    }



}
