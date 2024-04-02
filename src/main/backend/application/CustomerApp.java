package main.backend.application;

import main.backend.handlers.MenuHandler;
import main.backend.handlers.OrderHandler;
import main.backend.handlers.PaymentHandler;
import main.backend.users.Customer;

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
