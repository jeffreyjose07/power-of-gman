package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.DijkstraPathFinder;


public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    private final Board board;
    private final DijkstraPathFinder finder;

    public PowerCalculator() {
        this(new Board());
    }

    public PowerCalculator(Board board) {
        this.board = board;
        this.finder = new DijkstraPathFinder(board);
    }

    @Override
    public int calculatePower(Position source, Position destination, Direction dir) {
        if (!board.inBounds(source) || !board.inBounds(destination)) {
            return 0;
        }
        int minPowerSpent = finder.findMinPower(source, destination, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }
}
