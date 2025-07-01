package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GManTest {
    @Test
    public void testDirectionIndex() {
        assertEquals(0, GMan.getDirectionIndex("N"));
        assertEquals(1, GMan.getDirectionIndex("E"));
        assertEquals(2, GMan.getDirectionIndex("S"));
        assertEquals(3, GMan.getDirectionIndex("W"));
        assertThrows(IllegalArgumentException.class, () -> GMan.getDirectionIndex("X"));
    }

    @Test
    public void testMoveForward() {
        GMan gman = new GMan(2, 2, "N");
        gman.moveForward();
        assertEquals(2, gman.getX());
        assertEquals(3, gman.getY());
    }

    @Test
    public void testTurnLeftAndRight() {
        GMan gman = new GMan(1, 1, "N");
        gman.turnLeft();
        assertEquals(3, gman.getDirIdx()); 
        gman.turnRight();
        assertEquals(0, gman.getDirIdx()); 
    }

    @Test
    public void testInBounds() {
        assertTrue(GMan.inBounds(0, 0));
        assertTrue(GMan.inBounds(6, 6));
        assertTrue(GMan.inBounds(0, 6));
        assertTrue(GMan.inBounds(6, 0));
        assertFalse(GMan.inBounds(-1, 0));
        assertFalse(GMan.inBounds(0, 7));
        assertFalse(GMan.inBounds(7, 7));
    }

    @Test
    public void testEdgeMovement() {
        GMan gman = new GMan(6, 6, "E");
        gman.moveForward();
        assertFalse(GMan.inBounds(gman.getX(), gman.getY()));
    }
} 