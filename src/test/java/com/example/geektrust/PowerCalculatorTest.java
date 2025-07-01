package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerCalculatorTest {
    private final PowerCalculator calc = new PowerCalculator();

    @Test
    public void testMinimalPath() {
        int result = calc.calculatePower(0, 0, 1, 0, "E");
        assertEquals(190, result); 
    }

    @Test
    public void testTurnRequired() {
        int result = calc.calculatePower(0, 0, 0, 1, "E");
        assertEquals(185, result); 
    }

    @Test
    public void testUnreachable() {
        int result = calc.calculatePower(0, 0, 7, 7, "N");
        assertEquals(0, result); 
    }

    @Test
    public void testSameStartEnd() {
        int result = calc.calculatePower(3, 3, 3, 3, "S");
        assertEquals(200, result);
    }

    @Test
    public void testEdgeCases() {
        assertTrue(calc.calculatePower(0, 6, 6, 6, "E") > 0);
        assertTrue(calc.calculatePower(6, 0, 0, 6, "N") > 0);
        assertTrue(calc.calculatePower(6, 6, 0, 0, "W") > 0);
    }
} 