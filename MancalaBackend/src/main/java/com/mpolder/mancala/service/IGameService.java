package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;

import java.util.List;
import java.util.UUID;

public interface IGameService {
    List<Game> getGames();

    List<Game> getGames(User user);

    Game getGame(UUID id);

    Game getGame(User user, UUID id);

    Game initGame(Invite invite);

    boolean tryDoTurn(Game game, Player player, Board board, Pit pit);

    boolean isValidTurn(Game game, Player player, Pit pit);

    /**
     * Returns whether a turn should be ended after playing the last marble
     *
     * @param pit    Pit the last marble landed in
     */
    boolean shouldEndTurn(Pit pit);
}
