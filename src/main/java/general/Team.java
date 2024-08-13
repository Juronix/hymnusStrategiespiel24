package general;

import java.io.Serializable;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import city.City;
import path.Path;

public class Team implements Serializable {
    
    private String name;
    private double reputation;
    private double reputationMultiplier = 1.0;
    private boolean capacityFlowChanged = false;
    
    private Map<City, Double> capacityUsed;
    private Map<Path, Double> capacityFlow;

    private final LinkedList<City> tradePostCites;  // oder doch nur das Map extrakt

    public Team(String name, double reputation) {
        this.name = name;
        this.reputation = reputation;
        tradePostCites = new LinkedList<City>();
        capacityUsed = new HashMap<City, Double>();
        capacityFlow = new HashMap<Path, Double>();
    }


    public void giveReputationForTrade() {
        if(capacityFlowChanged) {
            recalculateCapacityFlow();
        }
        for(City city : tradePostCites) {
            reputation += city.getReputationForTrade(this, capacityUsed.get(city), reputationMultiplier);
        }
    }

    public void recalculateCapacityFlow() {
        if(capacityFlowChanged) {
            for(City city : tradePostCites) {
                
            }
            capacityFlowChanged = false;
        }
    }


    public void addReputation(double reputation) {
        this.reputation += reputation;
    }

    public void addTradePost(City city) {
        tradePostCites.add(city);
        city.addTradePost(this);
    }

    public void addTradeUnit() {
        capacityFlowChanged = true;
    }


    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
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



}
