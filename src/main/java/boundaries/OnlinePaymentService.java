package main.java.boundaries;
import java.util.Scanner;

public class OnlinePaymentService implements PaymentService {

    private Scanner scanner;

    public OnlinePaymentService() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public boolean simulatePayment() {

        System.out.println("Online Payment Selected - Please enter your payment details");

        System.out.println("Enter domain:");
        String domain = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        System.out.println("Processing online payment for domain: " + domain + " with email: " + email);

        return true; 
    }
}

