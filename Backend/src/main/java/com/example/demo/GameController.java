package com.example.demo;

import city.City;
import com.example.demo.dto.*;
import database.Database;

import group.Family;
import group.Senate;
import group.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import time.GameTime;
import time.GameTimer;

import java.util.List;
import java.util.Collection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class GameController {

    //game Objects
    @Autowired
    GameService game;

    //Example usage
    @GetMapping("/time")
    public GameTime time() {
        return game.getGameTime();
    }

    @GetMapping("/test")
    public GameTime test(@RequestParam(value = "gamespeed") String gamespeed) {
        game.getGameTime().setGameSpeed(Float.parseFloat(gamespeed));
        return game.getGameTime();
    }

    @PostMapping("/settime")
    public void setTime(){
        game.getGameTime().setMinutesPlayed(game.getGameTime().getMinutesPlayed() +5);
    }

    @GetMapping("/getFamilies")
    public List<Family> getFamilies() {
        return game.getFamilies();
    }

    @GetMapping("/getSenate")
    public Senate getSenate() {
        return game.getSenate();
    }

    @PostMapping("/setFamilyOfPolitician")
    public ResponseEntity<String> setFamilyOfPolitician(@RequestBody FamilyPoliticianRequest request) {
        boolean success = game.setFamilyOfPolitician(request.getFamilyId(), request.getPoliticianId());
        if (success) {
            return ResponseEntity.ok("family of politician updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update family of politician");
        }
    }

    @PostMapping("/testInfluence")
    public void testInfluence(){
        double add = 1.0;
        for (Family family : game.getFamilies()) {
            family.setAdditionalReputation(add++);
            for (Team team : family.getTeams()) {
                team.setReputation(add++);
            }
        }
    }

    @PostMapping("/testhymnen")
    public void testHymnen(){
        double add = 100.0;
        for (Family family : game.getFamilies()) {
            for (Team team : family.getTeams()) {
                team.setHymnen(add++);
            }
        }
    }

    @PostMapping("/resetHymnen")
    public ResponseEntity<String> resetHymnen(@RequestBody ResetHymnenRequest request){
        boolean success = game.resetHymnen(request.getId());

        if (success) {
            return ResponseEntity.ok("Team name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update team name");
        }
    }

    @GetMapping("/getCities")
    public Collection<City> getCities() {
        return game.getCities();
    }

    @PostMapping("/buildCity")
    public ResponseEntity<String> buildCity(@RequestBody BuildCityRequest request) {
        boolean success = game.buildCity(request.getId(), request.getCityId());

        if (success) {
            return ResponseEntity.ok("Team name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update team name");
        }
    }

    @PostMapping("/setTeamName")
    public ResponseEntity<String> changeTeamName(@RequestBody TeamNameChangeRequest request) {
        boolean success = game.changeTeamName(request.getId(), request.getNewName());

        if (success) {
            return ResponseEntity.ok("Team name updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update team name");
        }
    }

    @PostMapping("/addReputation")
    public ResponseEntity<String> addReputation(@RequestBody AddReputationRequest request) {
        boolean success = game.addReputation(request.getId(), request.getReputation());

        if (success) {
            return ResponseEntity.ok("Reputation added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add reputation");
        }
    }

    @PostMapping("/setReputationMultiplier")
    public ResponseEntity<String> setReputationMultiplier(@RequestBody SetReputationMultiplier request){
        boolean success = game.setMultiplier(request.getId(), request.getMultiplier());

        if (success) {
            return ResponseEntity.ok("SetReputationMultiplier successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to set reputation multiplier");
        }
    }

    @PostMapping("/buildTradePost")
    public ResponseEntity<String> buildTradePost(@RequestParam(value = "teamId") String teamId, @RequestParam(value = "cityId") String cityId ){
        //todo
        boolean success = true;
        if (success) {
            return ResponseEntity.ok("SetReputationMultiplier successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to set reputation multiplier");
        }
    }

    @PostMapping("/createNewTradeUnit")
    public ResponseEntity<String> createNewTradeUnit(@RequestBody CreateNewTradeUnitRequest request){
        boolean success = game.createNewTradeUnit(request.getIsLandTradeUnit(), request.getUnitLevel(), request.getTeamId(), request.getCityId1(), request.getCityId2());
        if (success) {
            return ResponseEntity.ok("createNewTradeUnit successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to create New Trade Unit");
        }
    }

    @GetMapping("/getCitiesToTradeTo")
    public List<City> getCitiesToTradeTo(@RequestParam(value = "teamId") int teamId) {
        return game.getCitiesToTradeTo(teamId);
    }
    @GetMapping("/getCitiesToTradeTo2")
    public List<City> getCitiesToTradeTo(@RequestParam(value = "teamId") int teamId, @RequestParam(value = "isLandTradeUnit") boolean isLandTradeUnit) {
        return game.getCitiesToTradeTo(teamId, isLandTradeUnit);
    }
    @GetMapping("/getCitiesToTradeTo3")
    public List<City> getCitiesToTradeTo(@RequestParam(value = "teamId") int teamId, @RequestParam(value = "isLandTradeUnit") boolean isLandTradeUnit, @RequestParam(value = "cityId") int cityId) {
        return game.getCitiesToTradeTo(teamId, isLandTradeUnit, cityId);
    }

    //initial call to setup new game
    @PostMapping("/setup")  
    public void setup(){
        game.setupGame();
    }

    // initial call to load and setup game
    @PostMapping("/loadGame") 
    public void loadGame(){
        try {
        //    game = Database.loadDatabase(1);
            game = Database.loadSafetySaveDatabse(1, 1, 40);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // call to close game
    @PostMapping("/closeGame")
    public void closeGame(){
        pauseGame();
        saveGame();
        //TODO close game
    }


    @PostMapping("/saveGame")
    public void saveGame() {
        Database.resaveDatabase();
    }

    @PostMapping("/resumeGame")
    public void resumeGame() {
        GameTimer.start();
    }

    @PostMapping("/pauseGame")
    public void pauseGame() {
        GameTimer.stop();
    }




}
