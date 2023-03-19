package com.mpolder.mancala.model;

import com.mpolder.mancala.model.idclass.PitId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Pit {
    @EmbeddedId
    private PitId id;
    private int marbles;
}
