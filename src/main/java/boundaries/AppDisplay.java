package main.java.boundaries;

import java.util.Scanner;

/**
 * An App Display Interface that will be implemented by StaffApp and CustomerApp
 * Implementing classes provide methods to execute the display of the application interface.
 * This interface is designed to abstract the display functionality, allowing for flexibility 
 * and customization in how the application is presented in its implementations
 * 
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public interface AppDisplay {

    /**
     * Executes the display of the App.
     * 
     * @param scanner the scanner object to be used for input
     */
    public void execute(Scanner scanner);
}
