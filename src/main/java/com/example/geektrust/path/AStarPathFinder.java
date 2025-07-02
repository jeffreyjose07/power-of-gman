package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.*;

/**
 * A* path finder for extensible use cases.
 */
public class AStarPathFinder implements PathFinder {
    private final Board board;
    private final int moveCost;
    private final int turnCost;

    public AStarPathFinder(Board board, int moveCost, int turnCost) {
        this.board = board;
        this.moveCost = moveCost;
        this.turnCost = turnCost;
    }

    private int heuristic(Position p, Position dest) {
        int dx = Math.abs(p.getX() - dest.getX());
        int dy = Math.abs(p.getY() - dest.getY());
        return (dx + dy) * Math.min(moveCost, turnCost);
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
            int nx = cur.getX() + cur.getDirection().dx();
            int ny = cur.getY() + cur.getDirection().dy();
            if (board.inBounds(nx, ny)) {
                int newCost = cur.getPowerSpent() + moveCost;
                int dirIdx = cur.getDirection().ordinal();
                if (newCost < gScore[nx][ny][dirIdx]) {
                    gScore[nx][ny][dirIdx] = newCost;
                    int f = newCost + heuristic(new Position(nx, ny), dest);
                    open.add(new State(nx, ny, cur.getDirection(), f));
                }
            }
            // turn left
            Direction left = cur.getDirection().left();
            int lCost = cur.getPowerSpent() + turnCost;
            if (lCost < gScore[cur.getX()][cur.getY()][left.ordinal()]) {
                gScore[cur.getX()][cur.getY()][left.ordinal()] = lCost;
                int f = lCost + heuristic(new Position(cur.getX(), cur.getY()), dest);
                open.add(new State(cur.getX(), cur.getY(), left, f));
            }
            // turn right
            Direction right = cur.getDirection().right();
            int rCost = cur.getPowerSpent() + turnCost;
            if (rCost < gScore[cur.getX()][cur.getY()][right.ordinal()]) {
                gScore[cur.getX()][cur.getY()][right.ordinal()] = rCost;
                int f = rCost + heuristic(new Position(cur.getX(), cur.getY()), dest);
                open.add(new State(cur.getX(), cur.getY(), right, f));
            }
        }
        return best;
    }
}
