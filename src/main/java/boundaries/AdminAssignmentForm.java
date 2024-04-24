package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffAssignmentController;
import main.java.daos.StaffDAO;

/**
 * A form as a boundary level object for admin staff to assign a manager to a branch, promote a staff to a manager
 * and transfer staff members across branches.
 * This form is implemented from the Form interface.
 *
 * @author SDDA Team 1
 * @version 1.4
 * @since 24-Apr-2024
 */
public class AdminAssignmentForm implements Form {

    /** The controller responsible for handling staff assignment operations. */
    private StaffAssignmentController assignmentController;

    /** The scanner object used for input. */
    private Scanner scanner;

    /**
     * Constructs an AdminAssignmentForm object with the specified scanner.
     *
     * @param scanner the scanner object to be used for input
     */
    public AdminAssignmentForm(Scanner scanner){
        this.assignmentController = new StaffAssignmentController(new StaffDAO());
        this.scanner = scanner;
    }

    /**
     * Generates a form of admin assignment actions and handles admin's input.
     * Checks if admin's input is valid within options 1-4.
     */
    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Admin Assignment Actions---------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Assign Manager to Branch                       |\n" +
                               "|                   2.Promote Staff to Manager                       |\n" +
                               "|                   3.Transfer Staff                                 |\n" +
                               "|                   4.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");
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
                    assignManagerToBranch();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    promoteStaff();
                    break;
                case 3:
                    scanner.nextLine(); // Consume the newline character
                    transferStaffBranch();
                    break;
                case 4:
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    /**
     * Assigns a manager to a branch based on admin's input - StaffID and Branch.
     * Prints a message to admin personnel if assignment is successful or not.
     */
    private void assignManagerToBranch() {
        System.out.println("Enter Staff ID: ");
        String staffID = scanner.nextLine();

        System.out.println("Enter Branch to assign: ");
        String branch = scanner.nextLine();

        if (assignmentController.assignManager(staffID, branch)) {
            System.out.println("Manager assigned successfully.");
        } else {
            System.out.println("Failed to assign manager.");
        }
    }

    /**
     * Promotes a staff member to a manager based on admin's input - StaffID.
     * Prints a message to admin personnel if promotion of staff is successful or not.
     */
    private void promoteStaff() {
        System.out.println("Enter Staff ID: ");
        String staffID = scanner.nextLine();

        if (assignmentController.promoteToManager(staffID)) {
            System.out.println("Staff promoted successfully.");
        } else {
            System.out.println("Failed to promote staff.");
        }
    }

    /**
     * Transfers a staff member from one branch to another based on admin's input - StaffID, old branch & new branch.
     * Prints a message to admin personnel if transfer of staff is successful or not.
     */
    private void transferStaffBranch(){
        System.out.println("Enter Staff ID: ");
        String staffID = scanner.nextLine();

        System.out.println("Enter old branch: ");
        String oldBranch = scanner.nextLine();

        System.out.println("Enter new branch: ");
        String newBranch = scanner.nextLine();

        if (assignmentController.transferStaff(staffID, oldBranch, newBranch)) {
            System.out.println("Staff transferred successfully.");
        } else {
            System.out.println("Failed to transfer staff.");
        }
    }
}