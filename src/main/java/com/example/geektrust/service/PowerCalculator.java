package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.path.BFSPathFinder;
import com.example.geektrust.path.PathFinder;


public class PowerCalculator implements PathFindingStrategy {
    public static final int DEFAULT_INITIAL_POWER = 200;
    public static final int DEFAULT_MOVE_COST = 10;
    public static final int DEFAULT_TURN_COST = 5;

    private final Board board;
    private final PathFinder finder;
    private final int initialPower;
    private final int moveCost;
    private final int turnCost;

    public PowerCalculator() {
        this(new Board(), DEFAULT_INITIAL_POWER, DEFAULT_MOVE_COST, DEFAULT_TURN_COST);
    }

    public PowerCalculator(Board board, int initialPower, int moveCost, int turnCost) {
        this.board = board;
        this.initialPower = initialPower;
        this.moveCost = moveCost;
        this.turnCost = turnCost;
        this.finder = new BFSPathFinder(board, moveCost, turnCost);
    }

    @Override
    public int calculatePower(Position source, Position destination, Direction dir) {
        if (!board.inBounds(source) || !board.inBounds(destination)) {
            return 0;
        }
        int minPowerSpent = finder.findMinPower(source, destination, dir);
        int remaining = initialPower - minPowerSpent;
        return Math.max(remaining, 0);
    }
}
