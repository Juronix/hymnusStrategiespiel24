package team;

import java.util.Set;
import java.util.HashSet;

import city.City;

public class TeamsCity {

    private final City city;
    private final Set<TeamsPath> teamPaths;
    private int distanceToRome;
    private double capacityUsed;

    public TeamsCity(City city, Set<TeamsPath> teamPaths) {
        this.city = city;
        this.teamPaths = teamPaths;
        resetCapacityUsed();
    }

    public TeamsCity(City city, TeamsPath teamPath) {
        this.city = city;
        this.teamPaths = new HashSet<>();
        this.teamPaths.add(teamPath);
        resetCapacityUsed();
    }

    public TeamsCity(City city) {
        this.city = city;
        this.teamPaths = new HashSet<>();
        resetCapacityUsed();
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

    public void resetDistanceToRome() {
        distanceToRome = Integer.MAX_VALUE;
    }

}
