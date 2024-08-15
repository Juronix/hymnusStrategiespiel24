package tradeUnit;

import path.Path;
import team.Team;

public class bullockCart extends LandTradeUnit {
    
    private static double capacity = 10;

    public bullockCart(Team team, Path path) {
        super(team, path);
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

}
