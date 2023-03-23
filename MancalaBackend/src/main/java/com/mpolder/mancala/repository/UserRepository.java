package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
