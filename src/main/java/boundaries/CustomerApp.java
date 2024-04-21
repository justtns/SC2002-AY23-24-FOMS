package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;
import main.java.applications.Actions;
import main.java.applications.CustomerOrderingAction;
import main.java.applications.CustomerPaymentAction;
import main.java.applications.CustomerPostOrderAction;



public class CustomerApp {
    public void execute(){
        CustomerSession session = new CustomerSession();
        Scanner scanner = ScannerProvider.getScanner();
        CustomerBranchSelectionForm selectBranch = new CustomerBranchSelectionForm(session, scanner);
        session = selectBranch.branchSelectionView();
        Actions action;
        Boolean loop=true;
        while (loop) {
            System.out.println("Order ID:" + session.getOrderId());
            System.out.println("-------------------------------------------------------------------\n" +
            "-----------------------------Customer Menu---------------------------\n" +
            "-------------------------------------------------------------------\n" +
            "                         Choose an option:\n" +
            "                         1.View menu and order\n" +
            "                         2.Make Payment\n" +
            "                         3.Check on my order\n" +
            "                         4.Logout\n" +

            "---------------------------------------------------------------------\n" +
            "\n" +
            "Enter your choice (1-4): \n");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-4)");
                continue;
            }
            switch (choice) {
                case 1:
                    action = new CustomerOrderingAction();
                    action.execute(session, scanner);
                    break;
                case 2:
                    action = new CustomerPaymentAction();
                    action.execute(session, scanner);
                    break;
                case 3:
                    action = new CustomerPostOrderAction();
                    action.execute(session, scanner);
                    break;
                case 4:
                    loop=false;
                    System.out.println("Logging out...");
                    break;
                
            
                default:
                System.out.println("Invalid Input. Please enter (1-4)");
                    break;
            }
        }
        ScannerProvider.closeScanner();
    }
}
