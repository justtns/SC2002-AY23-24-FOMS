package main.java.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderID;
    private List<OrderItem> orderItems;
    public String orderStatus;
    private float totalAmount;
    private long orderTime;
    private char takeawayOption;
    private boolean paymentStatus;

    public Order(int orderID, List<OrderItem> orderItems, float totalAmount, long orderTime, char takeawayOption, boolean paymentStatus) {
        this.orderID = orderID;
        this.orderItems = orderItems != null ? new ArrayList<>(orderItems) : new ArrayList<>();
        this.totalAmount = totalAmount;
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = takeawayOption;
        this.paymentStatus = paymentStatus;
    }

    //Overload
    public Order(int orderID, List<OrderItem> orderItems, long orderTime, char takeawayOption, boolean paymentStatus) {
        this.orderID = orderID;
        this.orderItems = orderItems != null ? new ArrayList<>(orderItems) : new ArrayList<>();
        this.totalAmount = 0;
        for (OrderItem orderItem : orderItems) {
            this.totalAmount += orderItem.getItem().getPrice() * orderItem.getQuantity();
        }
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = takeawayOption;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderID + "\nItems:\n");
        for (OrderItem item : orderItems) {
            sb.append("Name: ").append(item.getItem().getName())
              .append(", Quantity: ").append(item.getQuantity())
              .append(", Customisations: ").append(item.getCustomisations())
              .append(" - $").append(String.format("%.2f", item.getItem().getPrice()))
              .append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", getAmount()));
        return sb.toString();
    }
    
    public int getOrderID() {
        return orderID;
    }

    public List<OrderItem> getItems() {
        return orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public char getTakeaway() {
        return takeawayOption;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public float getAmount() {
        return totalAmount;
    }

    public long getTime() {
        return orderTime;
    }

    public void addItem(MenuItem item, int quantity, String customisations) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().equals(item)) {
                orderItem.setQuantity(orderItem.getQuantity() + quantity);
                orderItem.setCustomisations(customisations);
                return;
            }
        }
        orderItems.add(new OrderItem(item, quantity, customisations));
    }

    public void removeItem(MenuItem item, int quantity) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().equals(item)) {
                if (orderItem.getQuantity() > quantity) {
                    orderItem.setQuantity(orderItem.getQuantity() - quantity);
                } else {
                    orderItems.remove(orderItem);
                }
                return;
            }
        }
    }

    public void removeItem(MenuItem item) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getItem().equals(item)) {
                orderItems.remove(orderItem);
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

