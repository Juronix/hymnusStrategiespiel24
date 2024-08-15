package tradeUnit;

import path.Path;
import team.Team;

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
