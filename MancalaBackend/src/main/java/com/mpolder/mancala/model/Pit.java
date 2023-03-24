package com.mpolder.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpolder.mancala.model.idclass.PitId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pit {
    @JsonIgnore
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

    @JsonIgnore
    public Game getGame() {
        return id.getGame();
    }

    public int getIndex() {
        return id.getPitIndex();
    }

    public void addMarbles(int amount) {
        marbles += amount;
    }

    public void clearMarbles() {
        marbles = 0;
    }
}
