package com.example.geektrust.command;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;

public class CommandContext {
    private Position source = new Position(0, 0);
    private Position destination = new Position(0, 0);
    private Direction direction = Direction.N;
    private final Board board;
    private final PathFindingStrategy pathFinder;

    public CommandContext(PathFindingStrategy pathFinder) {
        this.board = new Board();
        this.pathFinder = pathFinder;
    }

    public Position getSource() { return source; }
    public Position getDestination() { return destination; }

    public void updateSource(Position pos, Direction dir) {
        this.source = pos;
        this.direction = dir;
    }

    public void updateDestination(Position pos) {
        this.destination = pos;
    }

    public Direction getDirection() { return direction; }
    public Board getBoard() { return board; }
    public PathFindingStrategy getPathFinder() { return pathFinder; }
}
