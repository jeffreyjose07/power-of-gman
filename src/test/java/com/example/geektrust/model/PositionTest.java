package com.example.geektrust.model;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    public void testGetters() {
        Position pos = new Position(2, 3);
        assertEquals(2, pos.getX());
        assertEquals(3, pos.getY());
    }

    @Test
    public void testMove() {
        Position start = new Position(1, 1);
        Position moved = start.move(Direction.E);
        assertEquals(new Position(2, 1), moved);
        assertEquals(new Position(1, 1), start);
    }
}
