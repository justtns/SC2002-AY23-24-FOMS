package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffManagementController;
import main.java.daos.StaffDAO;
import main.java.utils.types.StaffRole;
import main.java.utils.loggers.StaffSession;

public class AdminManagementForm implements Form {

    private StaffDAO staffDAO = new StaffDAO();
    private StaffManagementController managementController = new StaffManagementController(staffDAO);
    private StaffSession session;
    private Scanner scanner;

    public AdminManagementForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Admin Management Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Add Staff Account\n" +
                    "                         2.Edit Staff Account\n" +
                    "                         3.Remove Staff Account\n" +
                    "                         4.Go to Homescreen\n" +    
                    "---------------------------------------------------------------------\n" +
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
                role = StaffRole.valueOf(roleInput);
            } 
            catch (IllegalArgumentException e) {
                System.out.println("Invalid role entered. Please enter one of the following roles: Admin, Manager, Staff.");
            }
        }
        System.out.println("Enter staff gender:");
        String gender = scanner.nextLine();
        System.out.println("Enter staff age:");
        int age = Integer.parseInt(scanner.nextLine());
        scanner.nextLine(); // Consume the newline character
        System.out.println("Enter staff branch:");
        String branch = scanner.nextLine();

        if(managementController.addStaff(name, loginId, role, gender, age, branch)) {
            System.out.println("Staff account added successfully.");
        } else {
            System.out.println("Staff account could not be added.");
        }
    }

    private void editStaffAccount() {
        System.out.println("Enter staff login ID to edit:");
        String loginId = scanner.nextLine();
        System.out.println("Enter new login ID for the staff:");
        String newLoginId = scanner.nextLine();
        System.out.println("Enter new password for the staff:");
        String newPassword = scanner.nextLine();
        System.out.println("Enter new branch for the staff:");
        String newBranch = scanner.nextLine();
    
        if (managementController.editStaff(loginId, newLoginId, newPassword, newBranch)) {
            System.out.println("Staff account updated successfully.");
        } else {
            System.out.println("Staff account could not be updated. Please check the details and try again.");
        }
    }

    private void removeStaffAccount() {
        System.out.println("Enter staff login ID to remove:");
        String loginId = scanner.nextLine();
    
        if (managementController.removeStaff(loginId)) {
            System.out.println("Staff account removed successfully.");
        } else {
            System.out.println("Staff account could not be removed.");
        }
    }
}
