package main.java.boundaries;

import java.util.List;
import java.util.Scanner;

import main.java.controllers.CustomerPaymentController;
import main.java.models.PaymentMethod;

/**
 * The CreditDebitPaymentService class implements the PaymentService interface for credit/debit card payments (e.g. Visa, AMEX etc.)
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CreditDebitPaymentService implements PaymentService {

    /**
     * The service provider of the credit/debit card (e.g. Visa, AMEX etc.)
     */
    private String cardProvider;

    /**
     * The card number.
     */
    private String number;

    /**
     * The expiry date of the card.
     */
    private String expiry;

    /**
     * The Card Verification Code (CVC) of the card.
     */
    private String cvc;

    /**
     * The controller responsible for handling customer payments.
     */
    private CustomerPaymentController paymentController;

    /**
     * Constructs a CreditDebitPaymentService with the specified CustomerPaymentController.
     * 
     * @param customerPaymentController The CustomerPaymentController to handle payment operations.
     */
    public CreditDebitPaymentService(CustomerPaymentController customerPaymentController){
        this.paymentController = customerPaymentController;
    }

    /**
     * Method to simulate the credit/debit card payment process.
     * Checks if credit/debit card payment method is available and whether payment is authenticated successfully.
     * 
     * @param scanner The Scanner object for user input.
     * @return true if the payment is available and successfully authenticated, false otherwise.
     */
    @Override
    public boolean simulatePayment(Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------\n" +
                           "|-------------------------Simulate Payment-------------------------------|\n" +
                           "--------------------------------------------------------------------------\n");

        System.out.println("Credit/Debit Card Payment Selected - Please enter your payment details");
        List<PaymentMethod> paymentItems = paymentController.getValidPaymentTypes();
        System.out.println("Available Online Payment Methods: ");
        if (paymentItems.isEmpty()) {
            System.out.println("No online methods available.");
        }
        for (PaymentMethod item : paymentItems) {
            if (item.getType().equalsIgnoreCase("creditdebit")) {
                System.out.printf("%s, ", item.getName());
            }
        }
        System.out.println("Enter CardProvider:");
        this.cardProvider = scanner.nextLine();

        System.out.println("Enter card number:");
        this.number = scanner.nextLine();

        System.out.println("Enter expiry date:");
        this.expiry = scanner.nextLine();

        System.out.println("Enter cvc:");
        this.cvc = scanner.nextLine();


        if (authenticatePayment()) {
            System.out.println("Processing credit/debit card payment for Card Provider: " + cardProvider);
            return true; 
        } else {
            System.out.println("Payment authentication failed.");
            return false;
        }
    }

    /**
     * Method to authenticate the credit/debit card payment.
     * 
     * @return true if the payment is successfully authenticated, false otherwise.
     */
    @Override
    public boolean authenticatePayment() {
        System.out.println("Authenticating payment...");

        // check if all fields are filled
        if (cardProvider.isEmpty() || number.isEmpty() || expiry.isEmpty() || cvc.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return false;
        }
        if (paymentController.validatePayment(cardProvider)){
            return true;
        }
        return true;
    }
}