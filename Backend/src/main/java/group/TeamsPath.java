package group;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.GameService;
import com.fasterxml.jackson.annotation.JsonIgnore;

import path.Path;
import tradeUnit.TradeUnit;

public class TeamsPath implements Serializable {

    private static final long serialVersionUID = 1L;
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

    public void refreshTradeCapacity() {
        tradeCapacity = 0;
        for(TradeUnit tradeUnit : tradeUnits) {
            tradeCapacity += tradeUnit.getCapacity();
        }
        Family senatorsFamily = GameService.getGameService().getSenate().getFamilyOfPolitician5();
        if(senatorsFamily == getFamily()) {
            tradeCapacity *= 1.2;
        }
    }

    @JsonIgnore
    public Family getFamily() {
        return tradeUnits.iterator().next().getTeam().getFamily();
    }

    public void addTradeUnit(TradeUnit tradeUnit) {
        tradeUnits.add(tradeUnit);
        refreshTradeCapacity();
    }

    public void resetCapacityUsed() {
        tradeCapacityUsed = 0;
    }

    public double getTradeCapacity() {
        return tradeCapacity;
    }

    public double getCapacityUsed() {
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
