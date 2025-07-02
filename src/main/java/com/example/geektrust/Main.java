package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(ApplicationError.NO_INPUT.getExitCode());
        }

        CommandProcessor processor = new CommandProcessor();
        try {
            processor.process(args[0]);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + args[0]);
            System.exit(ApplicationError.FILE_NOT_FOUND.getExitCode());
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(ApplicationError.IO_ERROR.getExitCode());
        } catch (RuntimeException e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            System.exit(ApplicationError.UNEXPECTED_ERROR.getExitCode());
        }
    }
}
