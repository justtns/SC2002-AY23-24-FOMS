package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import main.java.actions.Actions;

import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;

import main.java.factories.ActionFactory;


public class CustomerApp {
    public void execute(){
        CustomerSession session = new CustomerSession();
        Scanner scanner = ScannerProvider.getScanner();
        CustomerBranchSelectionForm selectBranch = new CustomerBranchSelectionForm(session, scanner);
        session = selectBranch.branchSelectionView();
        ActionFactory actionFactory = new ActionFactory();
        Form form;
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
            if (choice < 1 | choice > 4){
                System.out.println("Invalid Input. Please enter (1-4)");
            }
            else{
                form = actionFactory.getAction(session, scanner, choice);
                form.generateForm();
            }
        }
        ScannerProvider.closeScanner();
    }
}
