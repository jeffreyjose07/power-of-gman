package com.example.geektrust.command;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.service.PathFindingStrategy;

public class CommandContext {
    private int sourceX = 0;
    private int sourceY = 0;
    private int destX = 0;
    private int destY = 0;
    private Direction direction = Direction.N;
    private final Board board;
    private final PathFindingStrategy pathFinder;

    public CommandContext(PathFindingStrategy pathFinder) {
        this.board = new Board();
        this.pathFinder = pathFinder;
    }

    public int getSourceX() { return sourceX; }
    public int getSourceY() { return sourceY; }
    public void setSource(int x, int y, Direction dir) {
        this.sourceX = x;
        this.sourceY = y;
        this.direction = dir;
    }

    public int getDestX() { return destX; }
    public int getDestY() { return destY; }
    public void setDestination(int x, int y) {
        this.destX = x;
        this.destY = y;
    }

    public Direction getDirection() { return direction; }
    public Board getBoard() { return board; }
    public PathFindingStrategy getPathFinder() { return pathFinder; }
}
