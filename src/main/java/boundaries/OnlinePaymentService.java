package main.java.boundaries;

import java.util.List;
import java.util.Scanner;

import main.java.controllers.CustomerPaymentController;
import main.java.models.PaymentMethod;

/**
 * The OnlinePaymentService class implements the PaymentService interface for online payments service providers (e.g. Paypal etc.)
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class OnlinePaymentService implements PaymentService {

    /**
     * The domain of the online payment provider.
     */
    private String domain;

    /**
     * The email associated with the online payment.
     */
    private String email;

    /**
     * The password associated with the online payment.
     */
    private String password;

    /**
     * The controller responsible for handling customer payments.
     */
    private CustomerPaymentController paymentController;

    /**
     * Constructs an OnlinePaymentService with the specified CustomerPaymentController.
     * 
     * @param paymentController The CustomerPaymentController to handle payment operations.
     */
    public OnlinePaymentService(CustomerPaymentController paymentController){
        this.paymentController = paymentController;
    }

    /**
     * Method to simulate the online payment process.
     * Checks if online payment method is available and whether payment is authenticated successfully.
     * 
     * @param scanner The Scanner object for user input.
     * @return true if the payment is available and successfully authenticated, false otherwise.
     */
    @Override
    public boolean simulatePayment(Scanner scanner) {
        System.out.println("Online Payment Selected - Please enter your payment details");
        List<PaymentMethod> paymentItems = paymentController.getValidPaymentTypes();
        System.out.println("Available Online Payment Methods: ");
        if (paymentItems.isEmpty()) {
            System.out.println("No online methods available.");
        }
        for (PaymentMethod item : paymentItems) {
            if (item.getType().equalsIgnoreCase("online")) {
                System.out.printf("%s, ", item.getName());
            }
        }
        System.out.printf("%n");
        System.out.println("Enter Online Payment Provider:");
        this.domain = scanner.nextLine();
        System.out.println("Enter email:");
        this.email = scanner.nextLine();
        System.out.println("Enter password:");
        this.password = scanner.nextLine();

        System.out.println("Processing online payment for domain: " + domain + " with email: " + email);

        if (authenticatePayment()) {
            return true; 
        } else {
            System.out.println("Payment authentication failed.");
            return false;
        }
    }

    /**
     * Method to authenticate the online payment.
     * 
     * @return true if the payment is successfully authenticated, false otherwise.
     */
    @Override
    public boolean authenticatePayment() {
        System.out.println("Authenticating payment...");

        // check if all fields are filled
        if (domain.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return false;
        }
        if (paymentController.validatePayment(domain)){
            return true;
        }
        return false;
    }
}