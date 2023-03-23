package com.mpolder.mancala.model.idclass;

import com.mpolder.mancala.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class InviteId {
    @ManyToOne
    @JoinColumn(name = "inviter")
    private User inviter;
    @ManyToOne
    @JoinColumn(name = "invitee")
    private User invitee;

    public InviteId() {
    }

    public InviteId(User inviter, User invitee) {
        this.inviter = inviter;
        this.invitee = invitee;
    }
}
