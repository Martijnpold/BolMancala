package com.mpolder.mancala.controller;

import com.mpolder.mancala.model.User;
import com.mpolder.mancala.service.IGameService;
import com.mpolder.mancala.service.IInviteService;
import com.mpolder.mancala.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/current")
public class SelfUserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IGameService gameService;
    @Autowired
    private IInviteService inviteService;

    @GetMapping()
    public User user(@AuthenticationPrincipal OAuth2User principal) {
        return getUser(principal);
    }

    private User getUser(OAuth2User principal) {
        return userService.getByEmail(principal.getAttribute("email"));
    }
}
