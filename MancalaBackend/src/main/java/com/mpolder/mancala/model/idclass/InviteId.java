package com.mpolder.mancala.model.idclass;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class InviteId {
    private String inviter;
    private String invitee;

    public InviteId() {
    }

    public InviteId(String inviter, String invitee) {
        this.inviter = inviter;
        this.invitee = invitee;
    }
}
