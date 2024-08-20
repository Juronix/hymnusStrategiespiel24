package group;

import java.util.HashSet;
import java.util.Set;

public class Family {

    private String name;
    private double additionalReputation = 0.0;
    
    private Set<Team> teams = new HashSet<>();

    public Family(String name) {
        this.name = name;
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

}
