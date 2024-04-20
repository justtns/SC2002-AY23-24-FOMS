package main.java.controllers;
import main.java.boundaries.PaymentService;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.types.OrderStatus;


public class CustomerPaymentController {
    private int orderId;
    private PaymentService paymentService; 
    private OrderDAO orderDAO;

    public CustomerPaymentController(int orderId, PaymentService paymentService, OrderDAO orderDAO) {
        this.orderId = orderId;
        this.paymentService = paymentService;
        this.orderDAO = orderDAO;
    }

    // overloaded method to authenticate payment - online
    public boolean authenticatePayment(String domain, String email, String password) {

        System.out.println("Payment authenticated for domain: " + domain);
        return true; 
    }

    // Ooerloaded method to authenticate payment - credit/debit
    public boolean authenticatePayment(String bank, int number, String expiry, int cvc) {

        System.out.println("Payment authenticated for bank: " + bank);
        return true; 
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
            return "Receipt for Order ID: " + orderId + "\n" + order.toString();
        } else {
            System.err.println("Order with ID " + orderId + " not found.");
        }
        return "Receipt for Order ID: " + orderId;
    }

}

