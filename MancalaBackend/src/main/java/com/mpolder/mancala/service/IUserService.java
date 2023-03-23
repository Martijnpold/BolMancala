package com.mpolder.mancala.service;

import com.mpolder.mancala.auth.MancalaOAuth2User;
import com.mpolder.mancala.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IUserService extends UserDetailsService {
    void processOAuthLogin(MancalaOAuth2User user);

    User registerIfMissing(User user);

    User getUser(OAuth2User oauth);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
