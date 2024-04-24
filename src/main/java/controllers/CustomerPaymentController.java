package main.java.controllers;

import java.util.List;
import main.java.boundaries.PaymentService;
import main.java.daos.OrderDAO;
import main.java.daos.PaymentDAO;
import main.java.models.Order;
import main.java.models.PaymentMethod;
import main.java.utils.ScannerProvider;
import main.java.utils.types.OrderStatus;

/**
 * The CustomerPaymentController class implements the business logic layer operations related to payments for customers.
 * It provides methods to make payments, calculate total amounts, and print receipts for orders.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.3
 * @since 24-Apr-2024
 */
public class CustomerPaymentController {

    /** The data access object (DAO) for orders */
    private OrderDAO orderDAO;

    /** The data access object (DAO) for approved payment methods. */
    private PaymentDAO paymentDAO;

    /**
     * Constructs a CustomerPaymentController object with the specified OrderDAO.
     * 
     * @param orderDAO The OrderDAO object to be used by the controller
     * @param paymentDAO The PaymentDAO object to be used by the controller
     */
    public CustomerPaymentController(OrderDAO orderDAO, PaymentDAO paymentDAO) {
        this.orderDAO = orderDAO;
        this.paymentDAO = paymentDAO;
    }

    /**
     * Makes a payment for the order with the given ID using the provided payment service.
     * 
     * @param orderId The ID of the order to be paid
     * @param paymentService The payment service to be used for the transaction
     * @return True if the payment was successful, false otherwise
     */
    public boolean makePayment(int orderId, PaymentService paymentService) {
        if (paymentService.simulatePayment(ScannerProvider.getScanner())) {
            Order order = orderDAO.findElement(Integer.toString(orderId));
            if (order != null) {
                order.setOrderStatus(OrderStatus.PAID);
                orderDAO.updateElement(order, order);
                orderDAO.saveData();
                return true;
            } else {
                System.err.println("Order with ID " + orderId + " not found for payment.");
            }
        }
        return false;
    }

    /**
     * Calculates the total amount to be paid for the order with the given ID.
     * 
     * @param orderId The ID of the order
     * @return The total amount to be paid for the order
     */
    public double getTotal(int orderId) {
        Order order = orderDAO.findElement(Integer.toString(orderId));
        if (order != null) {
            return order.calculateTotalPrice(); 
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
            return 0.0; 
        }
    }

    /**
     * Prints a receipt for the order with the given ID.
     * 
     * @param orderId The ID of the order
     * @return A string containing the receipt details
     */
    public String printReceipt(int orderId) {
        Order order = orderDAO.findElement(Integer.toString(orderId));
        if (order != null) {
            StringBuilder receipt = new StringBuilder();
            receipt.append("============================================\n");
            receipt.append(String.format("Receipt for Order ID: %d\n", orderId));
            receipt.append("============================================\n");
            
            receipt.append("Order Details:\n");
            receipt.append(String.format("Order ID: %d\n", order.getOrderId()));
            receipt.append(String.format("Order Status: %s\n", order.getOrderStatus().toString()));
            receipt.append(String.format("Total Price: $%.2f\n", order.calculateTotalPrice()));
            receipt.append(String.format("Dine-in: %s\n", order.isDineIn() ? "Yes" : "No"));
            
            receipt.append("--------------------------------------------\n");
            order.getItems().forEach(item -> {
                receipt.append(String.format("%s - $%.2f\n", item.getName(), item.getPrice()));
            });
            
            receipt.append("--------------------------------------------\n");
            receipt.append("Thank you for your purchase!\n");
            receipt.append("============================================\n");
            return receipt.toString();
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
            return "Receipt could not be generated for Order ID: " + orderId;
        }
    }
    
    
    /**
     * Validates if the payment is approved.
     * 
     * @param name The name of the payment method
     * @return True if the payment exists
     */
    public boolean validatePayment(String name){
        if (paymentDAO.findElement(name) == null){
            return false;
        }
        return true;
    }

    /**
     * Retrieves a list of valid payment types.
     * 
     * @return A list of PaymentMethod objects representing valid payment types
     */
    public List<PaymentMethod> getValidPaymentTypes(){
        return paymentDAO.getElements();
    }
}