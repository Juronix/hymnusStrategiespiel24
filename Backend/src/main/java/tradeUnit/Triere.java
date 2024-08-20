package tradeUnit;

import group.Team;
import path.Path;

public class Triere extends SeaTradeUnit {

    private static double capacity = 20;

    public Triere(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }
    
}
