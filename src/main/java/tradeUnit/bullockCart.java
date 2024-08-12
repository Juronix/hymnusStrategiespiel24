package tradeUnit;

import general.Team;
import path.Path;

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
