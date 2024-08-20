package path;

import city.City;
import group.Team;
import tradeUnit.LandTradeUnit;
import tradeUnit.TradeUnit;

public class Road extends Path {

    public Road(City city1, City city2) {
        super(city1, city2);
    }

    @Override
    public boolean canPass(TradeUnit tradeUnit) {
        return tradeUnit instanceof LandTradeUnit;
    }

    @Override
    public boolean hasSpaceFor(Team team) {
        return true;
    }

}