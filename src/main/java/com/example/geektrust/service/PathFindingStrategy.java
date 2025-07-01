package com.example.geektrust.service;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;

public interface PathFindingStrategy {
    int calculatePower(Position source, Position destination, Direction direction);
}
