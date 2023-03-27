package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;
import com.mpolder.mancala.repository.PitRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {
    @Mock
    private PitRepository mockPitRepository;
    @InjectMocks
    private BoardService boardService;

    @Test
    public void assertExecuteMoveMovesMarbles() {
        Game game = new Game();
        Player player = new Player(game, Side.TOP, new User("test@gmail.com", "test"));
        Board board = Board.build(game);
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
        Player player = new Player(game, Side.BOTTOM, new User("test@gmail.com", "test"));
        Board board = Board.build(game);
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
        Player player = new Player(game, Side.TOP, new User("test@gmail.com", "test"));
        Board board = Board.build(game);
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
    public void assertShouldCollectSidesReturnsFalse() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 6, 0, 2, 1, 0,
                0,
                0, 0, 2, 0, 5, 0,
                0,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertFalse(boardService.shouldCollectSides(board));
    }

    @Test
    public void assertShouldCollectSidesReturnsTrueTopEmpty() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 0, 0, 0, 0, 0,
                5,
                0, 0, 2, 0, 5, 0,
                0,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertTrue(boardService.shouldCollectSides(board));
    }

    @Test
    public void assertShouldCollectSidesReturnsTrueBottomEmpty() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 6, 0, 2, 1, 0,
                0,
                0, 0, 0, 0, 0, 0,
                2,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertTrue(boardService.shouldCollectSides(board));
    }

    @Test
    public void assertCollectSidesSumsPits() {
        Game game = new Game();
        Player player = new Player(game, Side.BOTTOM, new User("test@gmail.com", "test"));
        Board board = Board.build(game);
        Arrays.stream(board.getPits()).forEach(Pit::clearMarbles);

        int[] marbles = {
                2, 1, 0, 5, 1, 0,
                2,
                0, 2, 0, 1, 0, 3,
                6,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        boardService.collectSides(board);

        int[] expected = {
                0, 0, 0, 0, 0, 0,
                11,
                0, 0, 0, 0, 0, 0,
                12
        };

        assertArrayEquals(expected, Arrays.stream(board.getPits()).mapToInt(Pit::getMarbles).toArray());
        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(board.getPits()));
    }

    @Test
    public void assertGetWinnerReturnsTop() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 0, 0, 0, 0, 0,
                10,
                0, 0, 0, 0, 0, 0,
                8,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertEquals(Side.TOP, boardService.getWinner(board));
    }

    @Test
    public void assertGetWinnerReturnsBottom() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 0, 0, 0, 0, 0,
                4,
                0, 0, 0, 0, 0, 0,
                12,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertEquals(Side.BOTTOM, boardService.getWinner(board));
    }

    @Test
    public void assertGetWinnerReturnsDraw() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] marbles = {
                0, 0, 0, 0, 0, 0,
                9,
                0, 0, 0, 0, 0, 0,
                9,
        };
        for (int i = 0; i < marbles.length; i++) board.getPits()[i].setMarbles(marbles[i]);

        assertNull(boardService.getWinner(board));
    }

    @Test
    public void assertInitBoardSavesBoard() {
        Game game = new Game();
        Board board = boardService.initBoard(game);

        verify(mockPitRepository, times(1)).saveAll(Arrays.asList(board.getPits()));
        assertEquals(game, board.getGame());
    }

    @Test
    public void assertGetBoardGetsOrderedFromRepository() {
        Game game = new Game();
        Board board = Board.build(game);
        List<Pit> shuffled = new ArrayList<>(Arrays.asList(board.getPits()));
        Collections.shuffle(shuffled);

        when(mockPitRepository.findAllByIdGameId(game.getId())).thenReturn(shuffled);

        Board result = boardService.getBoard(game);
        verify(mockPitRepository, times(1)).findAllByIdGameId(game.getId());
        assertArrayEquals(board.getPits(), result.getPits());
    }

    @Test
    public void assertGetScorePitIsCorrect() {
        Board board = Board.build(new Game());

        Pit top = boardService.getScorePit(board, Side.TOP);
        Pit bottom = boardService.getScorePit(board, Side.BOTTOM);

        assertEquals(board.getPits()[6], top);
        assertEquals(board.getPits()[13], bottom);
    }

    @Test
    public void assertIsScorePitShouldBeFalseForNonScorePits() {
        Game game = new Game();
        List<Pit> regularPits = Arrays.asList(
                new Pit(game, 0, 1),
                new Pit(game, 1, 1),
                new Pit(game, 2, 1),
                new Pit(game, 3, 1),
                new Pit(game, 4, 1),
                new Pit(game, 5, 1),
                new Pit(game, 7, 1),
                new Pit(game, 8, 1),
                new Pit(game, 9, 1),
                new Pit(game, 10, 1),
                new Pit(game, 11, 1),
                new Pit(game, 12, 1)
        );

        regularPits.forEach(pit -> {
            assertFalse(boardService.isScorePit(pit), "Pit " + pit.getId().getPitIndex() + " returns false");
        });
    }

    @Test
    public void assertIsScorePitShouldBeTrueForScorePits() {
        Game game = new Game();
        List<Pit> regularPits = Arrays.asList(
                new Pit(game, 6, 1),
                new Pit(game, 13, 1)
        );

        regularPits.forEach(pit -> {
            assertTrue(boardService.isScorePit(pit), "Pit " + pit.getId().getPitIndex() + " returns true");
        });
    }

    @Test
    public void assertGetTopOppositePitToBeCorrect() {
        Board board = Board.build(new Game());
        Pit pit1 = board.getPits()[2];
        Pit pit2 = board.getPits()[5];

        Pit pit1Opposite = boardService.getOppositePit(board, pit1);
        Pit pit2Opposite = boardService.getOppositePit(board, pit2);

        assertEquals(board.getPits()[10], pit1Opposite);
        assertEquals(board.getPits()[7], pit2Opposite);
    }

    @Test
    public void assertGetBottomOppositePitToBeCorrect() {
        Board board = Board.build(new Game());
        Pit pit1 = board.getPits()[9];
        Pit pit2 = board.getPits()[12];

        Pit pit1Opposite = boardService.getOppositePit(board, pit1);
        Pit pit2Opposite = boardService.getOppositePit(board, pit2);

        assertEquals(board.getPits()[3], pit1Opposite);
        assertEquals(board.getPits()[0], pit2Opposite);
    }
}
