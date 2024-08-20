package group;

import java.io.Serializable;

import city.City;
import path.Path;
import tradeUnit.TradeUnit;

public class Team implements Serializable {
    
    private String name;
    private Family family;
    private double hymnen;
    private double reputation = 0.0;
    private double additionalReputationMultiplier = 1.0;
    
    private TeamsGraph teamsGraph;

    public Team(String name, Family family) {
        this.name = name;
        this.family = family;
        family.addTeam(this);
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

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public double getAdditionalReputationMultiplier() {
        return additionalReputationMultiplier;
    }

    public void setAdditionalReputationMultiplier(double additionalReputationMultiplier) {
        this.additionalReputationMultiplier = additionalReputationMultiplier;
    }

    public double getReputationMultiplier(){
        //TODO add senat
        return additionalReputationMultiplier;
    }

    public TeamsGraph getTeamsGraph() {
        return teamsGraph;
    }

    public Family getFamily() {
        return family;
    }

    public double getHymnen() {
        return hymnen;
    }

    public void setHymnen(double hymnen) {
        this.hymnen = hymnen;
    }

}
