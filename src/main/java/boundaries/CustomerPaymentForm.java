package main.java.boundaries;

import java.util.Scanner;
import main.java.controllers.CustomerPaymentController;
import main.java.daos.OrderDAO;
import main.java.utils.loggers.CustomerSession;

public class CustomerPaymentForm {
    private CustomerSession session;
    private OrderDAO orderDAO;
    private CustomerPaymentController paymentController = new CustomerPaymentController(orderDAO);

    public CustomerPaymentForm(CustomerSession session) {
        this.session = session;

    }
    Scanner scanner = new Scanner(System.in);
    
    public void promptPaymentMethod() {
        System.out.println("Please select a payment method:");
        System.out.println("1: Online Payment");
        System.out.println("2: Credit/Debit Card Payment");


        int choice = scanner.nextInt();
        scanner.nextLine();  
        switch (choice) {
            case 1:
                handlePayment(new OnlinePaymentService());
                break;
            case 2:
                handlePayment(new CreditDebitPaymentService());
                break;
            default:
                System.out.println("Invalid payment method, please try again.");
                promptPaymentMethod();
                return; 
        }
    }

    private void handlePayment(PaymentService paymentService) {
        if (paymentController.makePayment(session.getOrderId(), paymentService)) {
            System.out.println("Payment successful!");
            printReceiptOption();
        } else {
            System.out.println("Payment failed. Please check the details and try again.");
        }
    }

    private void printReceiptOption() {
        System.out.println("Would you like a receipt?");
        System.out.println("1: Yes");
        System.out.println("2: No");

        int receiptChoice = scanner.nextInt();
        if (receiptChoice == 1) {
            System.out.println(paymentController.printReceipt(session.getOrderId()));
        } else {
            System.out.println("Thank you for your payment!");
        }
    }
}
