package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT g FROM Game g INNER JOIN Player p ON p.id.game = g.id WHERE p.user = :user")
    List<Game> findAllPlayerGames(User user);

    @Query("SELECT g FROM Player p INNER JOIN Game g ON p.id.game = g.id WHERE g.id = :id AND p.user = :user")
    Optional<Game> findByUserAndId(User user, UUID id);
}
