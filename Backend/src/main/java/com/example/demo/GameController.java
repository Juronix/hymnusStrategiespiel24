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
}
