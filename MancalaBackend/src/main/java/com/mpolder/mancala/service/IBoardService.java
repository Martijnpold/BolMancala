package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;

public interface IBoardService {
    /**
     * Execute a move and return the end pit
     *
     * @param pit Pit to start moving marbles from
     * @return Pit that the last marble landed in
     */
    Pit executeMove(Player player, Board board, Pit pit);

    /**
     * Collect the given pit and the pit on the opposite side of the board
     *
     * @param player Player to tally scores to
     * @param board  Board to collect from
     * @param pit    Pit to collect from
     */
    void collectOpposites(Player player, Board board, Pit pit);

    /**
     * @param board Board to check
     * @return whether one side of the game board has no marbles remaining and the pits should be tallied
     */
    boolean shouldCollectSides(Board board);

    /**
     * Collect all pit marbles into the side's score pit
     *
     * @param board Board to collect
     */
    void collectSides(Board board);

    /**
     * @param board Board to check
     * @return Winning side of the board, null if the board is a draw
     */
    Side getWinner(Board board);

    /**
     * Prepare a new board for an upcoming game
     *
     * @param game Game to set the board for
     * @return Initialised board
     */
    Board initBoard(Game game);

    Board getBoard(Game game);

    /**
     * Get the side of the board the pit is on
     *
     * @param pit Pit to check
     * @return Pit board side
     */
    Side getPitSide(Pit pit);

    boolean isScorePit(Pit pit);

    Pit getScorePit(Board board, Side side);

    /**
     * Get the side that a pit is on, if the pit is a score pit.
     *
     * @param pit Pit to check
     * @return Pit board side, returns null if the pit is not a score pit
     */
    Side getScorePitSide(Pit pit);

    Pit getOppositePit(Board board, Pit pit);
}
