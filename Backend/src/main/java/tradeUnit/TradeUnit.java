package tradeUnit;

import java.io.Serializable;

import group.Team;
import path.Path;

public abstract class TradeUnit implements Serializable {

    private final Team team;

    public TradeUnit(Team team, Path path) {
        this.team = team;
        team.addTradeUnit(path, this);
        path.addTradeUnit(this);
    }

    public abstract double getCapacity();

    public void createNewTradeUnit(boolean landTradeUnit, int level, Team team, Path path) {
        if (landTradeUnit) {
            switch (level) {
                case 1:
                    new Donkey(team, path);
                    break;
                case 2:
                    new BullockCart(team, path);
                    break;
                case 3:
                    new HorseCart(team, path);
                    break;
            }
        } else {
            switch(level) {
                case 1:
                    new Triere(team, path);
                    break;
                case 2:
                    new Corbita(team, path);
                    break;
                case 3:
                    new Dodekaere(team, path);
                    break;
            }
        }
    }

    public Team getTeam() {
        return team;
    }

}






