package city;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import general.Team;
import path.Path;

/**
 * Abstract class representing a City.
 * Provides a template for city-level specific implementations.
 */
public abstract class City implements Serializable {
    private final String name;
    private final int cityLevel;
    private final int id;
    private double multiplier = 1.0;
    private Optional<Bonus> optionalBonus;

    private Set<Team> teamsWitchTradingPost;
    private Set<Path> paths;

    private static Map<String, Integer> idMap;
    private static Map<Integer, City> cityMap;


    /**
     * Constructs a City with the specified name and level.
     *
     * @param name      the name of the city
     * @param cityLevel the level of the city
     */
    public City(String name, int cityLevel, Optional<Bonus> optionalBonus) {
        this.name = name;
        this.cityLevel = cityLevel;
        this.optionalBonus = optionalBonus;
        id = idMap.size();
        idMap.put(name, id);
        cityMap.put(id, this);
    }

    public City(String name, int cityLevel) {
        this(name, cityLevel, Optional.empty());
    }

    public void createNewCity(String name, int cityLevel, Optional<Bonus> optionalBonus) {
        switch(cityLevel) {
            case 0:
                new Rome(name);
                break;
            case 1:
                new CityLvl1(name, optionalBonus);
                break;
            case 2:
                new CityLvl2(name, optionalBonus);
                break;
            case 3:
                new CityLvl3(name, optionalBonus);
                break;
            default:
                throw new IllegalArgumentException("Invalid city level");
            
        }
    }

    public abstract double getReputation();
    public abstract double getCapacityNeeded();

    /**
     * Calculates the Reputation based on connection efficiency and team multiplier.
     *
     * @param connectionEfficiency the efficiency of the connection to Rome
     * @param teamMultiplier       the team-specific multiplier
     * @return the calculated Reputation
     */
    public double calculateReputation(double connectionEfficiency, double teamMultiplier) {
        return cityLevel * multiplier * teamMultiplier * connectionEfficiency;
    };

    public void addPath(Path path) {
        paths.add(path);
    }

    public void buildTradingPost(Team team) {
        teamsWitchTradingPost.add(team);
    }
    public boolean hasTradingPost(Team team) {
        return teamsWitchTradingPost.contains(team);
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
    public Optional<Bonus> getOptionalBonus() {
        return optionalBonus;
    }
    public int getId() {
        return id;
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

}


