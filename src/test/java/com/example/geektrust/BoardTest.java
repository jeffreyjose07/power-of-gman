package com.example.geektrust;

import com.example.geektrust.model.Board;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testDefaultSize() {
        Board board = new Board();
        assertEquals(Board.DEFAULT_SIZE, board.getSize());
    }

    @Test
    public void testCustomSize() {
        Board board = new Board(5);
        assertEquals(5, board.getSize());
    }

    @Test
    public void testInBounds() {
        Board board = new Board();
        assertTrue(board.inBounds(0,0));
        assertTrue(board.inBounds(6,6));
        assertFalse(board.inBounds(-1,0));
        assertFalse(board.inBounds(0,7));
    }
}
