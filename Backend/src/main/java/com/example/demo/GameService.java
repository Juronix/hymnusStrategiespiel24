package com.example.demo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Collection;

import org.springframework.stereotype.Service;

import city.City;
import city.Province;
import group.Family;
import group.Senate;
import group.Team;
import path.Path;
import time.GameTime;


@Service
public class GameService {

    private GameTime time = new GameTime();
    private Set<Province> provinces = new HashSet<>();
    private City rome;
    private Map<Integer, City> cityMap = new HashMap<>();
    private Set<Family> families = new HashSet<>();
    private Senate senate = new Senate();

    private static GameService gameService;

    public GameService() {
        gameService = this;
    }

    public static GameService getGameService() {
        return gameService;
    }

    public static void setGameService(GameService gameService) {
        GameService.gameService = gameService;
    }

    public static void reloadStaticGameService(GameService gameService) {
        GameService.gameService = gameService;
    }

    public void setupGame() {
        createCitiesAndPaths();
        createSenateTeamsAndFamilies();
    }

    public void createCitiesAndPaths(){

        Map<String, Province> provinceMap = new HashMap<>();

        try (Scanner scanner = new Scanner(City.class.getResourceAsStream("/csv/provinces.csv"))) {
            scanner.useDelimiter(";|\n");
            if (scanner.hasNextLine()) { scanner.nextLine(); }  // Skip header
            while (scanner.hasNext()) {
                String name = scanner.next();
                Province province = new Province(name);
                provinceMap.put(name, province);
                provinces.add(province);
            }
        }

        Map<String, City> cityNameMap = new HashMap<>();

        try (Scanner scanner = new Scanner(City.class.getResourceAsStream("/csv/cities.csv"))) {
            scanner.useDelimiter(";|\n");
            if (scanner.hasNextLine()) { scanner.nextLine(); }  // Skip header
            while (scanner.hasNext()) {
                String name = scanner.next();
                int level = Integer.parseInt(scanner.next());
                String province = scanner.next();
                String tradeGood = scanner.hasNext() ? scanner.next().trim() : null;

                Province provinceObj = provinceMap.get(province);
                boolean hasTradeGood = (tradeGood != null && !tradeGood.isEmpty());

                int id = cityMap.size();
                City city = City.getNewCity(name, id, level, provinceObj, hasTradeGood);
                cityNameMap.put(name, city);
                cityMap.put(Integer.valueOf(id), city);
                if(city.getCityLevel() == 0){
                    rome = city;
                }
            }
        }

        try (Scanner scanner = new Scanner(City.class.getResourceAsStream("/csv/paths.csv"))) {
            scanner.useDelimiter(";|\n");
            if (scanner.hasNextLine()) { scanner.nextLine(); }  // Skip header
            while (scanner.hasNext()) {
                String city1 = scanner.next();
                String city2 = scanner.next();
                boolean isSeaRoute = Boolean.parseBoolean(scanner.next());
                boolean isTrail = Boolean.parseBoolean(scanner.next());

                City city1Obj = cityNameMap.get(city1);
                City city2Obj = cityNameMap.get(city2);

                if (city2Obj == null) {
                    throw new IllegalArgumentException("City2Obi is null: " + city2);
                }

                Path.getNewPath(city1Obj, city2Obj, isSeaRoute, isTrail);
            }
        }

    }


    public void createSenateTeamsAndFamilies() {
        senate = new Senate();
        Family family1 = new Family("Family 1");
        Family family2 = new Family("Family 2");
        Family family3 = new Family("Family 3");
        families.add(family1);
        families.add(family2);
        families.add(family3);
        int i = 0;
        for (Family family : families) {
            for (int j = 0; j < 3; j++) {
                new Team("Team " + i, family, rome);
                i++;
            }
        }
    }

    public boolean changeTeamName(String oldName, String newName) {
        System.out.println(oldName);
        System.out.println(newName);
        // Beispielhafte Logik zur Änderung des Teamnamens
        //Team team = teamRepository.findByName(oldName);
       // if (team != null) {
        //    team.setName(newName);
        //    teamRepository.save(team);
            return true;
        //} else {
         //   return false;
       // }
    }

    public boolean addReputation(String teamName, double reputationAmount) {
//        Team team = teamRepository.findByName(teamName);
//        if (team != null) {
//            team.setReputation(team.getReputation() + reputationAmount);
//            teamRepository.save(team);
            return true;
//        } else {
//            return false;
//        }
    }

    public GameTime getGameTime() {
        return time;
    }

    public Set<Province> getProvinces() {
        return provinces;
    }

    public City getRome() {
        return rome;
    }

    public City getCity(int id) {
        return cityMap.get(id);
    }

    public Collection<City> getCities() {
        return cityMap.values();
    }

    public Set<Family> getFamilies() {
        return families;
    }

    public Senate getSenate() {
        return senate;
    }




}
