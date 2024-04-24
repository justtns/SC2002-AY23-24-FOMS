package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;

import java.util.Scanner;

import main.java.factories.FormFactory;

/**
 * The CustomerApp class represents the entry point for customer users.
 * It is a boundary level object that interacts with staff users.
 * This class provides functionality for customer users to view menu and place an order, make a payment,
 * check on their orders and logout.
 * This App is implemented from AppDisplay interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerApp implements AppDisplay {

    /**
     * Executes the customer application.
     * 
     * This method displays all possible options for customers at a restaurant. They can view menu and place orders,
     * make payment, or check on order status.
     * They are directed to the corresponding forms for further actions.
     * It checks if customer input is valid within 1-4.
     * 
     * @param scanner The Scanner object for user input.
     */
    public void execute(Scanner scanner) {
        CustomerSession session = new CustomerSession();
        CustomerBranchSelectionForm selectBranch = new CustomerBranchSelectionForm(session, scanner);
        session = selectBranch.generateForm();
        FormFactory actionFactory = new FormFactory();
        Form form;
        Boolean loop = true;
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
            if (choice < 1 || choice > 4) {
                System.out.println("Invalid Input. Please enter (1-4)");
            } else if (choice == 4) {
                loop = false;
                System.out.println("Logging out...");
                break;
            } else {
                form = actionFactory.getForm(session, scanner, choice);
                form.generateForm();
            }
        }
    }
}