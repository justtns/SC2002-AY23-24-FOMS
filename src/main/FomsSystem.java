package main;

import main.java.boundaries.FOMSApp;

/**
 * The main class for the FOMS system.
 * This class is responsible for initializing and starting the FOMS application.
 *
 * @author SDDA Team 1
 * @version 1.1
 * @since 25-Apr-2024
 */
public class FomsSystem {
    
    /**
     * Default constructor for FomsSystem.
     */
    public FomsSystem() {
    }

    /**
     * The main method that serves as the entry point for the FOMS application.
     * It clears the console and then creates and executes an instance of FOMSApp.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Clearing the console screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Creating an instance of FOMSApp
        FOMSApp fomsApp = new FOMSApp();

        // Executing the FOMS application
        fomsApp.execute();
    }
}