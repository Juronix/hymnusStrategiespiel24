package group;

import java.util.*;

import city.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import path.Path;
import tradeUnit.TradeUnit;

public class TeamsGraph {

    @JsonIgnore
    private final TeamsCity teamsRome;
    private final Map<City, TeamsCity> teamCityMap;
    private final Map<Path, TeamsPath> teamPathMap;

    public TeamsGraph() {
        teamCityMap = new HashMap<>();
        teamPathMap = new HashMap<>();
        teamsRome = new TeamsCity(City.getRome(), 0);
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

        double maxFlow = 0;
        final int source = teamCityMap.size();
        final int sink = 0;
        double rGraph[][] = new double[teamCityMap.size() + 1][teamCityMap.size() + 1];
        int parent[] = new int[teamCityMap.size() + 1];

        // fill rGraph with 0
        for (int i = 0; i < teamCityMap.size() + 1; i++) {
            for (int j = 0; j < teamCityMap.size() + 1; j++) {
                rGraph[i][j] = 0;
            }
        }
        // fill rGraph with capacities
        teamPathMap.values().forEach(teamsPath -> {
            int id1 = teamCityMap.get(teamsPath.getPath().getCity1()).getCity().getId();
            int id2 = teamCityMap.get(teamsPath.getPath().getCity2()).getCity().getId();
            rGraph[id1][id2] = teamsPath.getTradeCapacity();
            rGraph[id2][id1] = teamsPath.getTradeCapacity(); // cause undirected graph
        });
        // fill rGraph with edges from source to cities
        teamCityMap.values().forEach(teamsCity -> {
            if (teamsCity != teamsRome) {
                int id = teamsCity.getId();
                rGraph[source][id] = teamsCity.getCity().getCapacityNeeded();
            }
        });

        // while there is a path from source to sink
        while (bfs(rGraph, source, sink, parent)) {
            double pathFlow = Double.MAX_VALUE;
            // find path flow
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }
            // update residual capacities of the edges and reverse edges
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }
            maxFlow += pathFlow;
        }
        return maxFlow;
    }

    public boolean bfs(double rGraph[][], int source, int sink, int parent[]) {

        // create visited array and mark all as not visited
        boolean visited[] = new boolean[teamCityMap.size() + 1];
        for (int i = 0; i < teamCityMap.size() + 1; i++) {
            visited[i] = false;
        }

        // create queue and add source to it
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < teamCityMap.size() + 1; v++) {
                // if there is a path and it is not visited
                if (!visited[v] && rGraph[u][v] > 0) {
                    // if we reached sink, then there is a path
                    if (v == sink) {
                        parent[v] = u;
                        return true;
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        // there is no path
        return false;
    }

    public void addCity(City city) {
        teamCityMap.put(city, new TeamsCity(city, teamCityMap.size()));
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
