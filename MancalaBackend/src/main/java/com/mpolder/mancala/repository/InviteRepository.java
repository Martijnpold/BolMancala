package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.idclass.InviteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, InviteId> {
    List<Invite> findAllByIdInviter(String id);

    List<Invite> findAllByIdInvitee(String id);
}
