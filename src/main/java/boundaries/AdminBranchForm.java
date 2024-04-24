package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffBranchController;
import main.java.daos.BranchDAO;

public class AdminBranchForm implements Form {
    private StaffBranchController branchController = new StaffBranchController(new BranchDAO());
    private Scanner scanner;

    public AdminBranchForm(Scanner scanner){
        this.scanner = scanner;
    }
    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-----------------------------------------------------------------------\n" +
                               "|-----------------------Admin Branch Actions--------------------------|\n" +
                               "-----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                 |\n" +
                               "|                   1.Open Branch                                     |\n" +
                               "|                   2.Close Branch                                    |\n" +
                               "|                   3.Go to Homescreen                                |\n" +
                               "-----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-3): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (InputMismatchException e) {
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
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    private void openBranch() {
        System.out.println("Enter branch name:");
        String name = scanner.nextLine();

        System.out.println("Enter branch location:");
        String location = scanner.nextLine();

        System.out.println("Enter branch capacity:");
        int capacity = Integer.parseInt(scanner.nextLine().trim());

        if(branchController.openBranch(name, location, capacity)){
            System.out.println("Branch opened successfully: " + name);
        }
        else{
            System.out.println("A branch with the name \"" + name + "\" already exists.");
        }
    }

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
}
