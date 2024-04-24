package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffManagementController;
import main.java.daos.StaffDAO;
import main.java.utils.types.StaffRole;

/**
 * A form for administrative staff as a boundary level object to perform changes to staff accounts.
 * Administrators can add, edit, or remove staff accounts using this form.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class AdminManagementForm implements Form {

    /** The controller responsible for managing staff accounts. */
    private StaffManagementController managementController;

    /** The scanner object used for input. */
    private Scanner scanner;

    /**
     * Constructs an AdminManagementForm object with the specified scanner.
     *
     * @param scanner the scanner object to be used for input
     */
    public AdminManagementForm(Scanner scanner){
        this.managementController = new StaffManagementController(new StaffDAO());
        this.scanner = scanner;
    }

    /**
     * Generates a form containing admin management actions and handles admin's input.
     * Checks if admin's input is valid within options 1-4.
     */
    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-----------------------------------------------------------------------\n" +
                               "|-----------------------Admin Management Actions----------------------|\n" +
                               "-----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                 |\n" +
                               "|                   1.Add Staff Account                               |\n" +
                               "|                   2.Edit Staff Account                              |\n" +
                               "|                   3.Remove Staff Account                            |\n" +
                               "|                   4.Go to Homescreen                                |\n" +
                               "-----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");
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
                    addStaffAccount();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    editStaffAccount();
                    break;
                case 3:
                    scanner.nextLine(); // Consume the newline character
                    removeStaffAccount();
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
     * Adds a new staff account based on admin's input - staff name, staff login id, staff role
     * Checks if role entered exists in StaffRole ENum.
     * Prints a message to admin personnel if staff account is successfully added or not.
     * 
     */
    private void addStaffAccount() {
        // Collect details from admin
        System.out.println("Enter new staff name:");
        String name = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter new staff login ID:");
        String loginId = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter staff role (Admin/Manager/Staff):");
        StaffRole role = null;
        while (role == null) {
            String roleInput = scanner.nextLine();
            scanner.nextLine(); // Consume the newline character
            try {
                role = StaffRole.valueOf(roleInput.toUpperCase());
                break;
            } 
            catch (IllegalArgumentException e) {
                System.out.println("Invalid role entered. Please enter one of the following roles: Admin, Manager, Staff.");
            }
        }
        System.out.println("Enter staff gender:");
        String gender = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter staff age:");
        int age = Integer.parseInt(scanner.nextLine());
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter staff branch:");
        String branch = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        if(managementController.addStaff(name, loginId, role, gender, age, branch)) {
            System.out.println("Staff account added successfully.");
        } else {
            System.out.println("Staff account could not be added.");
        }
    }

    /**
     * Edits an existing staff account based on admin's input - staff current login id, new login id, new password and new branch.
     * Prints a message to admin personnel if staff account is successfully updated or not.
     */
    private void editStaffAccount() {
        System.out.println("Enter staff login ID to edit:");
        String loginId = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter new login ID for the staff:");
        String newLoginId = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter new password for the staff:");
        String newPassword = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter new branch for the staff:");
        String newBranch = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character
    
        if (managementController.editStaff(loginId, newLoginId, newPassword, newBranch)) {
            System.out.println("Staff account updated successfully.");
        } else {
            System.out.println("Staff account could not be updated. Please check the details and try again.");
        }
    }

    /**
     * Removes an existing staff account based on admin's input - staff login id
     * Prints a message to admin personnel if staff account is successfully removed or not.
     */
    private void removeStaffAccount() {
        System.out.println("Enter staff login ID to remove:");
        String loginId = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character
    
        if (managementController.removeStaff(loginId)) {
            System.out.println("Staff account removed successfully.");
        } else {
            System.out.println("Staff account could not be removed.");
        }
    }
}