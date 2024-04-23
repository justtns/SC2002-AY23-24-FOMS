package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffAssignmentController;
import main.java.daos.StaffDAO;
import main.java.utils.loggers.StaffSession;

public class AdminAssignmentForm implements Form {

    private StaffDAO staffDAO = new StaffDAO();
    private StaffAssignmentController assignmentController = new StaffAssignmentController(staffDAO);
    private StaffSession session;
    private Scanner scanner;

    public AdminAssignmentForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

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

    private void promoteStaff() {
        System.out.println("Enter Staff ID: ");
        String staffID = scanner.nextLine();

        if (assignmentController.promoteToManager(staffID)) {
            System.out.println("Staff promoted successfully.");
        } else {
            System.out.println("Failed to promote staff.");
        }
    }

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
