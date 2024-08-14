package team;

import java.util.Set;
import java.util.HashSet;

import city.City;

public class TeamsCity {

    private final City city;
    private final Set<TeamsPath> teamPaths;
    private int distanceToRome = Integer.MAX_VALUE;
    private double capacityUsed = 0.0;

    public TeamsCity(City city, Set<TeamsPath> teamPaths) {
        this.city = city;
        this.teamPaths = teamPaths;
    }

    public TeamsCity(City city, TeamsPath teamPath) {
        this.city = city;
        this.teamPaths = new HashSet<>();
        this.teamPaths.add(teamPath);
    }

    public TeamsCity(City city) {
        this.city = city;
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

    public int getDistanceToRome() {
        return distanceToRome;
    }

    public void setDistanceToRome(int distanceToRome) {
        this.distanceToRome = distanceToRome;
    }
/*
    public void resetDistanceToRome() {
        distanceToRome = Integer.MAX_VALUE;
    }
*/
    public void addPath(TeamsPath teamsPath) {
        teamPaths.add(teamsPath);
    }

}
