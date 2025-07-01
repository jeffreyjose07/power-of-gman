package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CommandProcessorTest {
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private File createTempInput(String content) throws Exception {
        File temp = File.createTempFile("input", ".txt");
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(content);
        }
        return temp;
    }

    private static class StubPathFinder implements PathFindingStrategy {
        private final int power;
        StubPathFinder(int power) {
            this.power = power;
        }
        @Override
        public int calculatePower(Position source, Position destination, Direction direction) {
            return power;
        }
    }

    @Test
    public void testRunWithValidCommands() throws Exception {
        File input = createTempInput("SOURCE 0 0 N\nDESTINATION 1 0\nPRINT_POWER\n");
        CommandProcessor cp = new CommandProcessor(new StubPathFinder(123));
        assertDoesNotThrow(() -> cp.run(input.getAbsolutePath()));
        assertTrue(outContent.toString().contains("POWER 123"));
    }

    @Test
    public void testInvalidCoordinateShowsError() throws Exception {
        File input = createTempInput("SOURCE 7 0 N\nPRINT_POWER\n");
        CommandProcessor cp = new CommandProcessor(new StubPathFinder(0));
        cp.run(input.getAbsolutePath());
        assertTrue(errContent.toString().contains("coordinate"));
    }

    @Test
    public void testUnknownCommandWarns() throws Exception {
        File input = createTempInput("JUMP 1 1\n");
        CommandProcessor cp = new CommandProcessor(new StubPathFinder(0));
        cp.run(input.getAbsolutePath());
        assertTrue(errContent.toString().contains("Unknown command"));
    }
}
