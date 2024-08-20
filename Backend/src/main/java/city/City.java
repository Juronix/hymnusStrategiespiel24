package city;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import group.Team;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import path.Path;

/**
 * Abstract class representing a City.
 * Provides a template for city-level specific implementations.
 */
public abstract class City implements Serializable, Comparable<City> {
    private final String name;
    private final int cityLevel;
    private final int id;
    private final Province province;
    private int distanceToRome = Integer.MAX_VALUE;
    private double multiplier = 1.0;
    private boolean hasTradeGood;

    private Set<Team> teamsWithTradingPost = new HashSet<>();
    private Set<Path> paths = new HashSet<>();

    private static City rome;
    private static Map<String, Integer> idMap = new HashMap<>();
    private static Map<Integer, City> cityMap = new HashMap<>();

    /**
     * Constructs a City with the specified name and level.
     *
     * @param name      the name of the city
     * @param cityLevel the level of the city
     */
    public City(String name, int cityLevel, Province province, boolean hasTradeGood) {
        this.name = name;
        this.cityLevel = cityLevel;
        this.province = province;
        this.hasTradeGood = hasTradeGood;
        id = idMap.size();
        idMap.put(name, id);
        cityMap.put(id, this);
    }

    public City(String name, Province province, int cityLevel) {
        this(name, cityLevel, province, false);
    }

    public static City getNewCity(String name, int cityLevel, Province province, boolean hasTradeGood) {
        switch (cityLevel) {
            case 0:
                rome = new Rome(name, province);
                return rome;
            case 1:
                return new CityLvl1(name, province, hasTradeGood);
            case 2:
                return new CityLvl2(name, province, hasTradeGood);
            case 3:
                return new CityLvl3(name, province, hasTradeGood);
            default:
                throw new IllegalArgumentException("Invalid city level");
        }
    }

    public abstract double getReputation();

    public abstract double getCapacityNeeded();

    @Override
    public int compareTo(City city) {
        int levelComparator = Integer.compare(this.getCityLevel(), city.getCityLevel());
        if (levelComparator == 0) {
            return Integer.compare(this.getDistanceToRome(), city.getDistanceToRome());
        } else {
            return levelComparator;
        }
    }

    public static void refreshDistancesToRome() {
        PriorityQueue<City> cityQueue = new PriorityQueue<City>(
                (d1, d2) -> Integer.compare(d1.getDistanceToRome(), d2.getDistanceToRome()));
        cityMap.values().forEach(city -> city.distanceToRome = Integer.MAX_VALUE);
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
        return 0;
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

    public int getCityLevel() {
        return cityLevel;
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

    public static Map<String, Integer> getIdMap() {
        return idMap;
    }

    public static int getId(String name) {
        return idMap.get(name);
    }

    public static City getCity(int id) {
        return cityMap.get(id);
    }

    public static City getCity(String name) {
        return cityMap.get(idMap.get(name));
    }

    public int getDistanceToRome() {
        return distanceToRome;
    }

    private void setDistanceToRome(int distance) {
        distanceToRome = distance;
    }

    public static City getRome() {
        return rome;
    }

    public static Collection<City> getNonRomeCities() {
        Set<City> nonRomeCities = new HashSet<>(cityMap.values());
        nonRomeCities.remove(rome);
        return nonRomeCities;
    }

}
