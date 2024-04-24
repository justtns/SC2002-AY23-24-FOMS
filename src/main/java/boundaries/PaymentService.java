package main.java.boundaries;

import java.util.Scanner;

/**
 * The PaymentService interface defines methods for simulating payment and authenticating payment.
 * This interface will be implemented by OnlinePaymentService and CreditDebitPaymentService classes.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public interface PaymentService {
    
    /**
     * Abstract method to simulates the payment process.
     * 
     * @param scanner The Scanner object for user input.
     * @return true if the payment is successfully simulated, false otherwise.
     */
    boolean simulatePayment(Scanner scanner);

    /**
     * Abstrat method to authenticates the payment.
     * 
     * @return true if the payment is successfully authenticated, false otherwise.
     */
    boolean authenticatePayment();
}