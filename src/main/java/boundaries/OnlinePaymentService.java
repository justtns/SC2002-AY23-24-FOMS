package main.java.boundaries;
import java.util.Scanner;
import main.java.utils.ScannerProvider;
public class OnlinePaymentService implements PaymentService {


    private String domain;
    private String email;
    private String password;
    @Override
    public boolean simulatePayment() {
        Scanner scanner = ScannerProvider.getScanner();
        System.out.println("Online Payment Selected - Please enter your payment details");

        System.out.println("Enter domain:");
        String domain = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        @SuppressWarnings("unused")
        String password = scanner.nextLine();

        System.out.println("Processing online payment for domain: " + domain + " with email: " + email);

        if (authenticatePayment()) {
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
        if (domain.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return false;
        }
        return true;
    }
}

