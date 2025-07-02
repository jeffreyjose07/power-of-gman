package com.example.geektrust.command;

import com.example.geektrust.command.CommandContext;
import com.example.geektrust.command.PrintPowerCommand;
import com.example.geektrust.model.Direction;
import com.example.geektrust.model.Position;
import com.example.geektrust.service.PathFindingStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PrintPowerCommandTest {
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testExecutePrintsPower() {
        CommandContext ctx = new CommandContext(new PathFindingStrategy() {
            @Override
            public int calculatePower(Position source, Position destination, Direction direction) {
                return 99;
            }
        });
        ctx.updateSource(new Position(0,0), Direction.N);
        ctx.updateDestination(new Position(1,1));
        PrintPowerCommand cmd = new PrintPowerCommand();
        cmd.execute(ctx);
        assertTrue(outContent.toString().contains("POWER 99"));
    }
}
