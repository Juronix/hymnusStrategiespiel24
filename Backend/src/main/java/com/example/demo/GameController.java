package com.example.demo;

import city.City;
import com.example.demo.dto.AddReputationRequest;
import com.example.demo.dto.TeamNameChangeRequest;
import group.Family;
import group.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import time.GameTime;

import java.util.Set;

@RestController
public class GameController {

    //game Objects
    @Autowired
    GameService game;

    //Example usage
    @GetMapping("/time")
    public GameTime time() {
        return game.getTime();
    }

    @GetMapping("/test")
    public GameTime test(@RequestParam(value = "gamespeed") String gamespeed) {
        game.getTime().setGameSpeed(Float.parseFloat(gamespeed));
        return game.getTime();
    }

    @PostMapping("/settime")
    public void setTime(){
        game.getTime().setMinutesPlayed(game.getTime().getMinutesPlayed() +5);
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


    @GetMapping("/getCities")
    public Set<City> getCities() {
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
    public void setReputationMultiplier(@RequestParam(value = "teamName") String teamName, @RequestParam(value = "multiplier") String multiplier ){
        System.out.println(teamName);
        System.out.println(multiplier);
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

    @PostMapping("/resetHymnen")
    public void resetHymnen(@RequestParam(value = "teamName") String teamName){
        System.out.println(teamName);
    }

    @PostMapping("/setup")
    public void setup(){
        game.createCitiesAndPaths();
        game.createTeamsAndFamilies();
    }

}
