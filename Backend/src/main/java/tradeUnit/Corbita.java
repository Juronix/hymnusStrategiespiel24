package tradeUnit;

import group.Team;
import path.Path;

public class Corbita extends SeaTradeUnit {
    
    private static final double capacity = 9;

    public Corbita(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
