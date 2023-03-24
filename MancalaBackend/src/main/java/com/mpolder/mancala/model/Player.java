package com.mpolder.mancala.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mpolder.mancala.model.idclass.PlayerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Player {
    @EmbeddedId
    @JsonIgnore
    private PlayerId id;
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    public Player() {
    }

    public Player(Game game, Side side, User user) {
        this.id = new PlayerId(game, side);
        this.user = user;
    }

    @JsonIgnore
    public Game getGame() {
        return id.getGame();
    }

    public Side getSide() {
        return id.getSide();
    }
}
