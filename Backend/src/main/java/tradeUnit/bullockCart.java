package tradeUnit;

import group.Team;
import path.Path;

public class BullockCart extends LandTradeUnit {
    
    private static final double capacity = 3;

    public BullockCart(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
