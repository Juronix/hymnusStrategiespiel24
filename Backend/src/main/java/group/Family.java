package group;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import city.City;

public class Family {

    private String name;
    private double additionalReputation = 0.0;
    
    private Set<Team> teams = new HashSet<>();

    public Family(String name) {
        this.name = name;
    }

    public void giveReputationForTrade(Map<Integer, City> cityMap) {
        for (Team team : teams) {
            team.giveReputationForTrade(cityMap);
        }
    }

    public void giveHymnenForTrade(){
        for (Team team : teams) {
            team.giveHymnenForTrade();
        }
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
    }

    public String getName() {
        return name;
    }

    public double getAdditionalReputation() {
        return additionalReputation;
    }

    public void setAdditionalReputation(double additionalReputation) {
        this.additionalReputation = additionalReputation;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public double getReputation() {
        double reputation = additionalReputation;
        for (Team team : teams) {
            reputation += team.getReputation();
        }
        return reputation;
    }

    public void somethingChanged() {
        for (Team team : teams) {
            team.somethingChanged();
        }
    }

}
