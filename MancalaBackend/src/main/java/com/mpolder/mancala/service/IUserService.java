package com.mpolder.mancala.service;

import com.mpolder.mancala.model.User;

public interface IUserService {
    User registerIfMissing(User user);

    User getByEmail(String email);
}
