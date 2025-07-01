package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DijkstraPathFinderTest {
    private final Board board = new Board();
    private final DijkstraPathFinder finder = new DijkstraPathFinder(board);

    @Test
    public void testForwardMovementCost() {
        int cost = finder.findMinPower(new Position(0, 0), new Position(1, 0), Direction.E);
        assertEquals(PowerCalculator.MOVE_COST, cost);
    }

    @Test
    public void testTurnAndMoveCost() {
        int expected = PowerCalculator.TURN_COST + PowerCalculator.MOVE_COST;
        int cost = finder.findMinPower(new Position(0, 0), new Position(0, 1), Direction.E);
        assertEquals(expected, cost);
    }
}
