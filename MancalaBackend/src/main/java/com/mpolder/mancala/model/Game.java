package com.mpolder.mancala.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Game {
    @Id
    private UUID id = UUID.randomUUID();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Side turn = Side.TOP;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Side winner = null;

    @OneToMany()
    @JoinColumn(name="gameId")
    private Set<Player> players;

    public Game() {
    }
}
