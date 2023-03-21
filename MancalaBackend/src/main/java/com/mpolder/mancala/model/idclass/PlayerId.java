package com.mpolder.mancala.model.idclass;

import com.mpolder.mancala.model.Side;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
public class PlayerId {
    private UUID gameId;
    private Side side;

    public PlayerId() {
    }

    public PlayerId(UUID gameId, Side side) {
        this.gameId = gameId;
        this.side = side;
    }
}