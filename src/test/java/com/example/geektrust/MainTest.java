package com.example.geektrust;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    private final PowerCalculator calc = new PowerCalculator();

    @Test
    public void testCase1() {
        
        int result = calc.calculatePower(new Position(5, 5), new Position(3, 3), "N");
        assertEquals(150, result);
    }

    @Test
    public void testCase2() {
        
        int result = calc.calculatePower(new Position(0, 0), new Position(6, 6), "W");
        assertEquals(70, result);
    }

    @Test
    public void testCase3() {
        
        int result = calc.calculatePower(new Position(1, 4), new Position(5, 2), "W");
        assertEquals(130, result);
    }

    @Test
    public void testCase4() {
        
        int result = calc.calculatePower(new Position(5, 5), new Position(1, 2), "E");
        assertEquals(120, result);
    }

    @Test
    public void testCase5() {
        
        int result = calc.calculatePower(new Position(0, 5), new Position(6, 1), "W");
        assertEquals(90, result);
    }

    @Test
    public void testCase6() {
        
        int result = calc.calculatePower(new Position(1, 1), new Position(1, 2), "S");
        assertEquals(180, result);
    }

    @Test
    public void testCase7() {
        
        int result = calc.calculatePower(new Position(3, 1), new Position(5, 1), "W");
        assertEquals(170, result);
    }
}