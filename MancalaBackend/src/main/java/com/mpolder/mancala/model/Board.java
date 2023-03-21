package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PitId;
import lombok.Data;

import java.util.UUID;

@Data
public class Board {
    private UUID gameId;
    private Pit[] pits;

    public Board(UUID gameId, Pit[] pits) {
        this.gameId = gameId;
        this.pits = pits;
    }

    public static Board build(UUID gameId) {
        Pit[] pits = new Pit[14];
        for (int i = 0; i < pits.length; i++) {
            int marbles = i % 7 == 0 ? 0 : 6;
            pits[i] = new Pit(new PitId(gameId, i), marbles);
        }
        return new Board(gameId, pits);
    }
}
