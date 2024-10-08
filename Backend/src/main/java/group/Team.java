package group;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import city.City;
import path.Path;
import tradeUnit.TradeUnit;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    @JsonIgnore
    private Family family;
    private double hymnen = 0.0;
    private double reputation = 0.0;
    private double additionalReputationMultiplier = 1.0;
    private TeamsGraph teamsGraph;

    private static int continuousHymnen = 50;

    public Team(String name, int id, Family family, City rome) {
        this.id = id;
        this.name = name;
        this.family = family;
        family.addTeam(this);
        this.teamsGraph = new TeamsGraph(this, rome);
        rome.addTradePost(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void giveReputationForTrade(Map<Integer, City> cityMap) {
        teamsGraph.giveReputationForTrade(cityMap);
    }

    public void giveHymnenForTrade() {
        addHymnen(continuousHymnen);
        teamsGraph.giveHymnenForTrade();
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
        return additionalReputationMultiplier;
    }

    public TeamsGraph getTeamsGraph() {
        return teamsGraph;
    }

    public double getHymnen() {
        return hymnen;
    }

    public void setHymnen(double hymnen) {
        this.hymnen = hymnen;
    }

    public void addHymnen(double hymnen) {
        this.hymnen += hymnen;
    }

    public void somethingChanged() {
        teamsGraph.somethingChanged();
    }

    public Family getFamily() {
        return family;
    }

}
