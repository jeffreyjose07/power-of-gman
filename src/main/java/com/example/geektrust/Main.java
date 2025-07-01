package com.example.geektrust;

import com.example.geektrust.command.CommandProcessor;

/**
 * Entry point of the application.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: No input file provided.");
            System.err.println("Usage: java -jar <jar-file> <input-file>");
            System.exit(ApplicationError.NO_INPUT.getExitCode());
        }

        new CommandProcessor().run(args[0]);
    }
}
