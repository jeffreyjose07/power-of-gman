package com.example.geektrust.service;

import com.example.geektrust.model.Board;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.model.State;

import java.util.*;

public class PowerCalculator implements PathFindingStrategy {
    public static final int INITIAL_POWER = 200;
    public static final int MOVE_COST = 10;
    public static final int TURN_COST = 5;

    private final Board board;

    public PowerCalculator() {
        this(new Board());
    }

    public PowerCalculator(Board board) {
        this.board = board;
    }

    @Override
    public int calculatePower(Position source, Position destination, Direction dir) {
        if (!board.inBounds(source) || !board.inBounds(destination)) {
            return 0;
        }
        int minPowerSpent = dijkstra(source, destination, dir);
        int remaining = INITIAL_POWER - minPowerSpent;
        return Math.max(remaining, 0);
    }

    private int dijkstra(Position source, Position dest, Direction dir) {
        Direction startDir = dir;
        int size = board.getSize();
        int dirCount = Direction.values().length;
        int[][][] minPower = initializePowerGrid(size, dirCount);
        PriorityQueue<State> pq = initializePriorityQueue(source.getX(), source.getY(), startDir);

        minPower[source.getX()][source.getY()][startDir.ordinal()] = 0;
        int minPowerSpent = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            State curr = pq.poll();

            if (isDestinationReached(curr, dest.getX(), dest.getY())) {
                minPowerSpent = Math.min(minPowerSpent, curr.getPowerSpent());
                continue;
            }

            exploreNeighbours(curr, minPower, pq);
        }

        return minPowerSpent;
    }

    private int[][][] initializePowerGrid(int size, int dirCount) {
        int[][][] minPower = new int[size][size][dirCount];
        for (int[][] arr2d : minPower) {
            for (int[] arr1d : arr2d) {
                Arrays.fill(arr1d, Integer.MAX_VALUE);
            }
        }
        return minPower;
    }

    private PriorityQueue<State> initializePriorityQueue(int sX, int sY, Direction startDir) {
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(State::getPowerSpent));
        pq.add(new State(sX, sY, startDir, 0));
        return pq;
    }

    private boolean isDestinationReached(State current, int destX, int destY) {
        return current.getX() == destX && current.getY() == destY;
    }

    private void exploreNeighbours(State current, int[][][] minPower, PriorityQueue<State> pq) {
        tryMovingForward(current, minPower, pq);
        tryTurning(current, minPower, pq, current.getDirection().left());
        tryTurning(current, minPower, pq, current.getDirection().right());
    }

    private void tryMovingForward(State current, int[][][] minPower, PriorityQueue<State> pq) {
        int nx = current.getX() + current.getDirection().dx();
        int ny = current.getY() + current.getDirection().dy();

        if (!board.inBounds(nx, ny)) {
            return;
        }

        int newPower = current.getPowerSpent() + MOVE_COST;
        int dirIdx = current.getDirection().ordinal();
        if (newPower >= minPower[nx][ny][dirIdx]) {
            return;
        }

        minPower[nx][ny][dirIdx] = newPower;
        pq.add(new State(nx, ny, current.getDirection(), newPower));
    }

    private void tryTurning(State current, int[][][] minPower, PriorityQueue<State> pq, Direction newDir) {
        int turnPower = current.getPowerSpent() + TURN_COST;
        if (turnPower < minPower[current.getX()][current.getY()][newDir.ordinal()]) {
            minPower[current.getX()][current.getY()][newDir.ordinal()] = turnPower;
            pq.add(new State(current.getX(), current.getY(), newDir, turnPower));
        }
    }
}
