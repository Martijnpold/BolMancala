package com.mpolder.mancala.service;

import com.mpolder.mancala.auth.MancalaOAuth2User;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void processOAuthLogin(MancalaOAuth2User user) {
        if (!userRepository.existsById(user.getEmail())) {
            userRepository.save(new User(user.getEmail()));
        }
    }

    @Override
    public User registerIfMissing(User user) {
        return null;
    }

    @Override
    public User getUser(OAuth2User oauth) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
