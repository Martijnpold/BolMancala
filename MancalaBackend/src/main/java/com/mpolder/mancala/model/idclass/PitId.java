package com.mpolder.mancala.model.idclass;

import com.mpolder.mancala.model.Game;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class PitId {
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;
    @Column(nullable = false)
    private int pitIndex;

    public PitId() {
    }

    public PitId(Game game, int pitIndex) {
        this.game = game;
        this.pitIndex = pitIndex;
    }
}
