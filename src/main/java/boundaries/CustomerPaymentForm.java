package main.java.boundaries;

import java.util.Scanner;
import main.java.controllers.CustomerPaymentController;
import main.java.utils.loggers.CustomerSession;


public class CustomerPaymentForm {
    private Scanner scanner = new Scanner(System.in);
    private CustomerPaymentController paymentController;
    private int orderId;

    public CustomerPaymentForm(CustomerSession session) {
        this.orderId = session.getOrderId();
        this.paymentController = new CustomerPaymentController(orderId);
    }

    public void promptPaymentMethod() {
        System.out.println("Please select a payment method:");
        System.out.println("1: Online Payment");
        System.out.println("2: Credit/Debit Card Payment");
    
        String choice = scanner.nextLine();
        PaymentService paymentService;
    
        switch (choice) {
            case "1":
                paymentService = new OnlinePaymentService();
                break;
            case "2":
                paymentService = new CreditDebitPaymentService();
                break;
            default:
                System.out.println("Invalid payment method, please try again.");
                promptPaymentMethod();
                return; 
        }
    
        if(paymentService != null) {
            paymentService.simulatePayment();
            paymentController.makePayment();

            System.out.println("Payment successful!");

            System.out.println("Would you like a receipt?");
            System.out.println("1: Yes");
            System.out.println("2: No");

            String receiptChoice = scanner.nextLine();
            if(receiptChoice.equals("1")) {
                System.out.println(paymentController.printReceipt());
            }

            System.out.println("Thank you for your payment!");

        }
    }
}

