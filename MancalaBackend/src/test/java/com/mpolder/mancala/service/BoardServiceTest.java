package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;
import com.mpolder.mancala.model.idclass.PitId;
import com.mpolder.mancala.model.idclass.PlayerId;
import com.mpolder.mancala.repository.PitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {
    @Mock
    private PitRepository mockPitRepository;
    @InjectMocks
    private BoardService boardService = new BoardService();

    @Test
    public void assertExecuteMoveMovesMarbles() {
        Game game = new Game();
        Player player = new Player(game, Side.TOP, "testmail");
        Board board = Board.build(game.getId());
        Arrays.stream(board.getPits()).forEach(Pit::clearMarbles);

        int[] marbles = {
                0, 6, 0, 2, 1, 0,
                0,
                0, 0, 0, 0, 0, 0,
                0,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        boardService.executeMove(player, board, board.getPits()[1]);

        int[] expected = {
                0, 0, 1, 3, 2, 1,
                1,
                1, 0, 0, 0, 0, 0,
                0
        };

        assertArrayEquals(expected, Arrays.stream(board.getPits()).mapToInt(Pit::getMarbles).toArray());
        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(
                board.getPits()[1],
                board.getPits()[2],
                board.getPits()[3],
                board.getPits()[4],
                board.getPits()[5],
                board.getPits()[6],
                board.getPits()[7]
        ));
    }

    @Test
    public void assertExecuteMoveSkipsOpponent() {
        Game game = new Game();
        Player player = new Player(game, Side.BOTTOM, "testmail");
        Board board = Board.build(game.getId());
        Arrays.stream(board.getPits()).forEach(Pit::clearMarbles);

        int[] marbles = {
                0, 6, 0, 2, 1, 0,
                0,
                0, 0, 0, 0, 0, 0,
                0,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        boardService.executeMove(player, board, board.getPits()[1]);

        int[] expected = {
                0, 0, 1, 3, 2, 1,
                0,
                1, 1, 0, 0, 0, 0,
                0
        };

        assertArrayEquals(expected, Arrays.stream(board.getPits()).mapToInt(Pit::getMarbles).toArray());
        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(
                board.getPits()[1],
                board.getPits()[2],
                board.getPits()[3],
                board.getPits()[4],
                board.getPits()[5],
                board.getPits()[7],
                board.getPits()[8]
        ));
    }

    @Test
    public void assertCollectOppositesMovesMarbles() {
        Game game = new Game();
        Player player = new Player(game, Side.TOP, "testmail");
        Board board = Board.build(game.getId());
        Pit pit = board.getPits()[2];
        Pit opposite = board.getPits()[10];
        Pit scorePit = board.getPits()[6];

        pit.setMarbles(2);
        opposite.setMarbles(3);
        scorePit.setMarbles(5);

        boardService.collectOpposites(player, board, pit);

        assertEquals(0, pit.getMarbles());
        assertEquals(0, opposite.getMarbles());
        assertEquals(10, scorePit.getMarbles());
        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(pit, opposite, scorePit));
    }

    @Test
    public void assertInitBoardSavesBoard() {
        Game game = new Game();
        Board board = boardService.initBoard(game);

        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(board.getPits()));
        assertEquals(game.getId(), board.getGameId());
    }

    @Test
    public void assertGetBoardGetsOrderedFromRepository() {
        Game game = new Game();
        Board board = Board.build(game.getId());
        List<Pit> shuffled = new ArrayList<>(Arrays.asList(board.getPits()));
        Collections.shuffle(shuffled);

        when(mockPitRepository.findAllByIdGameId(game.getId())).thenReturn(shuffled);

        Board result = boardService.getBoard(game);
        verify(mockPitRepository, times(1)).findAllByIdGameId(game.getId());
        assertArrayEquals(board.getPits(), result.getPits());
    }

    @Test
    public void assertGetScorePitIsCorrect() {
        Board board = Board.build(UUID.randomUUID());

        Pit top = boardService.getScorePit(board, Side.TOP);
        Pit bottom = boardService.getScorePit(board, Side.BOTTOM);

        assertEquals(board.getPits()[6], top);
        assertEquals(board.getPits()[13], bottom);
    }

    @Test
    public void assertIsScorePitShouldBeFalseForNonScorePits() {
        UUID gameId = UUID.randomUUID();
        List<Pit> regularPits = Arrays.asList(
                new Pit(new PitId(gameId, 0), 1),
                new Pit(new PitId(gameId, 1), 1),
                new Pit(new PitId(gameId, 2), 1),
                new Pit(new PitId(gameId, 3), 1),
                new Pit(new PitId(gameId, 4), 1),
                new Pit(new PitId(gameId, 5), 1),
                new Pit(new PitId(gameId, 7), 1),
                new Pit(new PitId(gameId, 8), 1),
                new Pit(new PitId(gameId, 9), 1),
                new Pit(new PitId(gameId, 10), 1),
                new Pit(new PitId(gameId, 11), 1),
                new Pit(new PitId(gameId, 12), 1)
        );

        regularPits.forEach(pit -> {
            assertFalse(boardService.isScorePit(pit), "Pit " + pit.getId().getPitIndex() + " returns false");
        });
    }

    @Test
    public void assertIsScorePitShouldBeTrueForScorePits() {
        UUID gameId = UUID.randomUUID();
        List<Pit> regularPits = Arrays.asList(
                new Pit(new PitId(gameId, 6), 1),
                new Pit(new PitId(gameId, 13), 1)
        );

        regularPits.forEach(pit -> {
            assertTrue(boardService.isScorePit(pit), "Pit " + pit.getId().getPitIndex() + " returns true");
        });
    }

    @Test
    public void assertGetTopOppositePitToBeCorrect() {
        Board board = Board.build(UUID.randomUUID());
        Pit pit1 = board.getPits()[2];
        Pit pit2 = board.getPits()[5];

        Pit pit1Opposite = boardService.getOppositePit(board, pit1);
        Pit pit2Opposite = boardService.getOppositePit(board, pit2);

        assertEquals(board.getPits()[10], pit1Opposite);
        assertEquals(board.getPits()[7], pit2Opposite);
    }

    @Test
    public void assertGetBottomOppositePitToBeCorrect() {
        Board board = Board.build(UUID.randomUUID());
        Pit pit1 = board.getPits()[9];
        Pit pit2 = board.getPits()[12];

        Pit pit1Opposite = boardService.getOppositePit(board, pit1);
        Pit pit2Opposite = boardService.getOppositePit(board, pit2);

        assertEquals(board.getPits()[3], pit1Opposite);
        assertEquals(board.getPits()[0], pit2Opposite);
    }
}
