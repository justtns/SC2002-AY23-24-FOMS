package main.java.boundaries;

import main.java.controllers.CustomerBranchController;
import main.java.daos.BranchDAO;
import main.java.models.Branch;
import main.java.utils.loggers.CustomerSession;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerBranchSelectionForm {
    // input: Branch selection: The user selects a branch by entering the corresponding number.

    private Scanner scanner;
    private BranchDAO branchDAO = new BranchDAO();
    private CustomerBranchController branchController = new CustomerBranchController(branchDAO);
    private CustomerSession session;

    public CustomerBranchSelectionForm(CustomerSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    public CustomerSession generateForm(){
        // Display branches for selection
        List<Branch> branches = branchController.getBranches();
        int choice;
        for (int i = 0; i < branches.size(); i++) {
            System.out.println((i) + ". " + branches.get(i).getName());
        }
        while (true){
            System.out.print("Please enter the branch number: ");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input...");
                scanner.nextLine(); // Consume invalid input
                continue;
            }
            if (choice < 0 || choice >= branches.size()) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            else{
                this.session.setBranch(branches.get(choice).getName());
                break;
            }
        }
        return this.session;
    }
}
