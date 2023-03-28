package com.mpolder.mancala.service;


import com.mpolder.mancala.exception.SelfInviteException;
import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.Invite;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.repository.InviteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InviteServiceTest {
    @Mock
    private IGameService mockGameService;
    @Mock
    private InviteRepository mockInviteRepository;
    @InjectMocks
    private InviteService inviteService;

    @Test
    public void assertGetIncomingInvitesAccessesRepository() {
        User user = new User("test", "test");
        List<Invite> invites = Arrays.asList(
                new Invite(UUID.randomUUID(), new User("t1", "t1"), user),
                new Invite(UUID.randomUUID(), new User("t2", "t2"), user)
        );
        when(mockInviteRepository.findAllByInvitee(user)).thenReturn(invites);

        List<Invite> returned = inviteService.getIncomingInvites(user);

        verify(mockInviteRepository, times(1)).findAllByInvitee(user);
        assertEquals(invites, returned);
    }

    @Test
    public void assertGetOutgoingInvitesAccessesRepository() {
        User user = new User("test", "test");
        List<Invite> invites = Arrays.asList(
                new Invite(UUID.randomUUID(), user, new User("t1", "t1")),
                new Invite(UUID.randomUUID(), user, new User("t2", "t2"))
        );
        when(mockInviteRepository.findAllByInviter(user)).thenReturn(invites);

        List<Invite> returned = inviteService.getOutgoingInvites(user);

        verify(mockInviteRepository, times(1)).findAllByInviter(user);
        assertEquals(invites, returned);
    }

    @Test
    public void assertCreateInviteInsertsInvite() {
        User user1 = new User("t1", "t1");
        User user2 = new User("t2", "t2");
        Invite expected = new Invite(UUID.randomUUID(), user1, user2);
        when(mockInviteRepository.save(any())).thenReturn(expected);

        Invite created = inviteService.createInvite(user1, user2);

        assertEquals(expected, created);
        assertEquals(user1, created.getInviter());
        assertEquals(user2, created.getInvitee());
    }

    @Test
    public void assertCreateInviteOnSelfThrowsException() {
        User user1 = new User("t1", "t1");
        assertThrows(SelfInviteException.class, () -> inviteService.createInvite(user1, user1));
    }

    @Test
    public void assertGetInviteAccessesRepository() {
        Invite invite = new Invite(UUID.randomUUID(), new User("t1", "t1"), new User("t2", "t2"));
        when(mockInviteRepository.findById(invite.getId())).thenReturn(Optional.of(invite));

        Invite returned = inviteService.getInvite(invite.getId());

        verify(mockInviteRepository, times(1)).findById(returned.getId());
        assertEquals(invite, returned);
    }

    @Test
    public void assertGetInvalidInviteThrowsException() {
        UUID id = UUID.randomUUID();
        assertThrows(ResourceNotFoundException.class, () -> inviteService.getInvite(id), "Invite not found");
    }

    @Test
    public void assertAcceptInviteInitializesGame() {
        Invite invite = new Invite(UUID.randomUUID(), new User("t1", "t1"), new User("t2", "t2"));

        inviteService.acceptInvite(invite);

        verify(mockInviteRepository, times(1)).delete(invite);
        verify(mockGameService, times(1)).initGame(invite);
    }

    @Test
    public void assertRemoveInviteAccessesRepository() {
        Invite invite = new Invite(UUID.randomUUID(), new User("t1", "t1"), new User("t2", "t2"));

        inviteService.removeInvite(invite);

        verify(mockInviteRepository, times(1)).delete(invite);
    }
}
