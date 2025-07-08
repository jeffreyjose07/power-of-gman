package com.example.geektrust.path;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderCostCalculatorTest {
    private PathFinderCostCalculator costCalculator;
    private final int moveCost = 10;
    private final int turnCost = 5;

    @BeforeEach
    void setUp() {
        costCalculator = new PathFinderCostCalculator(moveCost, turnCost);
    }

    @Test
    void testCalculateMoveCost() {
        State state = new State(new Position(0, 0), Direction.E, 20);
        
        int result = costCalculator.calculateMoveCost(state);
        
        assertEquals(30, result);
    }

    @Test
    void testCalculateTurnCost() {
        State state = new State(new Position(1, 1), Direction.N, 15);
        
        int result = costCalculator.calculateTurnCost(state);
        
        assertEquals(20, result);
    }

    @Test
    void testComputePriority() {
        Position pos = new Position(2, 3);
        Position dest = new Position(4, 5);
        int costSoFar = 25;
        
        int result = costCalculator.computePriority(pos, costSoFar, dest);
        
        assertEquals(costSoFar, result);
    }

    @Test
    void testGetMoveCost() {
        assertEquals(moveCost, costCalculator.getMoveCost());
    }

    @Test
    void testGetTurnCost() {
        assertEquals(turnCost, costCalculator.getTurnCost());
    }
}