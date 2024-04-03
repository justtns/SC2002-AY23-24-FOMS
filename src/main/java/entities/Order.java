package main.java.entities;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;
// to identify time of order

public class Order{

    private int orderID;
    private List<MenuItem> items; 
    public String orderStatus;
    private float totalAmount;
    private Timestamp orderTime;
    @SuppressWarnings("unused")
    private char takeawayOption;
    private boolean paymentStatus;

    public Order(int orderID, List<MenuItem> items, Timestamp orderTime) {
        this.orderID = orderID;
        this.items = new ArrayList<>();
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = 'N';
        this.paymentStatus = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderID + "\nItems:\n");
        for (MenuItem item : items) {
            sb.append("Name: ").append(item.getName())
              .append(", ").append(item.getName())
              .append(" - $").append(String.format("%.2f", item.getPrice()))
              .append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", getAmount()));
        return sb.toString();
    }
    
    public int getOrderID() {
        return orderID;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public float getAmount() {
        float sum = 0;
        for (MenuItem item : items) {
            sum = sum+ item.getPrice();
        }
        this.totalAmount = sum;
        
        return totalAmount;
    }

    public Timestamp getTime() {
        return orderTime;
    }

    public String OrderStatus() {
        return orderStatus;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    public char setTakeawayOption(char takeawayOption) {
        return this.takeawayOption = takeawayOption;
    }

    public void setPaymentStatusTrue() {
        this.paymentStatus = true;
    }

}

