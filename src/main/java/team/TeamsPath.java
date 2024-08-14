package team;

import java.util.HashSet;
import java.util.Set;

import path.Path;
import tradeUnit.TradeUnit;

public class TeamsPath {

    private final Path path;
    private final Set<TradeUnit> tradeUnits;
    private double tradeCapacity;
    private double tradeCapacityUsed;

    public TeamsPath(Path path, Set<TradeUnit> tradeUnits) {
        this.path = path;
        this.tradeUnits = tradeUnits;
        refreshTradeCapacity();
    }

    public TeamsPath(Path path, TradeUnit tradeUnit) {
        this.path = path;
        this.tradeUnits = new HashSet<>();
        tradeUnits.add(tradeUnit);
        refreshTradeCapacity();
    }

    public TeamsPath(Path path) {
        this.path = path;
        this.tradeUnits = new HashSet<>();
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

    public void setCapacityUsed(double capacityUsed) {
        tradeCapacityUsed = capacityUsed;
    }

    public void increaseCapacityUsed(double capacityNeeded) {
        tradeCapacityUsed += capacityNeeded;
    }

    public double getFreeTradeCapacity() {
        return tradeCapacity - tradeCapacityUsed;
    }

    public Path getPath() {
        return path;
    }

}
