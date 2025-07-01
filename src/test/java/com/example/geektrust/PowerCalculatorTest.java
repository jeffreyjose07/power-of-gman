package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerCalculatorTest {
    @Test
    public void testMinimalPath() {
        int result = PowerCalculator.calculatePower(0, 0, 1, 0, "E");
        assertEquals(190, result); // 1 move east
    }

    @Test
    public void testTurnRequired() {
        int result = PowerCalculator.calculatePower(0, 0, 0, 1, "E");
        assertEquals(185, result); // turn left to N, then move
    }

    @Test
    public void testUnreachable() {
        int result = PowerCalculator.calculatePower(0, 0, 7, 7, "N");
        assertEquals(0, result); // out of bounds, should return 0
    }

    @Test
    public void testSameStartEnd() {
        int result = PowerCalculator.calculatePower(3, 3, 3, 3, "S");
        assertEquals(200, result);
    }

    @Test
    public void testEdgeCases() {
        assertTrue(PowerCalculator.calculatePower(0, 6, 6, 6, "E") > 0);
        assertTrue(PowerCalculator.calculatePower(6, 0, 0, 6, "N") > 0);
        assertTrue(PowerCalculator.calculatePower(6, 6, 0, 0, "W") > 0);
    }
} 