package path;

import city.City;
import group.Team;
import tradeUnit.LandTradeUnit;
import tradeUnit.TradeUnit;

public class Trail extends Path {

    private static final int maxTradingTeams = 4;

    public Trail(City city1, City city2) {
        super(city1, city2);
    }

    @Override
    public boolean canPass(TradeUnit tradeUnit) {
        return tradeUnit instanceof LandTradeUnit;
    }

    @Override
    public boolean hasSpaceFor(Team team) {
        if(getTradingTeams().contains(team)){
            return true;
        } else {
            return getTradingTeams().size() < maxTradingTeams;
        }
    }



    

}
