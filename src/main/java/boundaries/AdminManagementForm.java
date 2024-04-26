package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffManagementController;
import main.java.daos.StaffDAO;
import main.java.daos.BranchDAO;
import main.java.utils.types.StaffRole;

/**
 * A form for administrative staff as a boundary level object to perform changes to staff accounts.
 * Administrators can add, edit, or remove staff accounts using this form.
 * This form is implemented from the Form interface.
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
        this.managementController = new StaffManagementController(new StaffDAO(), new BranchDAO());
        this.scanner = scanner;
    }

    /**
     * Generates a form containing admin management actions and handles admin's input.
     * Checks if admin's input is valid within options 1-4.
     */
    @Override
    public void generateForm(){
        
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
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

                    //time delay
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.print("\033[H\033[2J");
                    System.out.flush();

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
     * Checks if role entered exists in StaffRole ENum, and if gender and age inputs are valid.
     * Prints a message to admin personnel if staff account is successfully added or not.
     * 
     */
    private void addStaffAccount() {
        // Collect details from admin
        System.out.println("Enter new staff name:");
        String name = scanner.nextLine();

        System.out.println("Enter new staff login ID:");
        String loginId = scanner.nextLine();

        System.out.println("Enter staff role (Admin/Manager/Staff):");
        StaffRole role = null;
        while (role == null) {
            String roleInput = scanner.nextLine();
            try {
                role = StaffRole.valueOf(roleInput.toUpperCase());
                break;
            } 
            catch (IllegalArgumentException e) {
                System.out.println("Invalid role entered. Please enter one of the following roles: Admin, Manager, Staff.");
            }
        }
        
        
        String gender = "";
        boolean validGender = false;

        while (!validGender) {
            System.out.println("Enter staff gender (M/F):");
            gender = scanner.nextLine().trim().toUpperCase();
            
            if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                validGender = true;
            } else {
                System.out.println("Invalid input. Please enter 'M' for male or 'F' for female.");
            }
        }

        int age = -1;
        while (age >= 0) {
            System.out.println("Enter staff age:");
            try {
                age = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        System.out.println("Enter staff branch:");
        String branch = scanner.nextLine();

        if(managementController.addStaff(name, loginId, role, gender, age, branch)) {
            System.out.println("Staff account added successfully.");
        } else {
            System.out.println("Staff account could not be added.");
        }

        System.out.println("Press enter to return to the Admin Management Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Edits an existing staff account based on admin's input - staff current login id, new login id, new password and new branch.
     * Prints a message to admin personnel if staff account is successfully updated or not.
     */
    private void editStaffAccount() {
        System.out.println("Enter staff login ID:");
        String loginId = scanner.nextLine();

        System.out.println("Enter new name for the staff:\n(Enter nil to skip)");
        String newName = scanner.nextLine();

        String newGender = "";
        boolean validGender = false;
        while (!validGender) {
            System.out.println("Enter new gender (M/F) for the staff:\n(Enter nil to skip)");
            newGender = scanner.nextLine().trim().toUpperCase();
            
            if (newGender.equalsIgnoreCase("M") || newGender.equalsIgnoreCase("F") || newGender.equalsIgnoreCase("nil")) {
                validGender = true;
            } else {
                System.out.println("Invalid input.");
            }
        }

        int newAge = -1;
        while (newAge >= 0) {
            System.out.println("Enter new age for the staff:\n(Enter 0 to skip)");
            try {
                newAge = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    
        if (managementController.editStaff(loginId, newName, newGender, newAge)) {
            System.out.println("Staff account updated successfully.");
        } else {
            System.out.println("Staff account could not be updated. Please check the details and try again.");
        }

        System.out.println("Press enter to return to the Admin Management Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Removes an existing staff account based on admin's input - staff login id
     * Prints a message to admin personnel if staff account is successfully removed or not.
     */
    private void removeStaffAccount() {
        System.out.println("Enter staff login ID to remove:");
        String loginId = scanner.nextLine();
    
        if (managementController.removeStaff(loginId)) {
            System.out.println("Staff account removed successfully.");
        } else {
            System.out.println("Staff account could not be removed.");
        }

        System.out.println("Press enter to return to the Admin Management Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    
}