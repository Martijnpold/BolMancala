package com.mpolder.mancala.model;


import com.mpolder.mancala.model.idclass.InviteId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Invite {
    @EmbeddedId
    private InviteId id;

    public Invite(User inviter, User invitee) {
        this.id = new InviteId(inviter, invitee);
    }

    public User getInviter() {
        return id.getInviter();
    }

    public User getInvitee() {
        return id.getInvitee();
    }
}
