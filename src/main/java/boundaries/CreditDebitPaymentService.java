package main.java.boundaries;
import java.util.Scanner;

public class CreditDebitPaymentService implements PaymentService {

    private Scanner scanner;

    public CreditDebitPaymentService() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean simulatePayment() {

        System.out.println("Credit/Debit Card Payment Selected - Please enter your payment details");
        System.out.println("Enter bank:");
        String bank = scanner.nextLine();
        System.out.println("Enter card number:");
        String number = scanner.nextLine();
        System.out.println("Enter expiry date:");
        String expiry = scanner.nextLine();
        System.out.println("Enter cvc:");
        String cvc = scanner.nextLine();


        System.out.println("Processing credit/debit card payment for bank: " + bank);

        return true; 
    }
}

