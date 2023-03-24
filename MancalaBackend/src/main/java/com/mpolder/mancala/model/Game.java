package com.mpolder.mancala.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

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

    public Game() {
    }
}
