package com.mpolder.mancala.service;

import com.mpolder.mancala.model.Board;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Pit;
import com.mpolder.mancala.model.Player;
import com.mpolder.mancala.model.idclass.PitId;
import com.mpolder.mancala.model.idclass.PlayerId;
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
    private GameService gameService = new GameService();

    @Test
    public void assertGetGamesAccessesGameRepository() {
        List<Game> created = Arrays.asList(new Game(), new Game());
        when(mockGameRepository.findAll()).thenReturn(created);

        List<Game> found = gameService.getGames();
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
    public void assertInitGameSave() {
        Game created = gameService.initGame();

        verify(mockGameRepository, times(1)).save(created);
        verify(mockBoardService, times(1)).initBoard(created);
        verify(mockPlayerService, times(1)).initPlayers(created);
    }

    @Test
    public void assertInvalidMoveShouldNotExecute() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn().opponent()));
        Board board = Board.build(game.getId());
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
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Board board = Board.build(game.getId());
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
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Board board = Board.build(game.getId());
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
        verify(mockBoardService, times(1)).collectOpposites(player, board, pit);
    }

    @Test
    public void assertMoveToScorePitShouldNotEndTurn() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Board board = Board.build(game.getId());
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
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Board board = Board.build(game.getId());
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
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Pit pit = new Pit(new PitId(game.getId(), 1), 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertTrue(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertNonTurnPlayerMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn().opponent()));
        Pit pit = new Pit(new PitId(game.getId(), 1), 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertOppositeSideMoveShouldBeNotValid() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Pit pit = new Pit(new PitId(game.getId(), 10), 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn().opponent());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertScorePitMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Pit pit = new Pit(new PitId(game.getId(), 0), 1);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(true);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertNoMarbleMoveShouldNotBeValid() {
        Game game = new Game();
        Player player = new Player(new PlayerId(game.getId(), game.getTurn()));
        Pit pit = new Pit(new PitId(game.getId(), 1), 0);

        when(mockBoardService.getPitSide(pit)).thenReturn(game.getTurn());
        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertFalse(gameService.isValidTurn(game, player, pit));
    }

    @Test
    public void assertShouldEndTurnTrueIfNotScorePit() {
        UUID gameId = UUID.randomUUID();
        Pit pit = new Pit(new PitId(gameId, 0), 1);

        when(mockBoardService.isScorePit(pit)).thenReturn(false);

        assertTrue(gameService.shouldEndTurn(pit));
    }

    @Test
    public void assertShouldEndTurnFalseIfScorePit() {
        UUID gameId = UUID.randomUUID();
        Pit pit = new Pit(new PitId(gameId, 6), 1);

        when(mockBoardService.isScorePit(pit)).thenReturn(true);

        assertFalse(gameService.shouldEndTurn(pit));
    }
}
