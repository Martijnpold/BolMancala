package com.mpolder.mancala.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Invite {
    @EmbeddedId
    private Invite id;

    public String getInviter() {
        return id.getInviter();
    }

    public String getInvitee() {
        return id.getInvitee();
    }
}
