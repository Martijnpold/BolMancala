package com.mpolder.mancala.controller;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.model.dto.InviteDTO;
import com.mpolder.mancala.service.IGameService;
import com.mpolder.mancala.service.IInviteService;
import com.mpolder.mancala.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/current/invites")
public class SelfInviteController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGameService gameService;
    @Autowired
    private IInviteService inviteService;

    @GetMapping()
    public List<Invite> invitesIn(@AuthenticationPrincipal OAuth2User principal) {
        return inviteService.getIncomingInvites(getUser(principal));
    }

    @PostMapping()
    public Invite invite(@AuthenticationPrincipal OAuth2User principal, @RequestBody InviteDTO dto) {
        User inviter = getUser(principal);
        User invitee = userService.getByEmail(dto.getInvitee());
        return inviteService.createInvite(inviter, invitee);
    }

    @GetMapping("/{id}")
    public Invite inviteDetails(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id) {
        User self = getUser(principal);
        return getInvite(self, id);
    }

    @PostMapping("/{id}/accept")
    public Game inviteAccept(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id) {
        User self = getUser(principal);
        Invite invite = getInvite(self, id);
        return gameService.initGame(invite);
    }

    @PostMapping("/{id}/reject")
    public void inviteReject(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id) {
        inviteCancel(principal, id);
    }

    @PostMapping("/{id}/cancel")
    public void inviteCancel(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id) {
        User self = getUser(principal);
        Invite invite = getInvite(self, id);
        inviteService.removeInvite(invite);
    }

    @GetMapping("/out")
    public List<Invite> invitesOut(@AuthenticationPrincipal OAuth2User principal) {
        return inviteService.getOutgoingInvites(getUser(principal));
    }

    private User getUser(OAuth2User principal) {
        return userService.getByEmail(principal.getAttribute("email"));
    }

    public Invite getInvite(User self, UUID id) {
        Invite invite = inviteService.getInvite(id);
        if (invite.getInviter().equals(self) || invite.getInvitee().equals(self)) {
            return invite;
        } else {
            throw new ResourceNotFoundException("Invite not found");
        }
    }
}
