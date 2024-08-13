package tradeUnit;

import java.io.Serializable;

import general.Team;
import path.Path;

public abstract class TradeUnit implements Serializable {

    private Team team;

    public TradeUnit(Team team, Path path) {
        this.team = team;
        team.addTradeUnit(this);
        path.addTradeUnit(this);
    }

    public abstract double getCapacity();

    public Team getTeam() {
        return team;
    }

}






