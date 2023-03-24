package com.mpolder.mancala.model.dto;

import lombok.Data;

@Data
public class MoveDTO {
    private int pitIndex;

    public MoveDTO() {
    }

    public MoveDTO(int pitIndex) {
        this.pitIndex = pitIndex;
    }
}
