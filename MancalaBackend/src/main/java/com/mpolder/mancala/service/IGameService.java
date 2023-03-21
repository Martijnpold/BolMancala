package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Board;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Pit;
import com.mpolder.mancala.model.Player;

import java.util.UUID;

public interface IGameService {
    Game getGame(UUID id);

    void tryDoTurn(Game game, Player player, Board board, Pit pit);

    boolean isValidTurn(Game game, Player player, Pit pit);

    /**
     * Returns whether a turn should be ended after playing the last marble
     *
     * @param player Player that played the turn
     * @param pit    Pit the last marble landed in
     */
    boolean shouldEndTurn(Player player, Pit pit);
}
