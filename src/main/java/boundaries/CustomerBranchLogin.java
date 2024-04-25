package main.java.boundaries;

import main.java.controllers.CustomerBranchController;
import main.java.daos.BranchDAO;
import main.java.models.Branch;
import main.java.utils.loggers.CustomerSession;

import java.util.List;
import java.util.Scanner;

/**
 * The CustomerBranchLogin class is an authentication check for customers to choose which branch they wish to order from.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerBranchLogin {

    /**
     * The Scanner object for user input.
     */
    private Scanner scanner;

    /**
     * The DAO object for retrieving branches.
     */
    private BranchDAO branchDAO = new BranchDAO();

    /**
     * The controller for handling customer branch operations.
     */
    private CustomerBranchController branchController = new CustomerBranchController(branchDAO);

    /**
     * The customer session associated with the form.
     */
    private CustomerSession session;

    /**
     * Constructs a CustomerBranchSelectionForm with the specified customer session and scanner.
     * 
     * @param session The customer session.
     * @param scanner The Scanner object for user input.
     */
    public CustomerBranchLogin(CustomerSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    /**
     * Generates the branch selections and handle's customer's input for choice of branch.
     * Checks if customer input is within available branch options.
     * 
     * @return The updated customer session after branch selection.
     */
    public CustomerSession generateForm(){
        // Display branches for selection
        
        System.out.print("\033[H\033[2J");
        System.out.flush();


        List<Branch> branches = branchController.getBranches();
        int choice;
        System.out.println("----------------------------------------------------------------------\n" +
                           "|-----------------------Customer Branch Selection--------------------|\n" +
                           "----------------------------------------------------------------------");
        for (int i = 0; i < branches.size(); i++) {
            System.out.println((i) + ". " + branches.get(i).getName());
        }
        while (true){
            System.out.print("Please enter the branch number: ");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
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