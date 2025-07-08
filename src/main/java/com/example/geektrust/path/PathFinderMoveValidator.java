package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

public class PathFinderMoveValidator {
    private final Board board;

    public PathFinderMoveValidator(Board board) {
        this.board = board;
    }

    public boolean isValidMove(Position position) {
        return board.inBounds(position);
    }

    public boolean isDestinationReached(State currentState, Position destination) {
        return currentState.getX() == destination.getX() && currentState.getY() == destination.getY();
    }

    public boolean shouldSkipState(State currentState, int bestCost) {
        return currentState.getPowerSpent() >= bestCost;
    }

    public Position calculateNextPosition(State currentState) {
        int nextX = currentState.getX() + currentState.getDirection().dx();
        int nextY = currentState.getY() + currentState.getDirection().dy();
        return new Position(nextX, nextY);
    }
}