package main.java.boundaries;

import java.util.Scanner;

public interface PaymentService {
    
    // payment service simulates payment and authenticates payment 
    // - since authentification is part of payment process, and payment cannot be made without authentification, it does not violate SRP

    boolean simulatePayment(Scanner scanner);

    boolean authenticatePayment();
}
