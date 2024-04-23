package main.java.boundaries;
import java.util.Scanner;

import main.java.controllers.CustomerPaymentController;

public class CreditDebitPaymentService implements PaymentService {

    private String cardProvider;
    private String number;
    private String expiry;
    private String cvc;
    private CustomerPaymentController paymentController;

    public CreditDebitPaymentService(CustomerPaymentController customerPaymentController){
        this.paymentController = customerPaymentController;
    }

    @Override
    public boolean simulatePayment(Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------\n" +
                           "|-------------------------Simulate Payment-------------------------------|\n" +
                           "--------------------------------------------------------------------------\n");

        System.out.println("Credit/Debit Card Payment Selected - Please enter your payment details");
        System.out.println("Enter CardProvider:");
        String cardProvider = scanner.nextLine();

        System.out.println("Enter card number:");
        @SuppressWarnings("unused")
        String number = scanner.nextLine();

        System.out.println("Enter expiry date:");
        @SuppressWarnings("unused")
        String expiry = scanner.nextLine();

        System.out.println("Enter cvc:");
        @SuppressWarnings("unused")
        String cvc = scanner.nextLine();


        if (authenticatePayment()) {
            System.out.println("Processing credit/debit card payment for Card Provider: " + cardProvider);
            return true; 
        } else {
            System.out.println("Payment authentication failed.");
            return false;
        }
    }

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

