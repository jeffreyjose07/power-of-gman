package com.example.geektrust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    
    private static final String DEFAULT_DIRECTION = "N";
    private static final int DEFAULT_COORDINATE = 0;
    
    
    private int sourceX = DEFAULT_COORDINATE;
    private int sourceY = DEFAULT_COORDINATE;
    private int destX = DEFAULT_COORDINATE;
    private int destY = DEFAULT_COORDINATE;
    private String direction = DEFAULT_DIRECTION;
    private final PathFindingStrategy pathFinder;
    
    
    public Main() {
        this.pathFinder = new PowerCalculator();
    }
    
    
    public Main(PathFindingStrategy pathFinder) {
        this.pathFinder = pathFinder;
    }
    
    
    private static final int ERROR_NO_INPUT = 1;
    private static final int ERROR_FILE_NOT_FOUND = 2;
    private static final int ERROR_IO = 3;
    private static final int ERROR_UNEXPECTED = 4;

    private static final int SOURCE_ARGS_COUNT = 4;
    private static final int DEST_ARGS_COUNT = 3;
    private static final int PRINT_ARGS_COUNT = 1;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(ERROR_NO_INPUT);
        }
        
        Main app = new Main();
        try {
            app.processInputFile(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + args[0]);
            System.exit(ERROR_FILE_NOT_FOUND);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(ERROR_IO);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(ERROR_UNEXPECTED);
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
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command '" + command + "': " + e.getMessage());
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
        this.direction = parts[3];
        
        
        try {
            GMan.getDirectionIndex(direction);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction: " + direction + ". Must be one of: N, E, S, W");
        }
    }
    
    
    private void updateDestination(String[] parts) {
        this.destX = parseCoordinate(parts[1], "destination X");
        this.destY = parseCoordinate(parts[2], "destination Y");
    }
    
    
    private int parseCoordinate(String coordStr, String coordName) {
        try {
            int coord = Integer.parseInt(coordStr);
            if (coord < DEFAULT_COORDINATE || coord >= GMan.GRID_SIZE) {
                throw new IllegalArgumentException(
                    String.format("%s coordinate must be between 0 and %d", coordName, GMan.GRID_SIZE - 1));
            }
            return coord;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordStr + ". Must be an integer.");
        }
    }
    
    
    private void printRemainingPower() {
        try {
            int remainingPower = pathFinder.calculatePower(sourceX, sourceY, destX, destY, direction);
            System.out.println("POWER " + remainingPower);
        } catch (Exception e) {
            System.err.println("Error calculating power: " + e.getMessage());
        }
    }
}
