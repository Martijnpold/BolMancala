package com.mpolder.mancala.service;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.model.idclass.InviteId;
import com.mpolder.mancala.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService implements IInviteService {
    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private IGameService gameService;

    @Override
    public List<Invite> getIncomingInvites(User user) {
        return inviteRepository.findAllByIdInvitee(user.getEmail());
    }

    @Override
    public List<Invite> getOutgoingInvites(User user) {
        return inviteRepository.findAllByIdInviter(user.getEmail());
    }

    @Override
    public Invite createInvite(User inviter, User invitee) {
        return inviteRepository.save(new Invite(inviter, invitee));
    }

    @Override
    public Invite getInvite(User inviter, User invitee) {
        return inviteRepository.findById(new InviteId(inviter, invitee)).orElseThrow(() -> new ResourceNotFoundException("Invite not found"));
    }

    @Override
    public Game acceptInvite(Invite invite) {
        removeInvite(invite);
        return gameService.initGame(invite);
    }

    @Override
    public void removeInvite(Invite invite) {
        inviteRepository.delete(invite);
    }
}
