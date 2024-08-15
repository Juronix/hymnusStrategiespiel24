package path;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import city.City;
import team.Team;
import tradeUnit.TradeUnit;

public abstract class Path implements Serializable {

    private final City city1;
    private final City city2;

    private final Map<Team, Set<TradeUnit>> tradeUnitTeamMap = new HashMap<>();

    public Path(City city1, City city2) {
        this.city1 = city1;
        this.city2 = city2;
        city1.addPath(this);
        city2.addPath(this);
    }

    public abstract boolean canPass(TradeUnit tradeUnit);
    public abstract boolean hasSpaceFor(Team team);

    public boolean hasTradingPosts(Team team) {
        return city1.hasTradingPost(team) && city2.hasTradingPost(team);
    }

    public boolean canTrade(TradeUnit tradeUnit) {
        return hasSpaceFor(tradeUnit.getTeam()) && canPass(tradeUnit) && hasTradingPosts(tradeUnit.getTeam());
    }

    public void addTradeUnit(TradeUnit tradeUnit) {
        if(!canTrade(tradeUnit)) {
            throw new IllegalArgumentException("Cannot trade on this path");
        }
        if(!tradeUnitTeamMap.containsKey(tradeUnit.getTeam())) {
            Set<TradeUnit> teamsTradeUnits = new HashSet<>();
            tradeUnitTeamMap.put(tradeUnit.getTeam(), teamsTradeUnits);
            teamsTradeUnits.add(tradeUnit);
        } else {
            tradeUnitTeamMap.get(tradeUnit.getTeam()).add(tradeUnit);
        }
    }

    public City getCity1() {
        return city1;
    }

    public City getCity2() {
        return city2;
    }

    public City getOtherCity(City city) {
        if(city == city1) {
            return city2;
        } else {
            return city1;
        }
    }

    public Set<TradeUnit> getTradeUnits(Team team) {
        return tradeUnitTeamMap.get(team);
    }

    public Set<Team> getTradingTeams() {
        return tradeUnitTeamMap.keySet();
    }

}







