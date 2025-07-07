package com.example.geektrust.command;

import com.example.geektrust.ApplicationError;
import com.example.geektrust.service.PathFindingStrategy;
import com.example.geektrust.service.PowerCalculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CommandProcessor {
    private final CommandContext context;
    private final CommandFactory factory = new CommandFactory();

    public CommandProcessor() {
        this(new PowerCalculator());
    }

    public CommandProcessor(PathFindingStrategy pathFinder) {
        this.context = new CommandContext(pathFinder);
    }

    public void run(String inputFile) {
        try {
            processInputFile(inputFile);
        } catch (FileNotFoundException e) {
            handleError("Error: Input file not found: " + inputFile, ApplicationError.FILE_NOT_FOUND);
        } catch (IOException e) {
            handleError("Error reading input file: " + e.getMessage(), ApplicationError.IO_ERROR);
        } catch (RuntimeException e) {
            handleError("An unexpected error occurred: " + e.getMessage(), ApplicationError.UNEXPECTED_ERROR);
        }
    }
    
    protected void handleError(String message, ApplicationError error) {
        System.err.println(message);
        if (!isTestMode()) {
            System.exit(error.getExitCode());
        } else {
            throw new RuntimeException(message);
        }
    }
    
    private boolean isTestMode() {
        try {
            Class.forName("org.junit.jupiter.api.Test");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private void processInputFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fis)) {
            while (scanner.hasNextLine()) {
                processCommand(scanner.nextLine().trim());
            }
        }
    }

    private void processCommand(String commandLine) {
        if (commandLine == null || commandLine.trim().isEmpty()) {
            return;
        }
        
        try {
            Command command = factory.fromLine(commandLine, context.getBoard());
            if (command != null) {
                command.execute(context);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command '" + commandLine + "': " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error processing command '" + commandLine + "': " + e.getMessage());
        }
    }
}
