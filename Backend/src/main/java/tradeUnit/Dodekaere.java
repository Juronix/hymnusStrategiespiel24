package tradeUnit;

import group.Team;
import path.Path;

public class Dodekaere extends SeaTradeUnit {
    
    private static final double capacity = 12;

    public Dodekaere(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
