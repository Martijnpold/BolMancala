package com.mpolder.mancala.controller;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private IGameService gameService;

    @GetMapping()
    public List<Game> getGames() {
        return gameService.getGames();
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable UUID id) {
        return gameService.getGame(id);
    }

    @PutMapping("/create")
    public Game createGame() {
        return gameService.initGame();
    }
}
