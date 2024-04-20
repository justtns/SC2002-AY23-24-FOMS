package main.java.controllers;
import main.java.boundaries.PaymentService;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.types.OrderStatus;


public class CustomerPaymentController {
    private int orderId;
    private PaymentService paymentService; 
    private OrderDAO orderDAO;

    public CustomerPaymentController(int orderId) {
        this.orderId = orderId;
    }

    public boolean makePayment() {
        if (paymentService.simulatePayment()) {
            Order order = orderDAO.findOrderById(orderId);
            if (order != null) {
                order.setOrderStatus(OrderStatus.Paid);
                orderDAO.saveData();
                return true;
            } else {
                System.err.println("Order with ID " + orderId + " not found for payment.");
            }
        }
        return false;
    }

    public double getTotal() {
        Order order = orderDAO.findOrderById(orderId);
        if (order != null) {
            return order.calculateTotalPrice();
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
            return 0.0; 
        }
    }

    public String printReceipt() {
        Order order = orderDAO.findOrderById(orderId);
        if (order != null) {
            return "Receipt for Order ID: " + orderId + "\n" + order.toString(); // need to create an order tostring method
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
        }
        return "Receipt for Order ID: " + orderId;
    }

}

