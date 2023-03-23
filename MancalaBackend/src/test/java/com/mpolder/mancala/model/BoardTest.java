package com.mpolder.mancala.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    @Test
    public void shouldInitialiseBoard() {
        Game game = new Game();
        Board board = Board.build(game);

        int[] expectedMarbles = {
                6, 6, 6, 6, 6, 6, //Top row
                0, //Top score
                6, 6, 6, 6, 6, 6, //Bottom row
                0, //Bottom score
        };

        assertEquals(game, board.getGame());
        assertArrayEquals(expectedMarbles, Arrays.stream(board.getPits()).mapToInt(Pit::getMarbles).toArray());
    }
}
