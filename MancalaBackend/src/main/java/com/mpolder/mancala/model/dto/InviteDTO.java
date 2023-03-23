package com.mpolder.mancala.model.dto;

import lombok.Data;

@Data
public class InviteDTO {
    private String invitee;

    public InviteDTO() {
    }

    public InviteDTO(String invitee) {
        this.invitee = invitee;
    }
}
