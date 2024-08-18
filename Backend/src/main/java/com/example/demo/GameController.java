package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import time.GameTime;

@RestController
public class GameController {

    //game Objects
    @Autowired
    GameService game;

    //Example usage
    @GetMapping("/time")
    public GameTime time() {
        return game.time;
    }

    @GetMapping("/test")
    public GameTime test(@RequestParam(value = "gamespeed") String gamespeed) {
        game.time.setGameSpeed(Float.parseFloat(gamespeed));
        return game.time;
    }

    @PostMapping("/settime")
    public void setTime(){
        game.time.setMinutesPlayed(game.time.getMinutesPlayed() +5);
    }
}
