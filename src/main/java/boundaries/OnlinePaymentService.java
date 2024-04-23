package main.java.boundaries;
import java.util.List;
import java.util.Scanner;

import main.java.controllers.CustomerPaymentController;
import main.java.models.PaymentMethod;
public class OnlinePaymentService implements PaymentService {


    private String domain;
    private String email;
    private String password;
    private CustomerPaymentController paymentController;

    public OnlinePaymentService(CustomerPaymentController paymentController){
        this.paymentController = paymentController;
    }
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
                System.out.printf("%, ", item.getName());
            }
        }
        System.out.printf("%n");
        System.out.println("Enter Online Payment Provider:");
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
        if (paymentController.validatePayment(domain)){
            return true;
        }
        return false;
    }
}

