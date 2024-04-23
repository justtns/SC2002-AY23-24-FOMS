package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffDisplayController;
import main.java.daos.StaffDAO;
import main.java.utils.types.StaffRole;

public class AdminDisplayForm implements Form {

    private StaffDAO staffDAO = new StaffDAO();
    private StaffDisplayController displayController = new StaffDisplayController(staffDAO);
    private Scanner scanner;
    public AdminDisplayForm(Scanner scanner){
        this.scanner = scanner;
    }
    public void generateForm(){
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
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-6)");
                    break;
            }
        }
    }

    private void staffListByBranch() {
        System.out.println("Enter the branch name:");
        String branch = scanner.nextLine();
        displayController.displayStaffListByBranch(branch);
    }

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
    }

    private void staffListByGender() {
        System.out.println("Enter the gender (Male/Female):");
        String gender = scanner.nextLine();
        displayController.displayStaffListByGender(gender);
    }

    private void staffListByAge() {
        System.out.println("Enter the age:");
        int age = Integer.parseInt(scanner.nextLine());
        scanner.nextLine(); // Consume the newline character
        displayController.displayStaffListByAge(age);
    }

    private void staffList() {
        displayController.displayStaffList();
    }    
}
