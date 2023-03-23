package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;

import java.util.List;

public interface IInviteService {
    List<Invite> getIncomingInvites(User user);

    List<Invite> getOutgoingInvites(User user);

    Invite getInvite(User inviter, User invitee);

    Game acceptInvite(Invite invite);

    void removeInvite(Invite invite);
}
