package main.java.boundaries;
import java.util.Scanner;

public class CreditDebitPaymentService implements PaymentService {

    private String bank;
    private String number;
    private String expiry;
    private String cvc;

    @Override
    public boolean simulatePayment(Scanner scanner) {
        System.out.println("--------------------------------------------------------------------------\n" +
                           "|-------------------------Simulate Payment-------------------------------|\n" +
                           "--------------------------------------------------------------------------\n");

        System.out.println("Credit/Debit Card Payment Selected - Please enter your payment details");
        System.out.println("Enter bank:");
        String bank = scanner.nextLine();

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
            System.out.println("Processing credit/debit card payment for bank: " + bank);
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
        if (bank.isEmpty() || number.isEmpty() || expiry.isEmpty() || cvc.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return false;
        }

        return true;
    }
}

