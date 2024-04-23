package main.java.controllers;

import main.java.boundaries.PaymentService;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.ScannerProvider;
import main.java.utils.types.OrderStatus;

public class CustomerPaymentController {
    private OrderDAO orderDAO;

    public CustomerPaymentController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public boolean makePayment(int orderId, PaymentService paymentService) {
        if (paymentService.simulatePayment(ScannerProvider.getScanner())) {
            Order order = orderDAO.findElement(Integer.toString(orderId));
            if (order != null) {
                order.setOrderStatus(OrderStatus.PAID);
                orderDAO.saveData();
                return true;
            } else {
                System.err.println("Order with ID " + orderId + " not found for payment.");
            }
        }
        return false;
    }

    public double getTotal(int orderId) {
        Order order = orderDAO.findElement(Integer.toString(orderId));
        if (order != null) {
            return order.calculateTotalPrice(); 
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
            return 0.0; 
        }
    }

    public String printReceipt(int orderId) {
        Order order = orderDAO.findElement(Integer.toString(orderId));
        if (order != null) {
            return "Receipt for Order ID: " + orderId + "\n" + order.toString(); 
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
            return "Receipt could not be generated for Order ID: " + orderId;
        }
    }

}
