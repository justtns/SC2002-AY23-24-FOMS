package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffPaymentController;
import main.java.daos.PaymentDAO;
import main.java.utils.loggers.StaffSession;

public class AdminPaymentForm implements Form{

    private PaymentDAO paymentDAO = new PaymentDAO();
    private StaffPaymentController paymentController = new StaffPaymentController(paymentDAO);
    private StaffSession session;
    private Scanner scanner;

    public AdminPaymentForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-------------------------Admin Payment Actions----------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Add Payment Method                             |\n" +
                               "|                   2.Remove Payment Method                          |\n" +
                               "|                   3.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-3): \n");
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
                    getNewPaymentDetails();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    getRemovePaymentDetails();
                    break;
                case 3:
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    private void getNewPaymentDetails() {
        System.out.println("Enter payment method name: ");
        String name = scanner.nextLine();
        System.out.println("Enter payment type ");
        String type = scanner.nextLine();

        if (paymentController.addPaymentMethod(name, type)) {
            System.out.println("Payment method added successfully.");
        } else {
            System.out.println("Failed to add payment method.");
        }
    }

    private void getRemovePaymentDetails() {
        System.out.println("Enter payment method name to remove: ");
        String name = scanner.nextLine();

        if (paymentController.removePaymentMethod(name)) {
            System.out.println("Payment method removed successfully.");
        } else {
            System.out.println("Failed to remove payment method.");
        }
    }

}
