package com.mpolder.mancala.controller;

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

@RestController
@RequestMapping("/api/users/current")
public class CurrentUserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGameService gameService;
    @Autowired
    private IInviteService inviteService;

    @GetMapping("")
    public User user(@AuthenticationPrincipal OAuth2User principal) {
        return getUser(principal);
    }

    @GetMapping("/games")
    public List<Game> games(@AuthenticationPrincipal OAuth2User principal) {
        return gameService.getGames(getUser(principal));
    }

    @GetMapping("/invites")
    public List<Invite> invitesIn(@AuthenticationPrincipal OAuth2User principal) {
        return inviteService.getIncomingInvites(getUser(principal));
    }

    @PostMapping("/invites")
    public Invite invite(@AuthenticationPrincipal OAuth2User principal, @RequestBody InviteDTO dto) {
        User inviter = getUser(principal);
        User invitee = userService.getByEmail(dto.getInvitee());
        return inviteService.createInvite(inviter, invitee);
    }

    @GetMapping("/invites/out")
    public List<Invite> invitesOut(@AuthenticationPrincipal OAuth2User principal) {
        return inviteService.getOutgoingInvites(getUser(principal));
    }

    private User getUser(OAuth2User principal) {
        return userService.getByEmail(principal.getAttribute("email"));
    }
}
