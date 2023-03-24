package com.mpolder.mancala.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueInviteCombination", columnNames = {"inviter", "invitee"})})
public class Invite {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne
    @JoinColumn(name = "inviter")
    private User inviter;
    @ManyToOne
    @JoinColumn(name = "invitee")
    private User invitee;

    public Invite() {
    }

    public Invite(UUID uuid, User inviter, User invitee) {
        this.id = uuid;
        this.inviter = inviter;
        this.invitee = invitee;
    }
}
