package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Pit;
import com.mpolder.mancala.model.idclass.PitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PitRepository extends JpaRepository<Pit, PitId> {
    List<Pit> findAllByIdGameId(UUID gameId);
}
