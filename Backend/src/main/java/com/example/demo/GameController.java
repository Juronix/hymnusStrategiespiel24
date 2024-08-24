package com.example.demo;

import city.City;
import database.Database;

import com.example.demo.dto.AddReputationRequest;
import com.example.demo.dto.SetReputationMultiplier;
import com.example.demo.dto.TeamNameChangeRequest;
import com.example.demo.dto.ResetHymnenRequest;
import group.Family;
import group.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import time.GameTime;
import time.GameTimer;

import java.util.Set;
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
    public Set<Family> getFamilies() {
        return game.getFamilies();
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
        double add = 1.0;
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

    @PostMapping("/setPath")
    public void setPath(@RequestParam(value = "teamName") String teamName, @RequestParam(value = "city1") String city1, @RequestParam(value = "city2") String city2, @RequestParam(value = "tradeUnits") String tradeUnits){
        System.out.println(teamName);
        System.out.println(city1);
        System.out.println(city2);
        System.out.println(tradeUnits);
    }

    @PostMapping("/buildTradePost")
    public void buildTradePost(@RequestParam(value = "teamName") String teamName, @RequestParam(value = "cityId") String cityId ){
        System.out.println(teamName);
        System.out.println(cityId);
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
            Database.loadDatabase(1);
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


    //TODO: getCitysToTravelTo

}
