package com.mpolder.mancala.model.idclass;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
public class PitId {
    private final UUID gameId;
    private final int index;

    public PitId(UUID gameId, int index) {
        this.gameId = gameId;
        this.index = index;
    }
}
