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
public class DijkstraPathFinder extends AbstractPathFinder {

    public DijkstraPathFinder(Board board, int moveCost, int turnCost) {
        super(board, moveCost, turnCost);
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
            moveForward(cur, minCost, pq, dest);
            // turn left
            turnLeft(cur, minCost, pq, dest);
            // turn right
            turnRight(cur, minCost, pq, dest);
        }
        return best;
    }
}
