package com.mpolder.mancala.repository;

import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InviteRepository extends JpaRepository<Invite, UUID> {
    List<Invite> findAllByInviter(User user);

    List<Invite> findAllByInvitee(User user);
}
