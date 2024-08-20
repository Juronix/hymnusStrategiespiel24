package path;

import city.City;
import group.Team;
import tradeUnit.SeaTradeUnit;
import tradeUnit.TradeUnit;

public class SeaRoute extends Path {

    public SeaRoute(City city1, City city2) {
        super(city1, city2);
    }

    @Override
    public boolean canPass(TradeUnit tradeUnit) {
        return tradeUnit instanceof SeaTradeUnit;
    }

    @Override
    public boolean hasSpaceFor(Team team) {
        return true;
    }
    

}
