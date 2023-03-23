package com.mpolder.mancala.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    private String email;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }
}
