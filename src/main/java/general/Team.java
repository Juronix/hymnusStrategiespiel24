package general;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import city.City;
import tradeUnit.TradeUnit;

public class Team implements Serializable {
    
    private String name;
    private double reputation;
    private final Set<City> tradePostCites;

    public Team(String name, double reputation) {
        this.name = name;
        this.reputation = reputation;
        tradePostCites = new HashSet<City>();
    }

    public void addReputation(double reputation) {
        this.reputation += reputation;
    }

    public void addTradePost(City city) {
        tradePostCites.add(city);
        city.addTradePost(this);
    }

    public void giveReputationForTrade() {
        for(City city : tradePostCites) {
            reputation += city.getReputation();
        }
    }




}
