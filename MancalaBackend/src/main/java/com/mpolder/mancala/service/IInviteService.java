package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;

import java.util.List;
import java.util.UUID;

public interface IInviteService {
    List<Invite> getIncomingInvites(User user);

    List<Invite> getOutgoingInvites(User user);

    Invite createInvite(User inviter, User invitee);

    Invite getInvite(UUID id);

    Game acceptInvite(Invite invite);

    void removeInvite(Invite invite);
}
