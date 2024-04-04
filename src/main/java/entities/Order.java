package main.java.entities;

import java.util.ArrayList;
import java.util.List;

import java.sql.Timestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderID;
    private List<OrderQuantities> itemsQuantities;
    public String orderStatus;
    private float totalAmount;
    private long orderTime;
    private char takeawayOption;
    private boolean paymentStatus;

    public Order(int orderID, List<OrderQuantities> itemsQuantities, float orderValue, long orderTime, char takeawayOption, boolean paymentStatus) {
        this.orderID = orderID;
        this.itemsQuantities = itemsQuantities != null ? new ArrayList<>(itemsQuantities) : new ArrayList<>();
        this.totalAmount = orderValue;
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = takeawayOption;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderID + "\nItems:\n");
        for (OrderQuantities itemQuantity : itemsQuantities) {
            MenuItem item = itemQuantity.getItem();
            sb.append("Name: ").append(item.getName())
              .append(", Quantity: ").append(itemQuantity.getQuantity())
              .append(" - $").append(String.format("%.2f", item.getPrice()))
              .append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", getAmount()));
        return sb.toString();
    }
    
    public int getOrderID() {
        return orderID;
    }

    public List<OrderQuantities> getItems() {
        return itemsQuantities;
    }

    public float getAmount() {
        float sum = 0;
        for (OrderQuantities mq : itemsQuantities) {
            sum += mq.getItem().getPrice() * mq.getQuantity();
        }
        return sum;
    }

    public long getTime() {
        return orderTime;
    }

    public String OrderStatus() {
        return orderStatus;
    }

    public void addItem(MenuItem item, int quantity) {
        for (OrderQuantities mq : itemsQuantities) {
            if (mq.getItem().equals(item)) {
                mq.setQuantity(mq.getQuantity() + quantity);
                return;
            }
        }
        itemsQuantities.add(new OrderQuantities(item, quantity));
    }

    public void removeItem(MenuItem item, int quantity) {
        for (OrderQuantities mq : itemsQuantities) {
            if (mq.getItem().equals(item)) {
                if (mq.getQuantity() > quantity) {
                    mq.setQuantity(mq.getQuantity() - quantity);
                } else {
                    itemsQuantities.remove(mq);
                }
                return;
            }
        }
    }

    public char setTakeawayOption(char takeawayOption) {
        return this.takeawayOption = takeawayOption;
    }

    public void setPaymentStatusTrue() {
        this.paymentStatus = true;
    }

}

