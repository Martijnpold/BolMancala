package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Player;
import com.mpolder.mancala.model.Side;
import com.mpolder.mancala.model.idclass.PlayerId;
import com.mpolder.mancala.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PlayerService implements IPlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void initPlayers(Game game) {
        playerRepository.saveAll(Arrays.asList(
                new Player(new PlayerId(game.getId(), Side.TOP)),
                new Player(new PlayerId(game.getId(), Side.BOTTOM))
        ));
    }

    @Override
    public List<Player> getPlayers(Game game) {
        return playerRepository.findAllByIdGameId(game.getId());
    }
}
