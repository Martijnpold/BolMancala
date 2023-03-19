package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Player;
import com.mpolder.mancala.model.idclass.PlayerId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, PlayerId> {
}
