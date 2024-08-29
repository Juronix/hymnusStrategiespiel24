package tradeUnit;

import java.io.Serializable;

import group.Team;
import path.Path;

public abstract class TradeUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Team team;

    public TradeUnit(Team team, Path path) {
        this.team = team;
        team.addTradeUnit(path, this);
        path.addTradeUnit(this);
    }

    public abstract double getCapacity();

    public static void createNewTradeUnit(boolean landTradeUnit, int level, Team team, Path path) {
        if (landTradeUnit) {
            switch (level) {
                case 0:
                    new Donkey(team, path);
                    break;
                case 1:
                    new BullockCart(team, path);
                    break;
                case 2:
                    new HorseCart(team, path);
                    break;
                case 3:
                    new Caravan(team, path);
                    break;
            }
        } else {
            switch(level) {
                case 0:
                    new Triere(team, path);
                    break;
                case 1:
                    new Corbita(team, path);
                    break;
                case 2:
                    new Dodekaere(team, path);
                    break;
            }
        }
    }

    public Team getTeam() {
        return team;
    }

}






