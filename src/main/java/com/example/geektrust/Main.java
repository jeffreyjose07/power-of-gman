package com.example.geektrust;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Entry point for the Power of G-Man application. Handles input and output.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide an input file path.");
            return;
        }
        int sourceX = 0, sourceY = 0, destX = 0, destY = 0;
        String direction = "N";
        PathFindingStrategy pathFinder = new PowerCalculator();
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                String input = sc.nextLine();
                String[] cmd = input.split(" ");
                switch (cmd[0]) {
                    case "SOURCE":
                        sourceX = Integer.parseInt(cmd[1]);
                        sourceY = Integer.parseInt(cmd[2]);
                        direction = cmd[3];
                        break;
                    case "DESTINATION":
                        destX = Integer.parseInt(cmd[1]);
                        destY = Integer.parseInt(cmd[2]);
                        break;
                    case "PRINT_POWER":
                        int remainingPower = pathFinder.calculatePower(sourceX, sourceY, destX, destY, direction);
                        System.out.println("POWER " + remainingPower);
                        break;
                }
            }
            sc.close();
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}
