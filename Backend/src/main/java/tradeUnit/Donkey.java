package tradeUnit;

import path.Path;
import team.Team;

public class Donkey extends LandTradeUnit {
    
    private static double capacity = 5;

    public Donkey(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
