package tradeUnit;

import general.Team;
import path.Path;

public class Corbita extends SeaTradeUnit {
    
    private static double capacity = 50;

    public Corbita(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
