package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;

import java.util.Scanner;
import java.util.InputMismatchException;

import main.java.factories.FormFactory;


public class CustomerApp implements AppDisplay{
    public void execute(Scanner scanner){
        CustomerSession session = new CustomerSession();
        CustomerBranchSelectionForm selectBranch = new CustomerBranchSelectionForm(session, scanner);
        session = selectBranch.generateForm();
        FormFactory actionFactory = new FormFactory();
        Form form;
        Boolean loop=true;
        while (loop) {
            System.out.println("Order ID:" + session.getOrderId());
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Customer Menu--------------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.View menu and order                            |\n" +
                               "|                   2.Make Payment                                   |\n" +
                               "|                   3.Check on my order                              |\n" +
                               "|                   4.Logout                                         |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");
            int choice = -1;

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-4)");
                continue;
            }
            if (choice < 1 | choice > 4){
                System.out.println("Invalid Input. Please enter (1-4)");
            }
            else if (choice == 4){
                loop=false;
                System.out.println("Logging out...");
                break;
            }
            else{
                form = actionFactory.getForm(session, scanner, choice);
                form.generateForm();
            }
        }
    }
}
