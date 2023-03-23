package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PitId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pit {
    @EmbeddedId
    private PitId id;
    @Column(nullable = false)
    private int marbles;

    public Pit() {
    }

    public Pit(Game game, int index, int marbles) {
        this.id = new PitId(game, index);
        this.marbles = marbles;
    }

    public void addMarbles(int amount) {
        marbles += amount;
    }

    public void clearMarbles() {
        marbles = 0;
    }
}
