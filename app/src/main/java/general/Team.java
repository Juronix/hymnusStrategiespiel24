package general;

import java.io.Serializable;
import java.util.Set;

import tradeUnit.TradeUnit;

public class Team implements Serializable {
    
    private String name;
    private double reputation;

    public Team(String name, double reputation) {
        this.name = name;
        this.reputation = reputation;
    }


}
