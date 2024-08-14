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
        refreshDistanceToRome();
        double maxFlow = calculateMaxFlowToRome();
        for (TeamsCity city : teamCityMap.values()) {
            city.resetCapacityUsed();
        }
        if (maxFlow > 0) {
            for (TeamsCity city : teamCityMap.values()) {
                city.increaseCapacityUsed(city.getDistanceToRome() * maxFlow);
            }
        }
    }

    public void refreshDistanceToRome() {
        PriorityQueue<TeamsCity> cityQueue = new PriorityQueue<>(
                Comparator.comparingInt(TeamsCity::getDistanceToRome));
        teamCityMap.values().forEach(TeamsCity::resetDistanceToRome);
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
        double maxFlow = 0;
        List<TeamsPath> path;

        while ((path = findAugmentingPath()) != null) {
            double pathFlow = Double.MAX_VALUE;

            for (TeamsPath edge : path) {
                pathFlow = Math.min(pathFlow, edge.getFreeTradeCapacity());
            }

            for (TeamsPath edge : path) {
                edge.increaseCapacityUsed(pathFlow);
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private List<TeamsPath> findAugmentingPath() {
        PriorityQueue<TeamsCity> queue = new PriorityQueue<>(Comparator.comparingInt(TeamsCity::getDistanceToRome));
        Map<TeamsCity, TeamsPath> pathMap = new HashMap<>();
        Set<TeamsCity> visited = new HashSet<>();

        queue.add(teamsRome);

        while (!queue.isEmpty()) {
            TeamsCity city = queue.poll();

            if (visited.contains(city)) {
                continue;
            }

            visited.add(city);

            for (TeamsPath path : city.getTeamPaths()) {
                TeamsCity nextCity = teamCityMap.get(path.getPath().getOtherCity(city.getCity()));

                if (!visited.contains(nextCity) && path.getFreeTradeCapacity() > 0) {
                    queue.add(nextCity);
                    pathMap.put(nextCity, path);

                    // Wenn die Senke erreicht wurde (Rom), baue den augmentierenden Pfad
                    if (nextCity.equals(teamsRome)) {
                        List<TeamsPath> result = new ArrayList<>();
                        TeamsCity currentCity = nextCity;
                        while (pathMap.get(currentCity) != null) {
                            TeamsPath currentPath = pathMap.get(currentCity);
                            result.add(currentPath);
                            currentCity = teamCityMap.get(currentPath.getPath().getOtherCity(currentCity.getCity()));
                        }
                        Collections.reverse(result);
                        return result;
                    }
                }
            }
        }

        return null; // Kein augmentierender Pfad gefunden
    }

    public void addCity(City city) {
        teamCityMap.put(city, new TeamsCity(city));
    }

    public void addPath(Path path) {
        teamPathMap.put(path, new TeamsPath(path));
    }

    public void addTradeUnit(Path path, TradeUnit tradeUnit) {
        if (teamPathMap.containsKey(path)) {
            teamPathMap.get(path).addTradeUnit(tradeUnit);
        } else {
            teamPathMap.put(path, new TeamsPath(path, tradeUnit));
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
}
