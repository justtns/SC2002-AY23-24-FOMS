package main.java.utils;

import java.util.Scanner;

public class ScannerProvider {
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void closeScanner() {
        scanner.close();
    }
}
