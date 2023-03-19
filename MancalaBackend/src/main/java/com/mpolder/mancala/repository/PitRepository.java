package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Pit;
import com.mpolder.mancala.model.idclass.PitId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitRepository extends CrudRepository<Pit, PitId> {
}
