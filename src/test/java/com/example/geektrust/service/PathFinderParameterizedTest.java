package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.path.BFSPathFinder;
import com.example.geektrust.path.DijkstraPathFinder;
import com.example.geektrust.path.PathFinder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathFinderParameterizedTest {

    static Stream<PathFinder> pathFinders() {
        Board board = new Board();
        int move = PowerCalculator.DEFAULT_MOVE_COST;
        int turn = PowerCalculator.DEFAULT_TURN_COST;
        return Stream.of(
                new BFSPathFinder(board, move, turn),
                new DijkstraPathFinder(board, move, turn)
        );
    }

    @ParameterizedTest
    @MethodSource("pathFinders")
    public void testForwardMovementCost(PathFinder finder) {
        int cost = finder.findMinPower(new Position(0, 0), new Position(1, 0), Direction.E);
        assertEquals(PowerCalculator.DEFAULT_MOVE_COST, cost);
    }

    @ParameterizedTest
    @MethodSource("pathFinders")
    public void testTurnAndMoveCost(PathFinder finder) {
        int expected = PowerCalculator.DEFAULT_TURN_COST + PowerCalculator.DEFAULT_MOVE_COST;
        int cost = finder.findMinPower(new Position(0, 0), new Position(0, 1), Direction.E);
        assertEquals(expected, cost);
    }
}
