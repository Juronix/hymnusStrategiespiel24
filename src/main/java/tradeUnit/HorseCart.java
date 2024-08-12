package tradeUnit;

import general.Team;
import path.Path;

public class HorseCart extends LandTradeUnit {
    
    private static double capacity = 5;

    public HorseCart(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
