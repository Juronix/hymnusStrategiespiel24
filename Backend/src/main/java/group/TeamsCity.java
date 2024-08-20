package group;

import java.util.Set;
import java.util.HashSet;

import city.City;

public class TeamsCity {

    private final City city;
    private final int id;
    private final Set<TeamsPath> teamPaths;
    private int distanceToRome = Integer.MAX_VALUE;
    private double capacityUsed = 0.0;

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

    public void setCapacityUsed(double capacityUsed) {
        this.capacityUsed = capacityUsed;
    }

    public int getId() {
        return id;
    }

}
