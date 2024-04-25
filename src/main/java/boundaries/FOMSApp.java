package main.java.boundaries;

import java.util.Scanner;
import main.java.utils.ScannerProvider;
import main.java.utils.types.LoginRole;

/**
 * Represents the main display for the Fastfood Ordering and Management System (FOMS).
 * It is a boundary level object that provides interactions with users of the FOMS.
 * It allows users to choose between customer and staff roles and provides access to the respective App functionalities, or exit the App.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class FOMSApp {
    /** The scanner object for user input. */
    private Scanner scanner = ScannerProvider.getScanner();

    /** The login role chosen by the user. */
    private LoginRole loginRole;

    /**
     * Executes the Fastfood Ordering and Management System (FOMS).
     * Prompts users to choose between customer and staff roles or exit the system.
     * It checks if user input is valid within options 1-3.
     */
    public void execute() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean loop = true;
        int choice;
        System.out.println(
            "        ________  ______   __       __   ______  \n" +
            "       /        |/      \\ /  \\     /  | /      \\ \n" +
            "       $$$$$$$$//$$$$$$  |$$  \\   /$$ |/$$$$$$  |\n" +
            "       $$ |__   $$ |  $$ |$$$  \\ /$$$ |$$ \\__$$/ \n" +
            "       $$    |  $$ |  $$ |$$$$  /$$$$ |$$      \\ \n" +
            "       $$$$$/   $$ |  $$ |$$ $$ $$/$$ | $$$$$$  |\n" +
            "       $$ |     $$ \\__$$ |$$ |$$$/ $$ |/  \\__$$ |\n" +
            "       $$ |     $$    $$/ $$ | $/  $$ |$$    $$/ \n" +
            "       $$/       $$$$$$/  $$/      $$/  $$$$$$/  "
        );
        System.out.println("------------------------------------------------------------------------\n" +
                            "|----------------Fastfood ordering and management System --------------|\n" +
                            "------------------------------------------------------------------------\n" +
                            "|                      Choose an option:                               |\n" +
                            "|                      1. Customer                                     |\n" +
                            "|                      2. Staff                                        |\n" +
                            "|                      3. Exit                                         |\n" +
                            "------------------------------------------------------------------------\n");
        while (loop) {
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                continue;
            }
            if (choice == 3) {
                System.out.println("Exiting System...");
                loop = false;
                break;
            }
            if (choice == 1 | choice == 2) {
                enterRole(choice);
                AppDisplay app;
                switch (loginRole) {
                    case CUSTOMER:
                        app = new CustomerApp();
                        app.execute(scanner);
                        System.out.println(
                            "        ________  ______   __       __   ______  \n" +
                            "       /        |/      \\ /  \\     /  | /      \\ \n" +
                            "       $$$$$$$$//$$$$$$  |$$  \\   /$$ |/$$$$$$  |\n" +
                            "       $$ |__   $$ |  $$ |$$$  \\ /$$$ |$$ \\__$$/ \n" +
                            "       $$    |  $$ |  $$ |$$$$  /$$$$ |$$      \\ \n" +
                            "       $$$$$/   $$ |  $$ |$$ $$ $$/$$ | $$$$$$  |\n" +
                            "       $$ |     $$ \\__$$ |$$ |$$$/ $$ |/  \\__$$ |\n" +
                            "       $$ |     $$    $$/ $$ | $/  $$ |$$    $$/ \n" +
                            "       $$/       $$$$$$/  $$/      $$/  $$$$$$/  "
                        );
                        System.out.println("------------------------------------------------------------------------\n" +
                                            "|----------------Fastfood ordering and management System --------------|\n" +
                                            "------------------------------------------------------------------------\n" +
                                            "|                      Choose an option:                               |\n" +
                                            "|                      1. Customer                                     |\n" +
                                            "|                      2. Staff                                        |\n" +
                                            "|                      3. Exit                                         |\n" +
                                            "------------------------------------------------------------------------\n");
                        break;
                    case STAFF:
                        app = new StaffApp();
                        app.execute(scanner);
                        System.out.println(
                            "        ________  ______   __       __   ______  \n" +
                            "       /        |/      \\ /  \\     /  | /      \\ \n" +
                            "       $$$$$$$$//$$$$$$  |$$  \\   /$$ |/$$$$$$  |\n" +
                            "       $$ |__   $$ |  $$ |$$$  \\ /$$$ |$$ \\__$$/ \n" +
                            "       $$    |  $$ |  $$ |$$$$  /$$$$ |$$      \\ \n" +
                            "       $$$$$/   $$ |  $$ |$$ $$ $$/$$ | $$$$$$  |\n" +
                            "       $$ |     $$ \\__$$ |$$ |$$$/ $$ |/  \\__$$ |\n" +
                            "       $$ |     $$    $$/ $$ | $/  $$ |$$    $$/ \n" +
                            "       $$/       $$$$$$/  $$/      $$/  $$$$$$/  "
                        );
                        System.out.println("------------------------------------------------------------------------\n" +
                                            "|----------------Fastfood ordering and management System --------------|\n" +
                                            "------------------------------------------------------------------------\n" +
                                            "|                      Choose an option:                               |\n" +
                                            "|                      1. Customer                                     |\n" +
                                            "|                      2. Staff                                        |\n" +
                                            "|                      3. Exit                                         |\n" +
                                            "------------------------------------------------------------------------\n");
                        break;
                }
            } else {
                System.out.println("Invalid Input. Please enter a number (1-3).");
            }
        }
        ScannerProvider.closeScanner();
    }

    /**
     * Sets the login role based on the user's choice.
     * 
     * @param choice the user's choice (1 for customer, 2 for staff)
     */
    public void enterRole(int choice) {
        switch (choice) {
            case 1:
                this.loginRole = LoginRole.CUSTOMER;
                break;
            case 2:
                this.loginRole = LoginRole.STAFF;
                break;
            default:
                System.out.println("Invalid Key! Enter your choice (1-2)");
                break;
        }
    }
}