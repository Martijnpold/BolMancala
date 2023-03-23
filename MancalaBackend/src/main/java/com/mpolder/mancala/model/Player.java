package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PlayerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Player {
    @EmbeddedId
    private PlayerId id;
    private String userEmail;

    public Player() {
    }

    public Player(Game game, Side side, String userEmail) {
        this.id = new PlayerId(game.getId(), side);
        this.userEmail = userEmail;
    }

    public Side getSide() {
        return id.getSide();
    }
}
