package com.mpolder.mancala.service;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InviteService implements IInviteService {
    @Autowired
    private InviteRepository inviteRepository;

    @Autowired
    private IGameService gameService;

    @Override
    public List<Invite> getIncomingInvites(User user) {
        return inviteRepository.findAllByInvitee(user);
    }

    @Override
    public List<Invite> getOutgoingInvites(User user) {
        return inviteRepository.findAllByInviter(user);
    }

    @Override
    public Invite createInvite(User inviter, User invitee) {
        return inviteRepository.save(new Invite(UUID.randomUUID(), inviter, invitee));
    }

    @Override
    public Invite getInvite(UUID id) {
        return inviteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invite not found"));
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
