package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {
    @Test
    public void testGetters() {
        Position pos = new Position(2, 3);
        assertEquals(2, pos.getX());
        assertEquals(3, pos.getY());
    }
}
