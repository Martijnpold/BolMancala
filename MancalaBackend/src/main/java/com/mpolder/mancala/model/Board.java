package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PitId;
import lombok.Data;

@Data
public class Board {
    private Game game;
    private Pit[] pits;

    public Board() {
    }

    public Board(Game game, Pit[] pits) {
        this.game = game;
        this.pits = pits;
    }

    public static Board build(Game game) {
        Pit[] pits = new Pit[14];
        for (int i = 0; i < pits.length; i++) {
            int marbles = (i + 1) % 7 == 0 ? 0 : 6;
            pits[i] = new Pit(game, i, marbles);
        }
        return new Board(game, pits);
    }
}
