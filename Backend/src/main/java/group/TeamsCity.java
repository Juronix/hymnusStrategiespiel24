package group;

import java.util.Set;
import java.util.HashSet;

import city.City;

public class TeamsCity {

    private final City city;
    private final int id;
    private final Set<TeamsPath> teamPaths;
    private double averageFlowDistance = 1;
    private double capacityUsed = 0.0;
    private double hymnenForTrade = 0.0;
    private double reputationForTrade = 0.0;

    public TeamsCity(City city, int id, Set<TeamsPath> teamPaths) {
        this.city = city;
        this.id = id;
        this.teamPaths = teamPaths;
    }

    public TeamsCity(City city, int id, TeamsPath teamPath) {
        this.city = city;
        this.id = id;
        this.teamPaths = new HashSet<>();
        this.teamPaths.add(teamPath);
    }

    public TeamsCity(City city, int id) {
        this.city = city;
        this.id = id;
        this.teamPaths = new HashSet<>();
    }

    public void resetCapacityUsed() {
        capacityUsed = 0;
    }

    public void increaseCapacityUsed(double capacityNeeded) {
        capacityUsed += capacityNeeded;
    }

    public double getCapacityUsed() {
        return capacityUsed;
    }

    public City getCity() {
        return city;
    }

    public Set<TeamsPath> getTeamPaths() {
        return teamPaths;
    }

    public void recalculateHymnenForTrade(){
        hymnenForTrade = city.getHymnenForTrade(capacityUsed);
    }

    public void recalculateReputationForTrade() {
        reputationForTrade = city.getReputationForTrade(capacityUsed, averageFlowDistance);
    }

    public void addPath(TeamsPath teamsPath) {
        teamPaths.add(teamsPath);
    }

    public void addToCapacityUsed(double capacityUsed, int flowDepth) {
        if(capacityUsed > 0){
            averageFlowDistance = (averageFlowDistance*this.capacityUsed + flowDepth*capacityUsed)/(this.capacityUsed + capacityUsed);
        } else {
            averageFlowDistance = flowDepth;
        }
        this.capacityUsed = this.capacityUsed + capacityUsed;
    }

    public int getId() {
        return id;
    }

    public double getHymnenForTrade() {
        return hymnenForTrade;
    }

    public double getReputationForTrade() {
        return reputationForTrade;
    }



}
