package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import java.util.Scanner;
import java.util.InputMismatchException;
import main.java.applications.Actions;
import main.java.applications.CustomerOrderingAction;
import main.java.applications.CustomerPaymentAction;
import main.java.applications.CustomerPostOrderAction;



public class CustomerApp {
    public void execute(){
        CustomerSession session = new CustomerSession();
        CustomerBranchSelectionForm selectBranch = new CustomerBranchSelectionForm(session);
        session = selectBranch.branchSelectionView();
        Scanner scanner = new Scanner(System.in);
        Actions action;
        Boolean loop=true;
        System.out.println("-------------------------------------------------------------------\n" +
        "-----------------------------Customer Menu---------------------------\n" +
        "-------------------------------------------------------------------\n" +
        "                         Choose an option:\n" +
        "                         1.Place an order\n" +
        "                         2.Check on my order\n" +
        "                         3.Logout\n" +

        "---------------------------------------------------------------------\n" +
        "\n" +
        "Enter your choice (1-3): \n");
        while (loop) {
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-3)");
                continue;
            }
            switch (choice) {
                case 1:
                    action = new CustomerOrderingAction();
                    action.execute(session);
                    action = new CustomerPaymentAction();
                    action.execute(session);
                    break;
                case 2:
                action = new CustomerPostOrderAction();
                action.execute(session);
                    break;
                case 3:
                    loop=false;
                    System.out.println("Logging out...");
                    break;
                
            
                default:
                System.out.println("Invalid Input. Please enter (1-3)");
                    break;
            }
            scanner.close();
        }
        
    }
}
