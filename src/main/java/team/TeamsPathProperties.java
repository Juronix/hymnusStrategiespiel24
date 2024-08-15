package team;

import java.util.HashSet;
import java.util.Set;

import tradeUnit.TradeUnit;
/* 
public class TeamsPathProperties {

    private final Set<TradeUnit> tradeUnits;
    private double tradeCapacity;
    private double tradeCapacityUsed;

    public TeamsPathProperties(Set<TradeUnit> tradeUnits) {
        this.tradeUnits = tradeUnits;
        refreshTradeCapacity();
    }

    public TeamsPathProperties(TradeUnit tradeUnit) {
        this.tradeUnits = new HashSet<>();
        tradeUnits.add(tradeUnit);
        refreshTradeCapacity();
    }

    public void refreshTradeCapacity() {
        tradeCapacity = 0;
        for(TradeUnit tradeUnit : tradeUnits) {
            tradeCapacity += tradeUnit.getCapacity();
        }
    }

    public void addTradeUnit(TradeUnit tradeUnit) {
        tradeUnits.add(tradeUnit);
        tradeCapacity += tradeUnit.getCapacity();
    }

    public void resetCapacityUsed() {
        tradeCapacityUsed = 0;
    }

    public double getTradeCapacity() {
        return tradeCapacity;
    }

    public double getTradeCapacityUsed() {
        return tradeCapacityUsed;
    }

    public double getUtilisation() {
        return tradeCapacityUsed / tradeCapacity;
    }

    public void increaseCapacityUsed(double capacityNeeded) {
        tradeCapacityUsed += capacityNeeded;
    }

    public double getFreeTradeCapacity() {
        return tradeCapacity - tradeCapacityUsed;
    }

}
*/