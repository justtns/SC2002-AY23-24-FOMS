package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffPaymentController;
import main.java.daos.PaymentDAO;

/**
 * A form for administrative staff as a boundary level object to perform payment actions.
 * Administrators can add or remove payment methods using this form.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class AdminPaymentForm implements Form {

    /** The controller responsible for managing payment methods. */
    private StaffPaymentController paymentController;

    /** The scanner object used for input. */
    private Scanner scanner;

    /**
     * Constructs an AdminPaymentForm object with the specified scanner.
     *
     * @param scanner the scanner object to be used for input
     */
    public AdminPaymentForm(Scanner scanner){
        this.paymentController = new StaffPaymentController(new PaymentDAO());
        this.scanner = scanner;
    }

    /**
     * Generates the admin payment form and handles admin's input.
     * Checks if user input is within options 1-3.
     */
    @Override
    public void generateForm(){

        System.out.print("\033[H\033[2J");
        System.out.flush();

        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-------------------------Admin Payment Actions----------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Add Payment Method                             |\n" +
                               "|                   2.Remove Payment Method                          |\n" +
                               "|                   3.Display Payment Methods                        |\n" +
                               "|                   4.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
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
                    scanner.nextLine(); // Consume the newline character
                    displayPaymentTypes();
                    break;
                case 4:
                    loop=false;
                    System.out.println("Returning to Homescreen...");

                    //time delay
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    /**
     * Gets details such as payment method name and payment type for adding a new payment method from the admin staff.
     * Prints a message to admin personnel if payment method is successfully added or not.
     */
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

    /**
     * Gets the payment method name for removal by paymentController.
     * Prints a message to admin personnel if payment method is successfully removed or not.
     */
    private void getRemovePaymentDetails() {
        System.out.println("Enter payment method name to remove: ");
        String name = scanner.nextLine();

        if (paymentController.removePaymentMethod(name)) {
            System.out.println("Payment method removed successfully.");
        } else {
            System.out.println("Failed to remove payment method.");
        }
    }

    /**
     * Displays the valid payment types by paymentController.
     */
    private void displayPaymentTypes() {
        paymentController.getPaymentTypes();
        
    }

}