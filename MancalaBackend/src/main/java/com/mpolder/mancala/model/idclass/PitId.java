package com.mpolder.mancala.model.idclass;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.UUID;

@Data
@Embeddable
public class PitId {
    private UUID gameId;
    private int index;
}
