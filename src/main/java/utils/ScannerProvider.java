package main.java.utils;

import java.util.Scanner;

/**
 * Provides a utility class for obtaining a Scanner object.
 * This class ensures a single Scanner instance is used throughout the application.
 * It also provides a method to close the Scanner when it is no longer needed.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class ScannerProvider {
    
    /** The Scanner object used for input operations. */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Retrieves the Scanner object.
     * 
     * @return the Scanner object
     */
    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * Closes the Scanner object.
     * It is recommended to call this method when the Scanner is no longer needed.
     */
    public static void closeScanner() {
        scanner.close();
    }
}