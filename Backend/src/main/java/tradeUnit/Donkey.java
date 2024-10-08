package tradeUnit;

import group.Team;
import path.Path;

public class Donkey extends LandTradeUnit {
    
    private static final double capacity = 1;

    public Donkey(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
