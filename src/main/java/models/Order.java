package main.java.models;

import java.util.ArrayList;
import java.util.List;

import main.java.utils.types.OrderStatus;

public class Order {
    private int orderId;
    private List<MenuItem> items;
    private List<String> comments;
    private String branch;
    private boolean isDineIn;

    private boolean isCompleted;
    private OrderStatus orderStatus;

    public Order(int orderId, String branch) {

        this.orderId = orderId;
        this.branch = branch;
        this.items=new ArrayList<>();
        this.comments = new ArrayList<>();
        this.isDineIn = false;
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

    public String getBranch(){
        return branch;
    }

    public void setBranch(String branch){
        this.branch = branch;
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
        if (comment == null){
            comments.add(" ");
        }
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
        comments.remove(items.indexOf(item));
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

    @Override
    public String toString() {
        return "Order{" +
        "orderId=" + orderId +
        ", items=" + itemsToString() +  // Helper method to handle list of items
        ", comments=" + comments +
        ", branch='" + branch + '\'' +
        ", isDineIn=" + isDineIn +
        ", isCompleted=" + isCompleted +
        ", orderStatus=" + orderStatus +
        '}';
    }

    private String itemsToString() {
        StringBuilder itemsStr = new StringBuilder("[");
        for (int i = 0; i < items.size(); i++) {
            itemsStr.append(items.get(i).toString());
            if (i < items.size() - 1) {
                itemsStr.append(", ");  // Add comma except after the last item
            }
        }
        itemsStr.append("]");
        return itemsStr.toString();
    }
}
