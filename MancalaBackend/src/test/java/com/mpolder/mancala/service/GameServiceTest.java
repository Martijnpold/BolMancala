package com.mpolder.mancala.service;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.*;
import com.mpolder.mancala.repository.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
    @Mock
    private GameRepository mockGameRepository;
    @Mock
    private IBoardService mockBoardService;
    @Mock
    private IPlayerService mockPlayerService;
    @InjectMocks
    private GameService gameService;

    @Test
    public void assertGetGamesAccessesGameRepository() {
        List<Game> created = Arrays.asList(new Game(), new Game());
        when(mockGameRepository.findAll()).thenReturn(created);

        List<Game> found = gameService.getGames();
        assertEquals(created, found);
    }

    @Test
    public void assertGetUserGamesAccessesGameRepository() {
        User user = new User("test", "test");
        List<Game> created = Arrays.asList(new Game(), new Game());
        when(mockGameRepository.findAllPlayerGames(user)).thenReturn(created);

        List<Game> found = gameService.getGames(user);
        assertEquals(created, found);
    }

    @Test
    public void assertGetGameAccessesGameRepository() {
        Game created = new Game();
        when(mockGameRepository.findById(created.getId())).thenReturn(Optional.of(created));

        Game found = gameService.getGame(created.getId());
        assertEquals(created, found);
    }

    @Test
    public void assertGetInvalidGameThrowsException() {
        Game created = new Game();
        assertThrows(ResourceNotFoundException.class, () -> gameService.getGame(created.getId()), "Game not found");
    }

    @Test
    public void assertGetInvalidUserGameAccessesGameRepository() {
        User user = new User("test", "test");
        Game created = new Game();
        when(mockGameRepository.findByUserAndId(user, created.getId())).thenReturn(Optional.of(created));

        Game found = gameService.getGame(user, created.getId());
        assertEquals(created, found);
    }

    @Test
    public void assertGetUserGameThrowsException() {
        Game created = new Game();
        assertThrows(ResourceNotFoundException.class, () -> gameService.getGame(created.getId()), "Game not found");
    }

    @Test
    public void assertInitGameSave() {
        Invite invite = new Invite(UUID.randomUUID(), new User("test", "test"), new User("test", "test"));
        Game created = gameService.initGame(invite);

        verify(mockGameRepository, times(1)).save(created);
        verify(mockBoardService, times(1)).initBoard(created);
        verify(mockPlayerService, times(1)).initPlayers(created, invite);
    }

    @Test
    public void assertInvalidMoveShouldNotExecute() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn().opponent(), new User("test", "test"));
        Board board = Board.build(game);
        Pit pit = board.getPits()[1];

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);
        assertFalse(gameService.isValidTurn(game, player, pit));

        gameService.tryDoTurn(game, player, board, pit);
        verify(mockBoardService, times(0)).executeMove(player, board, pit);
    }

    @Test
    public void assertValidMoveShouldExecute() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Board board = Board.build(game);
        Pit pit = board.getPits()[1];
        int endIndex = pit.getId().getPitIndex() + pit.getMarbles();
        Pit endPit = board.getPits()[endIndex];

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);
        when(mockBoardService.executeMove(player, board, pit)).thenReturn(endPit);
        assertTrue(gameService.isValidTurn(game, player, pit));

        gameService.tryDoTurn(game, player, board, pit);
        verify(mockBoardService, times(1)).executeMove(player, board, pit);
        verify(mockBoardService, times(0)).collectOpposites(player, board, pit);
    }

    @Test
    public void assertValidMoveToEmptyShouldCollectOpposites() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Board board = Board.build(game);
        Pit pit = board.getPits()[1];
        int endIndex = pit.getId().getPitIndex() + pit.getMarbles();
        Pit endPit = board.getPits()[endIndex];
        endPit.setMarbles(1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);
        when(mockBoardService.executeMove(player, board, pit)).thenReturn(endPit);
        assertTrue(gameService.isValidTurn(game, player, pit));

        gameService.tryDoTurn(game, player, board, pit);
        verify(mockBoardService, times(1)).executeMove(player, board, pit);
        verify(mockBoardService, times(1)).collectOpposites(player, board, endPit);
    }

    @Test
    public void assertMoveToScorePitShouldNotEndTurn() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Board board = Board.build(game);
        Pit pit = board.getPits()[1];
        int endIndex = pit.getId().getPitIndex() + pit.getMarbles();
        Pit endPit = board.getPits()[endIndex];

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(endPit)).thenReturn(true);
        when(mockBoardService.executeMove(player, board, pit)).thenReturn(endPit);
        assertTrue(gameService.isValidTurn(game, player, pit));

        gameService.tryDoTurn(game, player, board, pit);
        verify(mockBoardService, times(1)).executeMove(player, board, pit);
        verify(mockBoardService, times(0)).collectOpposites(player, board, pit);
        assertEquals(player.getSide(), game.getTurn());
    }

    @Test
    public void assertMoveToNonScorePitShouldEndTurn() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Board board = Board.build(game);
        Pit pit = board.getPits()[1];
        int endIndex = pit.getId().getPitIndex() + pit.getMarbles();
        Pit endPit = board.getPits()[endIndex];

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(endPit)).thenReturn(false);
        when(mockBoardService.executeMove(player, board, pit)).thenReturn(endPit);
        assertTrue(gameService.isValidTurn(game, player, pit));

        gameService.tryDoTurn(game, player, board, pit);
        verify(mockBoardService, times(1)).executeMove(player, board, pit);
        verify(mockBoardService, times(0)).collectOpposites(player, board, pit);
        assertEquals(player.getSide().opponent(), game.getTurn());
    }

    @Test
    public void assertNormalMoveShouldBeValid() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Pit pit = new Pit(game, 1, 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertTrue(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertNonTurnPlayerMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn().opponent(), new User("test", "test"));
        Pit pit = new Pit(game, 1, 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertOppositeSideMoveShouldBeNotValid() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Pit pit = new Pit(game, 10, 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn().opponent());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertScorePitMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Pit pit = new Pit(game, 0, 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(true);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertNoMarbleMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(game, game.getTurn(), new User("test", "test"));
        Pit pit = new Pit(game, 1, 0);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertShouldEndTurnTrueIfNotScorePit() {
        Game game = new Game();
        Pit pit = new Pit(game, 0, 1);

        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertTrue(gameService.shouldEndTurn(pit));
    }

    @Test
    public void assertShouldEndTurnFalseIfScorePit() {
        Game game = new Game();
        Pit pit = new Pit(game, 6, 1);

        when(mockBoardService.isScorePit(pit)).thenReturn(true);

        assertFalse(gameService.shouldEndTurn(pit));
    }
}
