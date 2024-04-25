package main.java.boundaries;

import java.util.Scanner;
import main.java.controllers.CustomerPaymentController;
import main.java.daos.OrderDAO;
import main.java.daos.PaymentDAO;
import main.java.utils.loggers.CustomerSession;

/**
 * A form for customers as a boundary level object to make payment.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class CustomerPaymentForm implements Form {
    
    /**
     * The current customer session
     */
    private CustomerSession session; 

    /**
     * Controller for managing customer payments
     */
    private CustomerPaymentController paymentController;

    /**
     * Scanner object for user input
     */
    private Scanner scanner;

    /**
     * Constructs a CustomerPaymentForm object with the specified session and scanner.
     * 
     * @param session The customer session.
     * @param scanner The scanner object for user input.
     */
    public CustomerPaymentForm(CustomerSession session, Scanner scanner) {
        this.session = session;
        this.paymentController = new CustomerPaymentController(new OrderDAO(), new PaymentDAO());
        this.scanner = scanner;
    }
    
    /**
     * Generates the form for customer payment and handles customer input for payment selection.
     * It includes the 2 main payment options of Online Payment (1) and Credit/Debit Card Payment (2).
     * Checks if customer input is within available options 1-2.
     */
    @Override
    public void generateForm() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("----------------------------------------------------------------------\n" +
                           "|----------------------------Customer Payment------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                   Please select a payment method                   |\n" +
                           "|                   1.Online Payment                                 |\n" +
                           "|                   2.Credit/Debit Card Payment                      |\n" +
                           "----------------------------------------------------------------------\n" +
                            "\n");

        int choice = Integer.parseInt(scanner.nextLine().trim());

        switch (choice) {
            case 1:
                handlePayment(new OnlinePaymentService(paymentController));
                break;
            case 2:
                handlePayment(new CreditDebitPaymentService(paymentController));
                break;
            default:
                System.out.println("Invalid payment method, please try again.");
                generateForm();
                return; 
        }
    }

    /**
     * Method to handles the selected payment method.
     * Prints a message to customers if payment is successful or not.
     * 
     * @param paymentService The payment service to be used.
     */
    private void handlePayment(PaymentService paymentService) {
        if (paymentController.makePayment(session.getOrderId(), paymentService)) {
            System.out.println("Payment successful!");
            printReceiptOption();
        } else {
            System.out.println("Payment failed. Please check the details and try again.");
        }
    }

    /**
     * Method to prompt customers to choose whether to print a receipt or not and prints it out accordingly.
     * Checks if input is valid from yes (1) or no (2).
     */
    private void printReceiptOption() {

        boolean loop = true;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                            "|----------------------------Customer Payment------------------------|\n" +
                            "----------------------------------------------------------------------\n" +
                            "|                   Would you like a receipt?                        |\n" +
                            "|                   1.Yes                                            |\n" +
                            "|                   2.No                                             |\n" +
                            "----------------------------------------------------------------------\n" +
                                "\n");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
            if (choice == 1) {
                System.out.println(paymentController.printReceipt(session.getOrderId()));
            } else {
                System.out.println("Thank you for your payment!");
            }
            return;
        }
    }
}