package com.mpolder.mancala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Game {
    @Id
    private UUID id;
    private Side turn;
    private Side winner;
}
