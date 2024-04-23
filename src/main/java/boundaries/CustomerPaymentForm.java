package main.java.boundaries;

import java.util.Scanner;
import main.java.controllers.CustomerPaymentController;
import main.java.daos.OrderDAO;
import main.java.utils.loggers.CustomerSession;

public class CustomerPaymentForm implements Form{
    private CustomerSession session;
    private OrderDAO orderDAO;
    private CustomerPaymentController paymentController = new CustomerPaymentController(orderDAO);
    private Scanner scanner;

    public CustomerPaymentForm(CustomerSession session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;

    }
    
    public void generateForm() {
        System.out.println("----------------------------------------------------------------------\n" +
                           "|----------------------------Customer Payment------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                   Please select a payment method                   |\n" +
                           "|                   1.Online Payment                                 |\n" +
                           "|                   2.Credit/Debit Card Payment                      |\n" +
                           "----------------------------------------------------------------------\n" +
                            "\n");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                handlePayment(new OnlinePaymentService());
                break;
            case 2:
                handlePayment(new CreditDebitPaymentService());
                break;
            default:
                System.out.println("Invalid payment method, please try again.");
                generateForm();
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
        System.out.println("----------------------------------------------------------------------\n" +
                           "|----------------------------Customer Payment------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                   Would you like a receipt?                        |\n" +
                           "|                   1.Yes                                            |\n" +
                           "|                   2.No                                             |\n" +
                           "----------------------------------------------------------------------\n" +
                            "\n");

        int receiptChoice = scanner.nextInt();
        if (receiptChoice == 1) {
            System.out.println(paymentController.printReceipt(session.getOrderId()));
        } else {
            System.out.println("Thank you for your payment!");
        }
    }
}
