package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Player;
import com.mpolder.mancala.model.idclass.PlayerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, PlayerId> {
    List<Player> findAllByIdGameId(UUID gameId);
}
