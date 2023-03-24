package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;
import com.mpolder.mancala.repository.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository mockPlayerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    public void assertInitPlayersSavesToRepository() {
        Game game = new Game();
        User inviter = new User("user1", "user1");
        User invitee = new User("user2", "user2");
        Invite invite = new Invite(UUID.randomUUID(), inviter, invitee);

        playerService.initPlayers(game, invite);

        verify(mockPlayerRepository, times(1)).saveAll(Arrays.asList(
                new Player(game, Side.TOP, invite.getInviter()),
                new Player(game, Side.BOTTOM, invite.getInvitee())
        ));
    }

    @Test
    public void assertGetPlayersAccessesRepository() {
        Game game = new Game();
        List<Player> players = Arrays.asList(
                new Player(game, Side.TOP, new User("t1", "t1")),
                new Player(game, Side.BOTTOM, new User("t2", "t2"))
        );

        when(mockPlayerRepository.findAllByIdGameId(game.getId())).thenReturn(players);
        List<Player> returned = playerService.getPlayers(game);

        verify(mockPlayerRepository, times(1)).findAllByIdGameId(game.getId());
        assertEquals(players, returned);
    }
}
