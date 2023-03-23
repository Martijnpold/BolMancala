package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PlayerId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Player {
    @EmbeddedId
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

    public Side getSide() {
        return id.getSide();
    }
}
