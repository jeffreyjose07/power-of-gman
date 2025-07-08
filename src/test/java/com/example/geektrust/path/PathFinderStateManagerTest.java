package com.example.geektrust.path;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderStateManagerTest {
    private PathFinderStateManager stateManager;
    private final int boardSize = 5;

    @BeforeEach
    void setUp() {
        stateManager = new PathFinderStateManager(boardSize);
    }

    @Test
    void testCreateCostArray() {
        int[][][] costs = stateManager.createCostArray();
        
        assertEquals(boardSize, costs.length);
        assertEquals(boardSize, costs[0].length);
        assertEquals(Direction.values().length, costs[0][0].length);
        
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < Direction.values().length; k++) {
                    assertEquals(Integer.MAX_VALUE, costs[i][j][k]);
                }
            }
        }
    }

    @Test
    void testInitializeStartState() {
        int[][][] costs = stateManager.createCostArray();
        Queue<State> queue = new LinkedList<>();
        Position source = new Position(0, 0);
        Direction startDir = Direction.E;
        
        stateManager.initializeStartState(source, startDir, costs, queue);
        
        assertEquals(1, queue.size());
        assertEquals(0, costs[0][0][startDir.ordinal()]);
        
        State state = queue.poll();
        assertEquals(0, state.getX());
        assertEquals(0, state.getY());
        assertEquals(startDir, state.getDirection());
        assertEquals(0, state.getPowerSpent());
    }

    @Test
    void testShouldUpdateCost() {
        int[][][] costs = stateManager.createCostArray();
        Position pos = new Position(1, 1);
        Direction dir = Direction.N;
        
        assertTrue(stateManager.shouldUpdateCost(pos, dir, 100, costs));
        
        costs[1][1][dir.ordinal()] = 50;
        assertFalse(stateManager.shouldUpdateCost(pos, dir, 100, costs));
        assertTrue(stateManager.shouldUpdateCost(pos, dir, 25, costs));
    }

    @Test
    void testUpdateCostAndEnqueue() {
        int[][][] costs = stateManager.createCostArray();
        Queue<State> queue = new LinkedList<>();
        Position pos = new Position(2, 3);
        Direction dir = Direction.S;
        int cost = 75;
        
        stateManager.updateCostAndEnqueue(pos, dir, cost, costs, queue);
        
        assertEquals(cost, costs[2][3][dir.ordinal()]);
        assertEquals(1, queue.size());
        
        State state = queue.poll();
        assertEquals(2, state.getX());
        assertEquals(3, state.getY());
        assertEquals(dir, state.getDirection());
        assertEquals(cost, state.getPowerSpent());
    }
}