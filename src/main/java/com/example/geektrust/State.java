package com.example.geektrust;


public class State {
    private final int x;
    private final int y;
    private final int dirIdx;
    private final int powerSpent;

    
    public State(int x, int y, int dirIdx, int powerSpent) {
        this.x = x;
        this.y = y;
        this.dirIdx = dirIdx;
        this.powerSpent = powerSpent;
    }

    
    public int getX() {
        return x;
    }

    
    public int getY() {
        return y;
    }

    
    public int getDirIdx() {
        return dirIdx;
    }

    
    public int getPowerSpent() {
        return powerSpent;
    }
}