package team;

import java.util.*;

import city.City;
import path.Path;
import tradeUnit.TradeUnit;

public class TeamsGraph {

    private final TeamsCity teamsRome;
    private final Map<City, TeamsCity> teamCityMap;
    private final Map<Path, TeamsPath> teamPathMap;

    public TeamsGraph() {
        teamCityMap = new HashMap<>();
        teamPathMap = new HashMap<>();
        teamsRome = new TeamsCity(City.getRome());
        teamCityMap.put(teamsRome.getCity(), teamsRome);
    }

    public void giveReputationForTrade() {
        
    }

    public void refreshDistancesToRome() {
        PriorityQueue<TeamsCity> cityQueue = new PriorityQueue<>(
                Comparator.comparingInt(TeamsCity::getDistanceToRome));
        teamsRome.setDistanceToRome(0);
        cityQueue.add(teamsRome);

        Set<TeamsCity> searchDone = new HashSet<>();

        while (!cityQueue.isEmpty()) {
            TeamsCity city = cityQueue.poll();
            searchDone.add(city);
            for (TeamsPath path : city.getTeamPaths()) {
                TeamsCity nextCity = teamCityMap.get(path.getPath().getOtherCity(city.getCity()));
                if (!searchDone.contains(nextCity)) {
                    int newDistance = city.getDistanceToRome() + 1;
                    if (newDistance < nextCity.getDistanceToRome()) {
                        nextCity.setDistanceToRome(newDistance);
                        if (cityQueue.contains(nextCity)) {
                            cityQueue.remove(nextCity);
                        }
                        cityQueue.add(nextCity);
                    }
                }
            }
        }
    }


    
    public double calculateMaxFlowToRome() {
        
        return 0.0;
    }
    


    public void addCity(City city) {
        teamCityMap.put(city, new TeamsCity(city));
    }

    public void addPath(Path path) {
        TeamsPath teamsPath = new TeamsPath(path);
        teamCityMap.get(path.getCity1()).addPath(teamsPath);
        teamCityMap.get(path.getCity2()).addPath(teamsPath);
        teamPathMap.put(path, teamsPath);
    }

    public void addTradeUnit(Path path, TradeUnit tradeUnit) {
        if (teamPathMap.containsKey(path)) {
            teamPathMap.get(path).addTradeUnit(tradeUnit);
        } else {
            addPath(path);
            teamPathMap.get(path).addTradeUnit(tradeUnit);
        }
    }

    public Collection<TeamsCity> getTeamCities() {
        return teamCityMap.values();
    }

    public Collection<TeamsPath> getTeamPaths() {
        return teamPathMap.values();
    }

    public TeamsCity getTeamsCity(City city) {
        return teamCityMap.get(city);
    }

    public TeamsPath getTeamsPath(Path path) {
        return teamPathMap.get(path);
    }

    public void setRomeTo0() {
        teamsRome.setDistanceToRome(0);
    }
}
