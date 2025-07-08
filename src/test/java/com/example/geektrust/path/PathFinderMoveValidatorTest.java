package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderMoveValidatorTest {
    private PathFinderMoveValidator moveValidator;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(5);
        moveValidator = new PathFinderMoveValidator(board);
    }

    @Test
    void testIsValidMove() {
        Position validPos = new Position(2, 3);
        Position invalidPos = new Position(5, 5);
        Position negativePos = new Position(-1, 0);
        
        assertTrue(moveValidator.isValidMove(validPos));
        assertFalse(moveValidator.isValidMove(invalidPos));
        assertFalse(moveValidator.isValidMove(negativePos));
    }

    @Test
    void testIsDestinationReached() {
        Position dest = new Position(3, 4);
        State reachedState = new State(3, 4, Direction.N, 100);
        State notReachedState = new State(2, 3, Direction.E, 50);
        
        assertTrue(moveValidator.isDestinationReached(reachedState, dest));
        assertFalse(moveValidator.isDestinationReached(notReachedState, dest));
    }

    @Test
    void testShouldSkipState() {
        State expensiveState = new State(1, 1, Direction.S, 150);
        State cheapState = new State(2, 2, Direction.W, 50);
        int bestCost = 100;
        
        assertTrue(moveValidator.shouldSkipState(expensiveState, bestCost));
        assertFalse(moveValidator.shouldSkipState(cheapState, bestCost));
    }

    @Test
    void testCalculateNextPosition() {
        State eastState = new State(2, 3, Direction.E, 10);
        State northState = new State(1, 1, Direction.N, 20);
        State southState = new State(3, 3, Direction.S, 30);
        State westState = new State(4, 2, Direction.W, 40);
        
        Position eastNext = moveValidator.calculateNextPosition(eastState);
        Position northNext = moveValidator.calculateNextPosition(northState);
        Position southNext = moveValidator.calculateNextPosition(southState);
        Position westNext = moveValidator.calculateNextPosition(westState);
        
        assertEquals(new Position(3, 3), eastNext);
        assertEquals(new Position(1, 2), northNext);
        assertEquals(new Position(3, 2), southNext);
        assertEquals(new Position(3, 2), westNext);
    }
}