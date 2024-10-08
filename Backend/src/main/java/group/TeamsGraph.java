package group;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import city.City;

import com.example.demo.GameService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import path.Path;
import path.Road;
import path.SeaRoute;
import path.Trail;
import tradeUnit.TradeUnit;

public class TeamsGraph implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private final TeamsCity teamsRome;
    @JsonIgnore
    private boolean somethingChanged = false;
    @JsonIgnore
    private Team team;
    @JsonIgnore
    private double hymnenForTrade = 0.0;
    @JsonIgnore
    private double reputationForTrade = 0.0;
    private final Map<City, TeamsCity> teamCityMap;
    private final Map<Path, TeamsPath> teamPathMap;

    public TeamsGraph(Team team, City rome) {
        this.team = team;
        teamCityMap = new HashMap<>();
        teamPathMap = new HashMap<>();
        teamsRome = new TeamsCity(rome, 0);
        teamCityMap.put(teamsRome.getCity(), teamsRome);
    }

    public void giveReputationForTrade(Map<Integer, City> cityMap) {
        if (somethingChanged) {
            calculateMaxFlowToRome(cityMap);
            recalculateReputationForTrade();
            recalculateHymnenForTrade();
        }
        somethingChanged = false;
        team.addReputation(reputationForTrade);
    }

    public void giveHymnenForTrade() {
        team.addHymnen(hymnenForTrade);
    }

    private void recalculateReputationForTrade() {
        double reputationForTrade = 0.0;
        for (TeamsCity teamsCity : teamCityMap.values()) {
            teamsCity.recalculateReputationForTrade();
            reputationForTrade += teamsCity.getReputationForTrade();
        }
        this.reputationForTrade = reputationForTrade;
    }

    private void recalculateHymnenForTrade() {
        double hymnenForTrade = 0.0;
        for (TeamsCity teamsCity : teamCityMap.values()) {
            teamsCity.recalculateHymnenForTrade();
            hymnenForTrade += teamsCity.getHymnenForTrade();
        }
        this.hymnenForTrade = hymnenForTrade;

        Family senatorsFamily = GameService.getGameService().getSenate().getFamilyOfPolitician1();
        if(senatorsFamily == team.getFamily()) {
            hymnenForTrade *= 1.2;
        }
        
    }

 

    public List<City> getCitiesToTradeTo() {
        return teamCityMap.keySet().stream()
                .sorted((city1, city2) -> city1.getName().compareToIgnoreCase(city2.getName()))
                .collect(Collectors.toList());
    }

    public List<City> getCitiesToTradeTo(boolean isLandTradeUnit) {
        return getCitiesToTradeTo();
    }

    public List<City> getCitiesToTradeTo(boolean isLandTradeUnit, City city) {
        return city.getPaths().stream()
                .filter(path -> {
                    if (isLandTradeUnit) {
                        return path instanceof Road || path instanceof Trail;
                    } else {
                        return path instanceof SeaRoute;
                    }
                })
                .filter(path -> path.teamCouldTrade(team))
                .map(path -> path.getOtherCity(city))
                .sorted((city1, city2) -> city1.getName().compareToIgnoreCase(city2.getName()))
                .collect(Collectors.toList());
    }

    public double calculateMaxFlowToRome(Map<Integer, City> cityMap) {

        double maxFlow = 0;
        final int source = teamCityMap.size();
        final int sink = 0;
        double rGraph[][] = new double[teamCityMap.size() + 1][teamCityMap.size() + 1];
        int parent[] = new int[teamCityMap.size() + 1];

        //reset capacities used


        // fill rGraph with 0
        for (int i = 0; i < teamCityMap.size() + 1; i++) {
            for (int j = 0; j < teamCityMap.size() + 1; j++) {
                rGraph[i][j] = 0;
            }
        }
        // fill rGraph with capacities
        teamPathMap.values().forEach(teamsPath -> {
            teamsPath.refreshTradeCapacity();
            int id1 = teamCityMap.get(teamsPath.getPath().getCity1()).getId();
            int id2 = teamCityMap.get(teamsPath.getPath().getCity2()).getId();
            rGraph[id1][id2] = teamsPath.getTradeCapacity();
            rGraph[id2][id1] = teamsPath.getTradeCapacity(); // cause undirected graph
        });
        // fill rGraph with edges from source to cities
        teamCityMap.values().forEach(teamsCity -> {
            if (teamsCity != teamsRome) {
                int id = teamsCity.getId();
                rGraph[source][id] = teamsCity.getCapacityNeeded();
            }
            teamsCity.resetCapacityUsed();
        });

        // while there is a path from source to sink
        while (bfs(rGraph, source, sink, parent)) {
            double pathFlow = Double.MAX_VALUE;
            int flowDepth = 0;
            // find path flow
            for (int v = sink; v != source; v = parent[v]) {
                flowDepth++;
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }
            // update residual capacities of the edges and reverse edges
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
                // decompuse the flow to the teamsCities
                if (u == source) {
                    final int target = v;
                    teamCityMap.values().parallelStream()
                        .filter(teamsCity -> teamsCity.getId() == target)
                        .findAny().get()
                        .addToCapacityUsed(pathFlow, flowDepth);
                }
            }
            maxFlow += pathFlow;

        }

        // decompose the flows to the teamsPaths
        teamPathMap.values().forEach(teamsPath -> {
            int id1 = teamCityMap.get(teamsPath.getPath().getCity1()).getId();
            int id2 = teamCityMap.get(teamsPath.getPath().getCity2()).getId();
            teamsPath.setCapacityUsed(Math.abs(teamsPath.getTradeCapacity() - rGraph[id1][id2]));
        });

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

    public void addPath(Path path, TradeUnit tradeUnit) {
        TeamsPath teamsPath = new TeamsPath(path, tradeUnit);
        teamCityMap.get(path.getCity1()).addPath(teamsPath);
        teamCityMap.get(path.getCity2()).addPath(teamsPath);
        teamPathMap.put(path, teamsPath);
    }

    public void addTradeUnit(Path path, TradeUnit tradeUnit) {
        if (teamPathMap.containsKey(path)) {
            teamPathMap.get(path).addTradeUnit(tradeUnit);
        } else {
            addPath(path, tradeUnit);
        }
        somethingChanged = true;
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

    public void somethingChanged() {
        somethingChanged = true;
    }

}
