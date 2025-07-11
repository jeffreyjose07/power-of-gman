package com.example.geektrust.model;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.State;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {
    @Test
    public void testGetters() {
        State state = new State(2, 3, Direction.E, 15);
        assertEquals(2, state.getX());
        assertEquals(3, state.getY());
        assertEquals(Direction.E, state.getDirection());
        assertEquals(15, state.getPowerSpent());
    }
}
