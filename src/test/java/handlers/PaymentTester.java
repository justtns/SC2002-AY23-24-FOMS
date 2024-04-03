package test.java.handlers;

import main.java.entities.*;
import main.java.handlers.PaymentHandler;

public class PaymentTester {
    
    public static void main(String[] args) {
        PaymentHandler paymentHandler = new PaymentHandler();

        // Test adding payments
        OnlinePayment onlinePayment = new OnlinePayment("test@example.com", "password123", "example.com");
        CreditDebitPayment creditDebitPayment = new CreditDebitPayment("John Doe", "1234567890123456", "123", "12/28", "Visa");

        System.out.println("Adding Online Payment...");
        paymentHandler.addElement(onlinePayment);
        System.out.println("Adding Credit/Debit Payment...");
        paymentHandler.addElement(creditDebitPayment);

        // Test listing payments
        System.out.println("Listing all Payment Methods:");
        paymentHandler.listElement();

        // Test removing payments
        System.out.println("Removing Online Payment...");
        paymentHandler.removeElement(onlinePayment);
        System.out.println("Listing all Payment Methods after removing one:");
        paymentHandler.listElement();

        // Optionally, test update functionality
        System.out.println("Updating Credit/Debit Payment...");
        CreditDebitPayment updatedCreditDebitPayment = new CreditDebitPayment("Jane Doe", "9876543210987654", "321", "01/22", "MasterCard");
        paymentHandler.updateElement(creditDebitPayment, updatedCreditDebitPayment);
        System.out.println("Listing all Payment Methods after updating:");
        paymentHandler.listElement();
    }
}

