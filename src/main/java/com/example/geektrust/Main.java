package com.example.geektrust;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point for the Power of G-Man application.
 * 
 * <p>This class is responsible for:
 * <ul>
 *   <li>Reading input from a file</li>
 *   <li>Parsing commands (SOURCE, DESTINATION, PRINT_POWER)</li>
 *   <li>Delegating power calculation to the pathfinding strategy</li>
 *   <li>Displaying the result</li>
 * </ul>
 * 
 * <p>Expected input format (in a text file):
 * <pre>
 * SOURCE 1 2 N
 * DESTINATION 4 3
 * PRINT_POWER
 * </pre>
 */
public class Main {
    // Default values
    private static final String DEFAULT_DIRECTION = "N";
    private static final int DEFAULT_COORDINATE = 0;
    
    // State variables
    private int sourceX = DEFAULT_COORDINATE;
    private int sourceY = DEFAULT_COORDINATE;
    private int destX = DEFAULT_COORDINATE;
    private int destY = DEFAULT_COORDINATE;
    private String direction = DEFAULT_DIRECTION;
    private final PathFindingStrategy pathFinder;
    
    /**
     * Constructs a new Main instance with the default pathfinding strategy.
     */
    public Main() {
        this.pathFinder = new PowerCalculator();
    }
    
    /**
     * Constructs a new Main instance with a custom pathfinding strategy.
     * 
     * @param pathFinder The pathfinding strategy to use
     */
    public Main(PathFindingStrategy pathFinder) {
        this.pathFinder = pathFinder;
    }
    
    /**
     * The main entry point for the application.
     * 
     * @param args Command-line arguments (expects a single argument: input file path)
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(1);
        }
        
        Main app = new Main();
        try {
            app.processInputFile(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + args[0]);
            System.exit(2);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(3);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(4);
        }
    }
    
    /**
     * Processes the input file line by line and executes the commands.
     * 
     * @param filePath Path to the input file
     * @throws IOException If an I/O error occurs while reading the file
     */
    private void processInputFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fis)) {
            
            while (scanner.hasNextLine()) {
                processCommand(scanner.nextLine().trim());
            }
        }
    }
    
    /**
     * Processes a single command from the input file.
     * 
     * @param commandLine The command line to process
     */
    private void processCommand(String commandLine) {
        if (commandLine.isEmpty()) {
            return; // Skip empty lines
        }
        
        String[] parts = commandLine.split("\\s+");
        String command = parts[0];
        
        try {
            switch (command) {
                case "SOURCE":
                    validateCommandParts(parts, 4);
                    updateSource(parts);
                    break;
                    
                case "DESTINATION":
                    validateCommandParts(parts, 3);
                    updateDestination(parts);
                    break;
                    
                case "PRINT_POWER":
                    validateCommandParts(parts, 1);
                    printRemainingPower();
                    break;
                    
                default:
                    System.err.println("Warning: Unknown command: " + command);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command '" + command + "': " + e.getMessage());
        }
    }
    
    /**
     * Validates that the command has the correct number of parts.
     * 
     * @param parts The command parts
     * @param expectedCount The expected number of parts
     * @throws IllegalArgumentException If the number of parts is incorrect
     */
    private void validateCommandParts(String[] parts, int expectedCount) {
        if (parts.length != expectedCount) {
            throw new IllegalArgumentException(
                String.format("Expected %d arguments but got %d", expectedCount - 1, parts.length - 1));
        }
    }
    
    /**
     * Updates the source coordinates and direction.
     * 
     * @param parts The command parts (SOURCE x y direction)
     */
    private void updateSource(String[] parts) {
        this.sourceX = parseCoordinate(parts[1], "source X");
        this.sourceY = parseCoordinate(parts[2], "source Y");
        this.direction = parts[3];
        
        // Validate direction
        try {
            GMan.getDirectionIndex(direction);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid direction: " + direction + ". Must be one of: N, E, S, W");
        }
    }
    
    /**
     * Updates the destination coordinates.
     * 
     * @param parts The command parts (DESTINATION x y)
     */
    private void updateDestination(String[] parts) {
        this.destX = parseCoordinate(parts[1], "destination X");
        this.destY = parseCoordinate(parts[2], "destination Y");
    }
    
    /**
     * Parses a coordinate string into an integer and validates it's within bounds.
     * 
     * @param coordStr The coordinate string to parse
     * @param coordName The name of the coordinate for error messages
     * @return The parsed coordinate
     * @throws IllegalArgumentException If the coordinate is invalid or out of bounds
     */
    private int parseCoordinate(String coordStr, String coordName) {
        try {
            int coord = Integer.parseInt(coordStr);
            if (coord < 0 || coord >= GMan.GRID_SIZE) {
                throw new IllegalArgumentException(
                    String.format("%s coordinate must be between 0 and %d", coordName, GMan.GRID_SIZE - 1));
            }
            return coord;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid coordinate: " + coordStr + ". Must be an integer.");
        }
    }
    
    /**
     * Calculates and prints the remaining power after traveling from source to destination.
     */
    private void printRemainingPower() {
        try {
            int remainingPower = pathFinder.calculatePower(sourceX, sourceY, destX, destY, direction);
            System.out.println("POWER " + remainingPower);
        } catch (Exception e) {
            System.err.println("Error calculating power: " + e.getMessage());
        }
    }
}
