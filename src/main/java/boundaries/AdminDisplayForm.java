package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffDisplayController;
import main.java.daos.StaffDAO;
import main.java.daos.BranchDAO;
import main.java.utils.types.StaffRole;

/**
 * A form for administrative staff as a boundary level object to display various information about staff members.
 * Administrators can view staff lists based on branch, role, gender, age, or display all staff members.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.3
 * @since 24-Apr-2024
 */
public class AdminDisplayForm implements Form {

    /** The controller responsible for managing the display of staff information. */
    private StaffDisplayController displayController;

    /** The scanner object used for input. */
    private Scanner scanner;

    /**
     * Constructs an AdminDisplayForm object with the specified scanner.
     *
     * @param scanner the scanner object to be used for input
     */
    public AdminDisplayForm(Scanner scanner){
        this.displayController = new StaffDisplayController(new StaffDAO(), new BranchDAO());
        this.scanner = scanner;
    }

    /**
     * Generates a form containing admin display actions and handles admin's input.
     * Checks if admin's input is valid within options 1-6.
     */
    @Override
    public void generateForm(){
        
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-----------------------------------------------------------------------\n" +
                               "|-----------------------Admin Display Actions-------------------------|\n" +
                               "-----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                 |\n" +
                               "|                   1.Display the List of Staff by Branch             |\n" +
                               "|                   2.Display the List of Staff by Role               |\n" +
                               "|                   3.Display the List of Staff by Gender             |\n" +
                               "|                   4.Display the List of Staff by Age                |\n" +
                               "|                   5.Display all Staff                               |\n" +
                               "|                   6.Go to Homescreen                                |\n" +
                               "-----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-6): \n");
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
                    staffListByBranch();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    staffListByRole();
                    break;
                case 3:
                    scanner.nextLine(); // Consume the newline character
                    staffListByGender();
                    break;
                case 4:
                    scanner.nextLine(); // Consume the newline character
                    staffListByAge();
                    break;
                case 5:
                    scanner.nextLine(); // Consume the newline character
                    staffList();
                    break;
                case 6:
                    loop=false;
                    System.out.println("Returning to Homescreen...");

                    // time delay
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.print("\033[H\033[2J");
                    System.out.flush();                   

                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-6)");
                    break;
            }
        }
    }

    /**
     * Displays the list of staff members for a particular branch.
     */
    private void staffListByBranch() {
        System.out.println("Enter the branch name:");
        String branch = scanner.nextLine();

        displayController.displayStaffListByBranch(branch);

        System.out.println("Press enter to return to the Admin Display Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the list of staff members for a particular role.
     * Checks if role entered exists in StaffRole ENum.
     */
    private void staffListByRole() {
        System.out.println("Enter the staff role (Admin/Manager/Staff):");
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
        displayController.displayStaffListByRole(role);

        System.out.println("Press enter to return to the Admin Display Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the list of staff members for a particular gender.
     * Checks if gender input is valid.
     */
    private void staffListByGender() {
        String gender = "";
        boolean validGender = false;

        while (!validGender) {
            System.out.println("Enter the gender (M/F):");
            gender = scanner.nextLine().trim().toUpperCase();
            
            if (gender.equals("M") || gender.equals("F")) {
                validGender = true;
            } else {
                System.out.println("Invalid input. Please enter 'M' for male or 'F' for female.");
            }
        }

        displayController.displayStaffListByGender(gender);

        System.out.println("Press enter to return to the Admin Display Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the list of staff members for a particular age.
     * Checks if age input is valid.
     */
    private void staffListByAge() {
        int age = -1;
        while (age == -1) {
            System.out.println("Enter the age:");
            try {
                age = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        displayController.displayStaffListByAge(age);

        System.out.println("Press enter to return to the Admin Display Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Displays the complete list of all staff members under the company.
     */
    private void staffList() {
        displayController.displayStaffList();

        System.out.println("Press enter to return to the Admin Display Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();

    }    
}