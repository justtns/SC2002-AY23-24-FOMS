package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffDisplayController;
import main.java.daos.StaffDAO;
import main.java.utils.loggers.StaffSession;
import main.java.utils.types.StaffRole;

public class ManagerDisplayForm {

    private StaffDAO staffDAO = new StaffDAO();
    private StaffDisplayController displayController = new StaffDisplayController(staffDAO, staffUserID);
    private String staffUserID;
    private StaffRole staffRole;
    private Scanner scanner;

    public ManagerDisplayForm(StaffSession session, Scanner scanner){
        this.staffUserID = session.getStaffUserID();
        this.staffRole = session.getStaffRole();
        this.scanner = scanner;
    }

    public void ManagerDisplayView(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Manager Display Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Display the List of Staff in your Branch\n" +
                    "                         2.Logout\n" +    
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-2): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
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
                    System.out.println("Logging out...");
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    private void displayStaffList() {
        StaffDisplayController.displayStaffList();
    }
}
