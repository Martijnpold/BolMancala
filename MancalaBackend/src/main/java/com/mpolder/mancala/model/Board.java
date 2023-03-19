package com.mpolder.mancala.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Board {
    private UUID gameId;
    private Pit[] pits;
}
