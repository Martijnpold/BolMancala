package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.Player;

import java.util.List;

public interface IPlayerService {
    void initPlayers(Game game, Invite invite);

    List<Player> getPlayers(Game game);
}
