package com.example.geektrust.path;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Classic Dijkstra search over (x,y,direction) states.
 */
public class DijkstraPathFinder implements PathFinder {
    private final Board board;
    private final int moveCost;
    private final int turnCost;

    public DijkstraPathFinder(Board board, int moveCost, int turnCost) {
        this.board = board;
        this.moveCost = moveCost;
        this.turnCost = turnCost;
    }

    @Override
    public int findMinPower(Position source, Position dest, Direction startDir) {
        int size = board.getSize();
        int dirCount = Direction.values().length;
        int[][][] minCost = new int[size][size][dirCount];
        for (int[][] arr2d : minCost) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
        pq.add(new State(source, startDir, 0));
        minCost[source.getX()][source.getY()][startDir.ordinal()] = 0;
        int best = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            if (cur.getPowerSpent() >= best) {
                continue;
            }
            if (cur.getX() == dest.getX() && cur.getY() == dest.getY()) {
                best = Math.min(best, cur.getPowerSpent());
                continue;
            }
            // move forward
            int nx = cur.getX() + cur.getDirection().dx();
            int ny = cur.getY() + cur.getDirection().dy();
            if (board.inBounds(nx, ny)) {
                int newCost = cur.getPowerSpent() + moveCost;
                int dirIdx = cur.getDirection().ordinal();
                if (newCost < minCost[nx][ny][dirIdx]) {
                    minCost[nx][ny][dirIdx] = newCost;
                    pq.add(new State(nx, ny, cur.getDirection(), newCost));
                }
            }
            // turn left
            Direction left = cur.getDirection().left();
            int lCost = cur.getPowerSpent() + turnCost;
            if (lCost < minCost[cur.getX()][cur.getY()][left.ordinal()]) {
                minCost[cur.getX()][cur.getY()][left.ordinal()] = lCost;
                pq.add(new State(cur.getX(), cur.getY(), left, lCost));
            }
            // turn right
            Direction right = cur.getDirection().right();
            int rCost = cur.getPowerSpent() + turnCost;
            if (rCost < minCost[cur.getX()][cur.getY()][right.ordinal()]) {
                minCost[cur.getX()][cur.getY()][right.ordinal()] = rCost;
                pq.add(new State(cur.getX(), cur.getY(), right, rCost));
            }
        }
        return best;
    }
}
