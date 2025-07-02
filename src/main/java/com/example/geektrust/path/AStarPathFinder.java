package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.*;

/**
 * A* path finder for extensible use cases.
 */
public class AStarPathFinder extends AbstractPathFinder {

    public AStarPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
    }

    private int heuristic(Position p, Position dest) {
        int dx = Math.abs(p.getX() - dest.getX());
        int dy = Math.abs(p.getY() - dest.getY());
        return (dx + dy) * Math.min(moveCost, turnCost);
    }

    @Override
    protected int computePriority(Position pos, int costSoFar, Position dest) {
        return costSoFar + heuristic(pos, dest);
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int size = board.getSize();
        int dirCount = Direction.values().length;
        int[][][] gScore = new int[size][size][dirCount];
        for (int[][] arr2d : gScore) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        PriorityQueue<State> open = new PriorityQueue<>(Comparator.comparingInt(s -> s.getPowerSpent()));
        open.add(new State(source, startDir, 0));
        gScore[source.getX()][source.getY()][startDir.ordinal()] = 0;
        int best = Integer.MAX_VALUE;

        while (!open.isEmpty()) {
            State cur = open.poll();
            if (cur.getX() == dest.getX() && cur.getY() == dest.getY()) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            if (cur.getPowerSpent() >= best) {
                continue;
            }
            // move forward
            moveForward(cur, gScore, open, dest);
            // turn left
            turnLeft(cur, gScore, open, dest);
            // turn right
            turnRight(cur, gScore, open, dest);
        }
        return best;
    }
}
