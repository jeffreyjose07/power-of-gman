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
            System.err.println("Error: Input file not found: " + inputFile);
            System.exit(ApplicationError.FILE_NOT_FOUND.getExitCode());
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(ApplicationError.IO_ERROR.getExitCode());
        } catch (RuntimeException e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(ApplicationError.UNEXPECTED_ERROR.getExitCode());
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
        try {
            Command command = factory.fromLine(commandLine, context.getBoard());
            if (command != null) {
                command.execute(context);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command: " + e.getMessage());
        }
    }
}
