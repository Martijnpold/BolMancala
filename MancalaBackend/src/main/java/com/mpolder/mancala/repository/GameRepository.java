package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends CrudRepository<Game, UUID> {
}
