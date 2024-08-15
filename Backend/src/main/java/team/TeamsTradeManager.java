package team;
/* 
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.checkerframework.checker.units.qual.min;

import city.City;
import path.Path;
import tradeUnit.TradeUnit;

public class TeamsTradeManager {

    private final Team team;
    private boolean capacityFlowChanged;

    private final Set<City> tradePostCites;
    private final Map<Path, TeamsPathProperties> teamsPathPropertiesMap;
    private Map<City, Double> cityCapacityUsed;               // capacity used by the team in each city



    public TeamsTradeManager(Team team) {
        this.team = team;
        capacityFlowChanged = true;
        tradePostCites = new HashSet<>();
        teamsPathPropertiesMap = new HashMap<>();
    }


    public void giveReputationForTrade() {
        if(capacityFlowChanged) {
            recalculateCapacityFlow();
        }
        for(City city : tradePostCites) {
            reputation += city.getReputationForTrade(this, capacityUsed.get(city), reputationMultiplier);
        }
    }



    public void fordFulkerson(){
        int maxFlow = 0;
        while (true) {
            List<Path> paths = bfsPriority();
        }
    }


    public List<TeamsPath> bfsPriority() {
        Queue<TeamsCity> teamsCityQueue = new PriorityQueue<>(Comparator.comparingDouble(teamsCity -> teamsCity.get));
        Set<City> citiesVisited = new HashSet<>();

        for(City city : City.getNonRomeCities()) {
            cityQueue.offer(city);
            citiesVisited.add(city);
        }

        while(!cityQueue.isEmpty()) {
            City city = cityQueue.poll(); 

            if(city == City.getRome()) {
                return city.getPaths(); // ???
            }

            for(Path path : city.getPaths()) {
                City nextCity = path.getOtherCity(city);
                TeamsPathProperties teamsPathProperties = teamsPathPropertiesMap.get(path);
                if(teamsPathProperties.getFreeTradeCapacity() > 0 && !citiesVisited.contains(nextCity)) {
                    citiesVisited.add(nextCity);
                    List<Path> paths = new LinkedList<>(cities);

                }
            }
        }

        cityQueue.add(City.getRome());
        Map<City, Path> usedPathMap = new HashMap<>();
        Map<City, Double> capacityUsed = new HashMap<>();
        Map<Path, Double> capacityFlow = new HashMap<>();
        while(!cityQueue.isEmpty()) {
            City city = cityQueue.poll();
            double capacityNeeded = city.getCapacityNeeded();
            for(Path path : city.getPaths()) {
                City nextCity = path.getOtherCity(city);
                if(!capacityUsed.containsKey(nextCity)) {
                    capacityUsed.put(nextCity, 0.0);
                }
                if(!capacityFlow.containsKey(path)) {
                    capacityFlow.put(path, 0.0);
                }
                double newCapacityUsed = capacityUsed.get(city) + path.getTradeUnits(team).size();
                if(newCapacityUsed < capacityUsed.get(nextCity)) {
                    capacityUsed.put(nextCity, newCapacityUsed);
                    if(cityQueue.contains(nextCity)) {
                        cityQueue.remove(nextCity);
                    }
                    cityQueue.add(nextCity);
                }
                capacityFlow.put(path, capacityFlow.get(path) + path.getTradeUnits(team).size());
            }
        }
        return null;
    }






    public void recalculateCapacityFlow() {
        cityCapacityUsed = new HashMap<>();
        teamsPathPropertiesMap.values().forEach(TeamsPathProperties::resetCapacityUsed);

        Set<City> emptyCities = new HashSet<>();
        Map<City, Double> routesFreeCapacityMap = new HashMap<>();
        Queue<City> cityQueue = new LinkedList<>();
        Map<City, Set<Path>> usedPathsMap = new HashMap<>();
        cityQueue.add(City.getRome());
        City rome = City.getRome();


        while(!cityQueue.isEmpty()) {
            City city = cityQueue.poll();
            double capacityNeeded = city.getCapacityNeeded();
            
            Path usedPath =  usedPathsMap.get(city);
            City cityOnRoute = usedPath.getOtherCity(city);
            double prevRoutesFreeCapacity = routesFreeCapacityMap.get(cityOnRoute);
            TeamsPathProperties usedPathProperties = teamsPathPropertiesMap.get(usedPath);
            double routesFreeCapacity = Math.min(prevRoutesFreeCapacity, usedPathProperties.getTradeCapacity());
            double usedCapacity = Math.min(capacityNeeded, routesFreeCapacity);


            

            City cityOnRoute = city;
            double maxCapacity = Double.POSITIVE_INFINITY;
            while(cityOnRoute != rome) {
                Path usedPath = usedPathMap.get(cityOnRoute);
                TeamsPathProperties usedPathProperties = teamsPathPropertiesMap.get(usedPath);
                teamsPathPropertiesMap.get(usedPath).increaseCapacityUsed(capacityNeeded);
                double capacityUsed = teamsPathPropertiesMap.get(usedPath).getTradeCapacityUsed();
                if(capacityUsed < maxCapacity) {
                    maxCapacity = capacityUsed;
                }
                cityOnRoute = usedPath.getOtherCity(cityOnRoute);
            }
            if(maxCapacity < 0) {
                cityOnRoute = city;
                while(cityOnRoute != rome) {
                    Path usedPath = usedPathMap.get(cityOnRoute);
                    TeamsPathProperties usedPathProperties = teamsPathPropertiesMap.get(usedPath);
                    teamsPathPropertiesMap.get(usedPath).increaseCapacityUsed(maxCapacity);
                    double capacityUsed = teamsPathPropertiesMap.get(usedPath).getTradeCapacityUsed();
                }
            } else {
                emptyCities.add(city);
            }

            for(Path path : city.getPaths()) {
                if(!teamsPathPropertiesMap.containsKey(path)) {
                    continue;   // path is not used by the team
                }
                city = path.getOtherCity(city);
                if(emptyCities.contains(city)) {
                    
                }
                

                if(!capacityUsed.containsKey(nextCity)) {
                    capacityUsed.put(nextCity, 0.0);
                }
                if(!capacityFlow.containsKey(path)) {
                    capacityFlow.put(path, 0.0);
                }
                double newCapacityUsed = capacityUsed.get(city) + path.getTradeUnits(team).size();
                if(newCapacityUsed < capacityUsed.get(nextCity)) {
                    capacityUsed.put(nextCity, newCapacityUsed);
                    if(cityQueue.contains(nextCity)) {
                        cityQueue.remove(nextCity);
                    }
                    cityQueue.add(nextCity);
                }
                capacityFlow.put(path, capacityFlow.get(path) + path.getTradeUnits(team).size());
            }
        }
        




        capacityFlowChanged = false;
    }

}
*/