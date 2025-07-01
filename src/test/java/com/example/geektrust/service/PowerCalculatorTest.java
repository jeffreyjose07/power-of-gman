package com.example.geektrust.service;

import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PowerCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerCalculatorTest {
    private final PowerCalculator calc = new PowerCalculator();

    @Test
    public void testMinimalPath() {
        int result = calc.calculatePower(new Position(0, 0), new Position(1, 0), Direction.E);
        assertEquals(190, result);
    }

    @Test
    public void testTurnRequired() {
        int result = calc.calculatePower(new Position(0, 0), new Position(0, 1), Direction.E);
        assertEquals(185, result);
    }

    @Test
    public void testUnreachable() {
        int result = calc.calculatePower(new Position(0, 0), new Position(7, 7), Direction.N);
        assertEquals(0, result);
    }

    @Test
    public void testSameStartEnd() {
        int result = calc.calculatePower(new Position(3, 3), new Position(3, 3), Direction.S);
        assertEquals(200, result);
    }

    @Test
    public void testEdgeCases() {
        assertTrue(calc.calculatePower(new Position(0, 6), new Position(6, 6), Direction.E) > 0);
        assertTrue(calc.calculatePower(new Position(6, 0), new Position(0, 6), Direction.N) > 0);
        assertTrue(calc.calculatePower(new Position(6, 6), new Position(0, 0), Direction.W) > 0);
    }
}
