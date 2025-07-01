package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private final PowerCalculator calc = new PowerCalculator();

    @Test
    public void testCase1() {
        // SOURCE 5 5 N, DESTINATION 3 3, PRINT_POWER -> POWER 150
        int result = calc.calculatePower(5, 5, 3, 3, "N");
        assertEquals(150, result);
    }

    @Test
    public void testCase2() {
        // SOURCE 0 0 W, DESTINATION 6 6, PRINT_POWER -> POWER 70
        int result = calc.calculatePower(0, 0, 6, 6, "W");
        assertEquals(70, result);
    }

    @Test
    public void testCase3() {
        // SOURCE 1 4 W, DESTINATION 5 2, PRINT_POWER -> POWER 130
        int result = calc.calculatePower(1, 4, 5, 2, "W");
        assertEquals(130, result);
    }

    @Test
    public void testCase4() {
        // SOURCE 5 5 E, DESTINATION 1 2, PRINT_POWER -> POWER 120
        int result = calc.calculatePower(5, 5, 1, 2, "E");
        assertEquals(120, result);
    }

    @Test
    public void testCase5() {
        // SOURCE 0 5 W, DESTINATION 6 1, PRINT_POWER -> POWER 90
        int result = calc.calculatePower(0, 5, 6, 1, "W");
        assertEquals(90, result);
    }

    @Test
    public void testCase6() {
        // SOURCE 1 1 S, DESTINATION 1 2, PRINT_POWER -> POWER 180
        int result = calc.calculatePower(1, 1, 1, 2, "S");
        assertEquals(180, result);
    }

    @Test
    public void testCase7() {
        // SOURCE 3 1 W, DESTINATION 5 1, PRINT_POWER -> POWER 170
        int result = calc.calculatePower(3, 1, 5, 1, "W");
        assertEquals(170, result);
    }
}