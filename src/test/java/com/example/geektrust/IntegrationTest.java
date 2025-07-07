package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "SOURCE 5 5 N;DESTINATION 3 3;PRINT_POWER|POWER 150",
        "SOURCE 0 0 W;DESTINATION 6 6;PRINT_POWER|POWER 70",
        "SOURCE 1 4 W;DESTINATION 5 2;PRINT_POWER|POWER 130",
        "SOURCE 5 5 E;DESTINATION 1 2;PRINT_POWER|POWER 120",
        "SOURCE 0 5 W;DESTINATION 6 1;PRINT_POWER|POWER 90",
        "SOURCE 1 1 S;DESTINATION 1 2;PRINT_POWER|POWER 180",
        "SOURCE 3 1 W;DESTINATION 5 1;PRINT_POWER|POWER 170"
    })
    public void testAllSevenTestCases(String input, String expectedOutput) {
        String actualOutput = executeCommands(input.replace(";", "\n"));
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    public void testOriginalInputFile() {
        String actualOutput = executeInputFile("sample_input/input1.txt");
        assertEquals("POWER 145", actualOutput);
    }
    
    @Test
    public void testEdgeCaseSamePosition() {
        String actualOutput = executeCommands("SOURCE 0 0 N\nDESTINATION 0 0\nPRINT_POWER");
        assertEquals("POWER 200", actualOutput);
    }
    
    @Test
    public void testEdgeCaseOneStepForward() {
        String actualOutput = executeCommands("SOURCE 0 0 N\nDESTINATION 0 1\nPRINT_POWER");
        assertEquals("POWER 190", actualOutput);
    }
    
    @Test
    public void testEdgeCaseOneTurn() {
        String actualOutput = executeCommands("SOURCE 0 0 N\nDESTINATION 1 0\nPRINT_POWER");
        assertEquals("POWER 185", actualOutput);
    }
    
    private String executeCommands(String commands) {
        File tempFile = null;
        try {
            tempFile = createTempFile(commands);
            return executeInputFile(tempFile.getAbsolutePath());
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
    
    private String executeInputFile(String filePath) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        
        try {
            System.setOut(new PrintStream(outputStream));
            CommandProcessor processor = new CommandProcessor();
            processor.run(filePath);
            return outputStream.toString().trim();
        } catch (RuntimeException e) {
            // Handle file not found or other errors gracefully
            if (e.getMessage().contains("Input file not found")) {
                throw new RuntimeException("Test input file not found: " + filePath, e);
            }
            throw e;
        } finally {
            System.setOut(originalOut);
        }
    }
    
    private File createTempFile(String content) {
        try {
            File tempFile = File.createTempFile("test_input_", ".txt");
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(content);
            }
            return tempFile;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create temp file", e);
        }
    }
}