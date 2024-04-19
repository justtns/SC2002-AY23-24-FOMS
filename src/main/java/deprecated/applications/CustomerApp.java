package main.java.deprecated.applications;

import main.java.deprecated.handlers.MenuHandler;
import main.java.deprecated.handlers.OrderHandler;
import main.java.deprecated.handlers.PaymentHandler;
import main.java.users.Customer;

public class CustomerApp {
    private Customer customer;
    private OrderHandler orderHandler;
    private MenuHandler menuHandler;
    private PaymentHandler paymentHandler;

    public CustomerApp(Customer customer, OrderHandler orderHandler, MenuHandler menuHandler, PaymentHandler paymentHandler) {
        this.customer = customer;
        this.orderHandler = orderHandler;
        this.menuHandler = menuHandler;
        this.paymentHandler = paymentHandler;
    }

    public void displayMenu() {
        this.menuHandler.listElement();
    }

    public void createOrder() {

    }

    public void addItem() {
        this.orderHandler.listElement();
    }

    public void removeItem() {
        this.orderHandler.listElement();
    }

    public void updateItem() {
        this.orderHandler.listElement();
    }

    public void listItems() {
        this.orderHandler.listElement();
    }

    public void initiatePayment() {

    }
}
