package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileBasedIntegrationTest {
    
    @ParameterizedTest
    @CsvSource({
        "sample_input/test1.txt, POWER 150",
        "sample_input/test2.txt, POWER 70", 
        "sample_input/test3.txt, POWER 130",
        "sample_input/test4.txt, POWER 120",
        "sample_input/test5.txt, POWER 90",
        "sample_input/test6.txt, POWER 180",
        "sample_input/test7.txt, POWER 170"
    })
    public void testSevenTestCasesFromFiles(String inputFile, String expectedOutput) {
        String actualOutput = executeInputFile(inputFile);
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
    public void testOriginalInputFile() {
        String actualOutput = executeInputFile("sample_input/input1.txt");
        assertEquals("POWER 145", actualOutput);
    }
    
    @Test
    public void testAdditionalInputFiles() {
        // Test the additional files we created earlier
        // input2: SOURCE 0 0 N -> DESTINATION 0 3 (3 steps north) = 200 - 30 = 170
        assertEquals("POWER 170", executeInputFile("sample_input/input2.txt"));
        // input3: SOURCE 0 0 E -> DESTINATION 3 0 (3 steps east) = 200 - 30 = 170  
        assertEquals("POWER 170", executeInputFile("sample_input/input3.txt"));
        // input4: SOURCE 0 0 S -> DESTINATION 0 0 (same position) = 200
        assertEquals("POWER 200", executeInputFile("sample_input/input4.txt"));
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
}