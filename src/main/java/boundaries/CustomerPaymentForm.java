package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.controllers.CustomerPaymentController;
import main.java.daos.OrderDAO;
import main.java.daos.PaymentDAO;
import main.java.utils.loggers.CustomerSession;

public class CustomerPaymentForm implements Form{
    private CustomerSession session;
    private CustomerPaymentController paymentController = new CustomerPaymentController(new OrderDAO(), new PaymentDAO());
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
                scanner.nextLine();
                handlePayment(new OnlinePaymentService(paymentController));
                break;
            case 2:
                scanner.nextLine();
                handlePayment(new CreditDebitPaymentService(paymentController));
                break;
            default:
                scanner.nextLine();
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
        boolean loop = true;
        while (loop)
        {
            System.out.println("----------------------------------------------------------------------\n" +
                            "|----------------------------Customer Payment------------------------|\n" +
                            "----------------------------------------------------------------------\n" +
                            "|                   Would you like a receipt?                        |\n" +
                            "|                   1.Yes                                            |\n" +
                            "|                   2.No                                             |\n" +
                            "----------------------------------------------------------------------\n" +
                                "\n");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
            if (choice == 1) {
                System.out.println(paymentController.printReceipt(session.getOrderId()));
            } else {
                System.out.println("Thank you for your payment!");
            }
            return;

        }
    }
}
