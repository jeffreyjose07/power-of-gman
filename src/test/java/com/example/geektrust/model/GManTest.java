package com.example.geektrust.model;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.GMan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GManTest {
    @Test
    public void testDirectionParsing() {
        assertEquals(Direction.N, Direction.fromString("N"));
        assertEquals(Direction.E, Direction.fromString("E"));
        assertEquals(Direction.S, Direction.fromString("S"));
        assertEquals(Direction.W, Direction.fromString("W"));
        assertThrows(IllegalArgumentException.class, () -> Direction.fromString("X"));
    }

    @Test
    public void testMoveForward() {
        GMan gman = new GMan(2, 2, Direction.N);
        gman.moveForward();
        assertEquals(2, gman.getX());
        assertEquals(3, gman.getY());
    }

    @Test
    public void testTurnLeftAndRight() {
        GMan gman = new GMan(1, 1, Direction.N);
        gman.turnLeft();
        assertEquals(Direction.W, gman.getDirection());
        gman.turnRight();
        assertEquals(Direction.N, gman.getDirection());
    }

    @Test
    public void testInBounds() {
        Board board = new Board();
        assertTrue(board.inBounds(0, 0));
        assertTrue(board.inBounds(6, 6));
        assertTrue(board.inBounds(0, 6));
        assertTrue(board.inBounds(6, 0));
        assertFalse(board.inBounds(-1, 0));
        assertFalse(board.inBounds(0, 7));
        assertFalse(board.inBounds(7, 7));
    }

    @Test
    public void testEdgeMovement() {
        GMan gman = new GMan(6, 6, Direction.E);
        gman.moveForward();
        assertFalse(gman.inBounds());
    }
}
