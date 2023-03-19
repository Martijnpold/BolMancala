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
}
