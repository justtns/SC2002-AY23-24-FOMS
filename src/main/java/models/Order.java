package main.java.models;

import java.util.ArrayList;
import java.util.List;

import main.java.utils.types.OrderStatus;

public class Order {
    private int orderId;
    private List<MenuItem> items;
    private List<String> comments;
    private boolean isDineIn;

    private boolean isCompleted;
    public static int count=111;
    private OrderStatus orderStatus;


    public Order(boolean isDineIn) {

        this.orderId =count++;
        this.items=new ArrayList<>();
        this.comments = new ArrayList<>();
        this.isDineIn = isDineIn;
        this.isCompleted = false;

    }

    public Order(int orderId, OrderStatus orderStatus, boolean isDineIn, boolean isCompleted) {
        this.orderId = orderId;
        this.isDineIn = isDineIn;
        this.isCompleted = isCompleted;
        this.orderStatus = orderStatus;
        this.items=new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Order(List<MenuItem> items, List<String> comments, boolean isDineIn) {
        this.orderId =count++;
        this.items = items;
        this.comments = new ArrayList<>();
        this.isDineIn = isDineIn;
        this.isCompleted = false;
        this.orderStatus=OrderStatus.New;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public boolean isDineIn() {
        return isDineIn;
    }

    public void setDineIn(boolean dineIn) {
        isDineIn = dineIn;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public void addItem(MenuItem item) {
        items.add(item);
    }
    public void addComment(String comment) {
        comments.add(comment);
    }
    public void addComment() {
        comments.add(" ");
    }
    public String getComment(int index) {
        return comments.get(index);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null; 
    }

    public List<MenuItem> findItemsByCategory(String category) {
        List<MenuItem> foundItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getCategory().equals(category)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
