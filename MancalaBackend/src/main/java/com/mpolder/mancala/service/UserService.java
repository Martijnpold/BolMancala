package com.mpolder.mancala.service;

import com.mpolder.mancala.model.User;
import com.mpolder.mancala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerIfMissing(User user) {
        if (!userRepository.existsById(user.getEmail())) {
            user = userRepository.save(user);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new RuntimeException("email not found :("));
    }
}
