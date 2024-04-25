package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffBranchController;
import main.java.daos.BranchDAO;
import main.java.daos.StaffDAO;

/**
 * A form for administrative staff as a boundary level object to perform branch-reltaed actions.
 * This form provides options for opening a new branch by providing its name, location, and capacity,
 * and for closing an existing branch, as well as viewing all available branches.
 * This form is implemented from the Form interface.
 *
 * @author SDDA Team 1
 * @version 1.3
 * @since 24-Apr-2024
 */
public class AdminBranchForm implements Form {

    /** The controller responsible for handling branch-related operations. */
    private StaffBranchController branchController;

    /** The scanner object used for input. */
    private Scanner scanner;

    /**
     * Constructs an AdminBranchForm object with the specified scanner.
     *
     * @param scanner the scanner object to be used for input
     */
    public AdminBranchForm(Scanner scanner){
        this.branchController = new StaffBranchController(new BranchDAO(), new StaffDAO());
        this.scanner = scanner;
    }

    /**
     * Generates a form containing admin branch actions and handles admin's input.
     * Checks if admin's input is valid within options 1-4.
     */
    @Override
    public void generateForm(){

        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-----------------------------------------------------------------------\n" +
                               "|-----------------------Admin Branch Actions--------------------------|\n" +
                               "-----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                 |\n" +
                               "|                   1.Open Branch                                     |\n" +
                               "|                   2.Close Branch                                    |\n" +
                               "|                   3.View All Branches                               |\n" +
                               "|                   4.Go to Homescreen                                |\n" +
                               "-----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    openBranch();
                    break;
                case 2:
                    closeBranch();
                    break;
                case 3:
                    viewBranch();
                    break;
                case 4:
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    /**
     * Opens a new branch based on admin's input - branch name, location and capacity.
     * Checks branch capcity input to see if it is valid or not.
     * Prints a message to admin personnel if branch is successfully opened or not.
     */
    private void openBranch() {
        System.out.println("Enter branch name:");
        String name = scanner.nextLine();

        System.out.println("Enter branch location:");
        String location = scanner.nextLine();

        int capacity = -1;
        while (capacity == -1) {
            System.out.println("Enter branch capacity:");
            try {
                capacity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if(branchController.openBranch(name, location, capacity)){
            System.out.println("Branch opened successfully: " + name);
        }
        else{
            System.out.println("A branch with the name \"" + name + "\" already exists.");
        }
    }

    /**
     * Closes an existing branch based on admin's input - branch name
     * Prints a message to admin personnel if branch is successfully closed or not.
     */
    private void closeBranch() {
        System.out.println("Enter branch name:");
        String name = scanner.nextLine();
        
        if (branchController.closeBranch(name)){
            System.out.println("Branch closed successfully: " + name);
        }
        else{
            System.out.println("Branch not found: " + name);
        }
    }


    /**
     * Displays all available branches.
     * Retrieves branch details from the controller and formats them for display.
     */
    private void viewBranch() {        
        branchController.displayAllBranches();
    }
}