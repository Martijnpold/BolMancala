package com.mpolder.mancala.model.idclass;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Side;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class PlayerId {
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;
    private Side side;

    public PlayerId() {
    }

    public PlayerId(Game game, Side side) {
        this.game = game;
        this.side = side;
    }
}
