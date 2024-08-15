package tradeUnit;

import java.io.Serializable;

import path.Path;
import team.Team;

public abstract class TradeUnit implements Serializable {

    private Team team;

    public TradeUnit(Team team, Path path) {
        this.team = team;
        team.addTradeUnit(path, this);
        path.addTradeUnit(this);
    }

    public abstract double getCapacity();

    public Team getTeam() {
        return team;
    }

}






