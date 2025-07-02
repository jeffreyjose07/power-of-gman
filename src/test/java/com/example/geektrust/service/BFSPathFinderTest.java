package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.path.BFSPathFinder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BFSPathFinderTest {
    private final Board board = new Board();
    private final BFSPathFinder finder = new BFSPathFinder(board, PowerCalculator.DEFAULT_MOVE_COST, PowerCalculator.DEFAULT_TURN_COST);

    @Test
    public void testForwardMovementCost() {
        int cost = finder.findMinPower(new Position(0, 0), new Position(1, 0), Direction.E);
        assertEquals(PowerCalculator.DEFAULT_MOVE_COST, cost);
    }

    @Test
    public void testTurnAndMoveCost() {
        int expected = PowerCalculator.DEFAULT_TURN_COST + PowerCalculator.DEFAULT_MOVE_COST;
        int cost = finder.findMinPower(new Position(0, 0), new Position(0, 1), Direction.E);
        assertEquals(expected, cost);
    }
}
