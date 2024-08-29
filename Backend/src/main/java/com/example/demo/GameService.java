package com.example.demo;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import database.Database;
import org.springframework.stereotype.Service;

import city.City;
import city.Province;
import group.Family;
import group.Senate;
import group.Team;
import path.Path;
import time.GameTime;
import tradeUnit.TradeUnit;


@Service
public class GameService implements Serializable {

    private static final long serialVersionUID = 1L;

    private GameTime time = new GameTime();
    private Set<Province> provinces = new HashSet<>();
    private City rome;
    private Senate senate = new Senate();
    private Map<Integer, City> cityMap = new HashMap<>();
    private List<Family> families = new ArrayList<>(3);

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

    public static GameService reloadStaticGameService(GameService gameService) {
        return GameService.gameService = gameService;
    }

    public void setupGame() {
        createCitiesAndPaths();
        createSenateTeamsAndFamilies();
        new Database(1, this);
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

                String isSeaRouteString = scanner.next();
                String isTrailString = scanner.next();

                boolean isSeaRoute = isSeaRouteString.equals("1");
                boolean isTrail = isTrailString.contains("1");

                City city1Obj = cityNameMap.get(city1);
                City city2Obj = cityNameMap.get(city2);

                if (city2Obj == null) {
                    throw new IllegalArgumentException("City2Obi is null: " + city2);
                }

                Path.getNewPath(city1Obj, city2Obj, isSeaRoute, isTrail);
            }
        }

        City.refreshDistancesToRome(cityMap.values(), rome);

    }


    public void createSenateTeamsAndFamilies() {
        senate = new Senate();
        Family family1 = new Family(1, "Metellier");
        Family family2 = new Family(2, "Tullier");
        Family family3 = new Family(3, "Scipionen");
        families.add(family1);
        families.add(family2);
        families.add(family3);
        int i = 0;
        for (Family family : families) {
            for (int j = 0; j < 3; j++) {
                new Team("Factio " + (i + 1), i, family, rome);
                i++;
            }
        }
    }



    public GameTime getTime() {
        return time;
    }

    public boolean changeTeamName(int id, String newName) {
        Team team = this.getTeamById(id);
        if (team != null) {
            team.setName(newName);
            return true;
        } else {
            return false;
        }
    }

    private Team getTeamById(int id){
        for(Family family : families) {
            for (Team team : family.getTeams()) {
                if(team.getId() == id){
                    return team;
                }
            }
        }
        return null;
    }

    public boolean addReputation(int id, double reputation) {
        System.out.println(reputation);
        Team team = this.getTeamById(id);
        if (team != null) {
            team.addReputation(reputation);
            return true;
        } else {
            return false;
        }
    }

    public boolean setMultiplier(int id, double multiplier) {
        Team team = this.getTeamById(id);
        if (team != null) {
            team.setAdditionalReputationMultiplier(multiplier);
            return true;
        } else {
            return false;
        }
    }

    public boolean resetHymnen(int id) {
        Team team = this.getTeamById(id);
        if (team != null) {
            double oldHymnen = team.getHymnen();
            team.setHymnen(oldHymnen % 100);
            return true;
        } else {
            return false;
        }
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

    public boolean buildCity(int id, int cityId) {
        Team team = this.getTeamById(id);
        if (team != null) {
            City city = this.getCity(cityId);
            team.createTradePost(city);
            return true;
        } else {
            return false;
        }
    }

    public List<Family> getFamilies() {
        return families;
    }

    public Family getFamilyByName(String name) {
        for (Family fam :
                families) {
            if(fam.getName().equals(name)){
                return fam;
            }
        }
        return null;
    }

    public Family getFamilyById(int id) {
        for (Family fam :
                families) {
            if(fam.getId() == id){
                return fam;
            }
        }
        return null;
    }


    public Senate getSenate() {
        return senate;
    }

    public Map<Integer, City> getCityMap() {
        return cityMap;
    }

    public boolean createNewTradeUnit(boolean isLandTradeUnit, int unitLevel, int teamId, int cityId1, int cityId2) {
        Path path = getPath(cityId1, cityId2);
        Team team = getTeamById(teamId);
        TradeUnit.createNewTradeUnit(isLandTradeUnit, unitLevel, team, path);
        return true;
    }

    public Path getPath(int cityId1, int cityId2) {
        City city1 = cityMap.get(cityId1);
        City city2 = cityMap.get(cityId2);

        for (Path path : city1.getPaths()) {
            if (path.getOtherCity(city1).equals(city2)) {
                return path;
            }
        }
        return null;
    }

    public List<City> getCitiesToTradeTo(int teamId) {
        Team team = this.getTeamById(teamId);
        return team.getTeamsGraph().getCitiesToTradeTo();
    }
    public List<City> getCitiesToTradeTo(int teamId, boolean isLandTradeUnit) {
        Team team = this.getTeamById(teamId);
        return team.getTeamsGraph().getCitiesToTradeTo(isLandTradeUnit);
    }
    public List<City> getCitiesToTradeTo(int teamId, boolean isLandTradeUnit, int cityId) {
        Team team = this.getTeamById(teamId);
        City city= this.getCity(cityId);
        return team.getTeamsGraph().getCitiesToTradeTo(isLandTradeUnit, city);
    }

    public boolean setFamilyOfPolitician(String familyId, int polititanId) {
        int famId = Integer.valueOf(familyId);
        Family fam = getFamilyById(famId);
        switch(polititanId) {
            case 1:
                getSenate().setFamilyOfPolitician1(fam);
                break;
            case 2:
                getSenate().setFamilyOfPolitician2(fam);
                break;
            case 3:
                getSenate().setFamilyOfPolitician3(fam);
                break;
            case 4:
                getSenate().setFamilyOfPolitician4(fam);
                break;
            case 5:
                getSenate().setFamilyOfPolitician5(fam);
                break;
            case 6:
                getSenate().setFamilyOfPolitician6(fam);
                break;
            case 7:
                getSenate().setFamilyOfPolitician7(fam);
                break;
            case 8:
                getSenate().setFamilyOfPolitician8(fam);
                break;
        }


        return true;
    }
}
