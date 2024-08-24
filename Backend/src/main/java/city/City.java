package city;

import java.io.Serializable;
import java.util.Set;

import group.Team;

import java.util.HashSet;
import java.util.PriorityQueue;

import path.Path;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstract class representing a City.
 * Provides a template for city-level specific implementations.
 */
public abstract class City implements Serializable, Comparable<City> {
    private final String name;
    private final int id;
    private final Province province;
    private int distanceToRome = Integer.MAX_VALUE;
    private double multiplier = 1.0;
    private boolean hasTradeGood;

    private Set<Team> teamsWithTradingPost = new HashSet<>();
    @JsonIgnore
    private Set<Path> paths = new HashSet<>();

    /**
     * Constructs a City with the specified name and level.
     *
     * @param name      the name of the city
     * @param cityLevel the level of the city
     */
    public City(String name, int id, Province province, boolean hasTradeGood) {
        this.name = name;
        this.province = province;
        this.hasTradeGood = hasTradeGood;
        this.id = id;
    }

    public City(String name, int id, Province province) {
        this(name, id, province, false);
    }

    public static City getNewCity(String name, int id, int cityLevel, Province province, boolean hasTradeGood) {
        switch (cityLevel) {
            case 0:
                return new Rome(name, id, province);
            case 1:
                return new CityLvl1(name, id, province, hasTradeGood);
            case 2:
                return new CityLvl2(name, id, province, hasTradeGood);
            case 3:
                return new CityLvl3(name, id, province, hasTradeGood);
            default:
                throw new IllegalArgumentException("Invalid city level");
        }
    }

    public abstract double getReputation();

    public abstract double getCapacityNeeded();

    public abstract int getCityLevel();

    @Override
    public int compareTo(City city) {
        int levelComparator = Integer.compare(this.getCityLevel(), city.getCityLevel());
        if (levelComparator == 0) {
            return Integer.compare(this.getDistanceToRome(), city.getDistanceToRome());
        } else {
            return levelComparator;
        }
    }

    public static void refreshDistancesToRome(Set<City> cities, City rome) {
        PriorityQueue<City> cityQueue = new PriorityQueue<City>(
                (d1, d2) -> Integer.compare(d1.getDistanceToRome(), d2.getDistanceToRome()));
        cities.forEach(city -> city.distanceToRome = Integer.MAX_VALUE);
        rome.setDistanceToRome(0);
        cityQueue.add(rome);

        Set<City> searchDone = new HashSet<City>();

        while (!cityQueue.isEmpty()) {
            City city = cityQueue.poll();
            searchDone.add(city);
            for (Path path : city.paths) {
                City nextCity;
                if (path.getCity1() == city) {
                    nextCity = path.getCity2();
                } else {
                    nextCity = path.getCity1();
                }
                if (!searchDone.contains(nextCity)) {
                    int newDistance = city.getDistanceToRome() + 1;
                    if (newDistance < nextCity.getDistanceToRome()) {
                        nextCity.setDistanceToRome(newDistance);
                        if(cityQueue.contains(nextCity)) {
                            cityQueue.remove(nextCity);
                        }
                        cityQueue.add(nextCity);
                    }
                }
            }
        }
    }

    /**
     * 
     */
    public double getReputationForTrade(Team team, double capacityUsed, double reputationMultiplier) {
        return 0; //TODO
    };

    public void addPath(Path path) {
        paths.add(path);
    }

    public void addTradePost(Team team) {
        teamsWithTradingPost.add(team);
    }

    public boolean hasTradingPost(Team team) {
        return teamsWithTradingPost.contains(team);
    }

    public String getName() {
        return name;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public boolean hasTradeGood() {
        return hasTradeGood;
    }

    public int getId() {
        return id;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public int getDistanceToRome() {
        return distanceToRome;
    }

    private void setDistanceToRome(int distance) {
        distanceToRome = distance;
    }

    public Province getProvince() {
        return province;
    }

}
