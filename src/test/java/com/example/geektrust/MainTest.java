package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void testSampleInput1() {
        // SOURCE 2 1 E, DESTINATION 4 3, PRINT_POWER -> POWER 155
        int result = PowerCalculator.calculatePower(2, 1, 4, 3, "E");
        assertEquals(155, result);
    }

    @Test
    public void testSampleInput2() {
        // SOURCE 0 5 W, DESTINATION 6 1, PRINT_POWER -> POWER 90
        int result = PowerCalculator.calculatePower(0, 5, 6, 1, "W");
        assertEquals(90, result);
    }

    @Test
    public void testSampleInput3() {
        // SOURCE 3 6 N, DESTINATION 1 0, PRINT_POWER -> POWER 110
        int result = PowerCalculator.calculatePower(3, 6, 1, 0, "N");
        assertEquals(110, result);
    }

    @Test
    public void testNoMove() {
        // SOURCE 2 2 N, DESTINATION 2 2, PRINT_POWER -> POWER 200
        int result = PowerCalculator.calculatePower(2, 2, 2, 2, "N");
        assertEquals(200, result);
    }

    @Test
    public void testEdgeCase() {
        // SOURCE 0 0 N, DESTINATION 5 5, PRINT_POWER (should be positive)
        int result = PowerCalculator.calculatePower(0, 0, 5, 5, "N");
        assertTrue(result > 0);
    }

    @Test
    public void testReportCase_0_6_to_6_6() {
        int result = PowerCalculator.calculatePower(0, 6, 6, 6, "W");
        assertEquals(70, result);
    }

    @Test
    public void testReportCase_5_0_to_6_1() {
        int result = PowerCalculator.calculatePower(5, 0, 6, 1, "W");
        assertEquals(90, result);
    }

    @Test
    public void testReportCase_5_6_to_6_6() {
        int result = PowerCalculator.calculatePower(5, 6, 6, 6, "E");
        assertEquals(150, result);
    }

    @Test
    public void testReportCase_6_0_to_0_6() {
        int result = PowerCalculator.calculatePower(6, 0, 0, 6, "N");
        assertTrue(result > 0);
    }
}