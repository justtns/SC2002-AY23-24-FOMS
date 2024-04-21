package main.java.boundaries;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;

import main.java.models.Branch;
import main.java.controllers.CustomerBranchController;
import main.java.daos.BranchDAO;
import main.java.utils.loggers.CustomerSession;

public class CustomerBranchSelectionForm {
    private Scanner scanner;
    private BranchDAO branchDAO = new BranchDAO();
    private CustomerBranchController branchController = new CustomerBranchController(branchDAO);
    private CustomerSession session;

    public CustomerBranchSelectionForm(CustomerSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    public CustomerSession branchSelectionView(){
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
