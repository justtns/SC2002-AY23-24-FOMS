package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffDisplayController;
import main.java.daos.StaffDAO;
import main.java.utils.loggers.StaffSession;

/**
 * The ManagerDisplayForm class is a form that is a boundary level object for managers to perform display actions
 * such as displaying the list of staff in their branch.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class ManagerDisplayForm implements Form {
    /**
     * The controller for displaying staff information.
     */
    private StaffDisplayController displayController = new StaffDisplayController(new StaffDAO());

    /**
     * The session of the manager.
     */
    private StaffSession session;

    /**
     * The scanner object used for user input.
     */
    private Scanner scanner;

    /**
     * Constructs a ManagerDisplayForm with the given session and scanner.
     * 
     * @param session The session of the manager.
     * @param scanner The scanner object used for user input.
     */
    public ManagerDisplayForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    /**
     * Generates the manager display form containing all manager display actions.
     * This method presents the manager with options to display the list of staff in their branch
     * or return to the homescreen.
     * Checks if manager input is valid within options 1-2.
     */
    @Override
    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Manager Display Actions----------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Display the List of Staff in your Branch       |\n" +
                               "|                   2.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-2): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    displayStaffList();
                    break;
                case 2:
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-2)");
                    break;
            }
        }
    }

    /**
     * Displays the list of staff in the manager's branch.
     */
    private void displayStaffList() {
        String staffUserID = session.getStaffUserID();
        displayController.managerDisplayStaffList(staffUserID);
    }
}