package com.example.geektrust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class CommandProcessor {
    private static final int DEFAULT_COORDINATE = 0;

    private int sourceX = DEFAULT_COORDINATE;
    private int sourceY = DEFAULT_COORDINATE;
    private int destX = DEFAULT_COORDINATE;
    private int destY = DEFAULT_COORDINATE;
    private Direction direction = Direction.N;
    private final PathFindingStrategy pathFinder;
    private final Board board = new Board();

    private static final int SOURCE_ARGS_COUNT = 4;
    private static final int DEST_ARGS_COUNT = 3;
    private static final int PRINT_ARGS_COUNT = 1;

    public CommandProcessor() {
        this(new PowerCalculator());
    }

    public CommandProcessor(PathFindingStrategy pathFinder) {
        this.pathFinder = pathFinder;
    }

    public void run(String inputFile) throws IOException {
        try {
            processInputFile(inputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + inputFile);
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException(e);
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
        if (commandLine.isEmpty()) {
            return;
        }
        String[] parts = commandLine.split("\\s+");
        String command = parts[0];
        try {
            handleCommand(command, parts);
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command '" + command + "': " + e.getMessage());
        }
    }

    private void handleCommand(String command, String[] parts) {
        switch (command) {
            case "SOURCE":
                validateCommandParts(parts, SOURCE_ARGS_COUNT);
                updateSource(parts);
                break;
            case "DESTINATION":
                validateCommandParts(parts, DEST_ARGS_COUNT);
                updateDestination(parts);
                break;
            case "PRINT_POWER":
                validateCommandParts(parts, PRINT_ARGS_COUNT);
                printRemainingPower();
                break;
            default:
                System.err.println("Warning: Unknown command: " + command);
        }
    }

    private void validateCommandParts(String[] parts, int expectedCount) {
        if (parts.length != expectedCount) {
            throw new IllegalArgumentException(
                String.format("Expected %d arguments but got %d", expectedCount - 1, parts.length - 1));
        }
    }

    private void updateSource(String[] parts) {
        this.sourceX = parseCoordinate(parts[1], "source X");
        this.sourceY = parseCoordinate(parts[2], "source Y");
        this.direction = Direction.fromString(parts[3]);
    }

    private void updateDestination(String[] parts) {
        this.destX = parseCoordinate(parts[1], "destination X");
        this.destY = parseCoordinate(parts[2], "destination Y");
    }

    private int parseCoordinate(String coordStr, String coordName) {
        int coord;
        try {
            coord = Integer.parseInt(coordStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordStr + ". Must be an integer.");
        }

        if (coord < DEFAULT_COORDINATE || coord >= board.getSize()) {
            throw new IllegalArgumentException(
                String.format("%s coordinate must be between 0 and %d", coordName, board.getSize() - 1));
        }
        return coord;
    }

    private void printRemainingPower() {
        try {
            Position src = new Position(sourceX, sourceY);
            Position dst = new Position(destX, destY);
            int remainingPower = pathFinder.calculatePower(src, dst, direction);
            System.out.println("POWER " + remainingPower);
        } catch (Exception e) {
            System.err.println("Error calculating power: " + e.getMessage());
        }
    }
}
