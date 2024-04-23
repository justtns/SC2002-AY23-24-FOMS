package main.java.models;

import java.util.ArrayList;
import java.util.List;

import main.java.utils.types.OrderStatus; // Import the ENUMs of OrderStatus

/**
 * The Order class represents an order made in a restaurant.
 * It is a business object that contains all necessary accessors and mutators for CRUD Operations performed by OrderDAO.
 * Order class is in a 'has-a' relation with OrderDAO (Composition).
 * It contains information such as the order ID, items ordered, comments,
 * branch, dine-in status, completion status, and order status.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 23-Apr-2024
 */
public class Order {
    
    /** The unique ID of the order. */
    private int orderId;
    
    /** The list of items ordered in the order. */
    private List<MenuItem> items;
    
    /** The list of comments associated with the order. */
    private List<String> comments;
    
    /** The branch where the order was placed. */
    private String branch;
    
    /** The flag indicating whether the order is for dine-in or not. */
    private boolean isDineIn;
    
    /** The flag indicating whether the order is completed or not. */
    private boolean isCompleted;
    
    /** The status of the order. */
    private OrderStatus orderStatus;

    /**
     * Constructs an Order object with the given order ID and branch.
     * Initializes other fields with default values.
     * 
     * @param orderId The unique ID of the order
     * @param branch The branch where the order was placed
     */
    public Order(int orderId, String branch) {
        this.orderId = orderId;
        this.branch = branch;
        this.items = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.isDineIn = false;
        this.isCompleted = false;
    }

    /**
     * Constructs an Order object with the given parameters.
     * 
     * @param orderId The unique ID of the order
     * @param orderStatus The status of the order
     * @param isDineIn Indicates whether the order is for dine-in
     * @param isCompleted Indicates whether the order is completed
     */
    public Order(int orderId, OrderStatus orderStatus, boolean isDineIn, boolean isCompleted) {
        this.orderId = orderId;
        this.isDineIn = isDineIn;
        this.isCompleted = isCompleted;
        this.orderStatus = orderStatus;
        this.items = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    /**
     * Retrieves the branch where the order was placed.
     * 
     * @return The branch of the order
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the branch where the order was placed.
     * 
     * @param branch The new branch of the order
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Retrieves the unique ID of the order.
     * 
     * @return The order ID
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique ID of the order.
     * 
     * @param orderId The new order ID
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Retrieves the list of items ordered in the order.
     * 
     * @return The list of items
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * Sets the list of items ordered in the order.
     * 
     * @param items The new list of items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * Checks if the order is for dine-in.
     * 
     * @return True if the order is for dine-in, false otherwise
     */
    public boolean isDineIn() {
        return isDineIn;
    }

    /**
     * Sets whether the order is for dine-in or not.
     * 
     * @param dineIn True if the order is for dine-in, false otherwise
     */
    public void setDineIn(boolean dineIn) {
        isDineIn = dineIn;
    }

    /**
     * Checks if the order is completed.
     * 
     * @return True if the order is completed, false otherwise
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Sets whether the order is completed or not.
     * 
     * @param completed True if the order is completed, false otherwise
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Adds an item to the order.
     * 
     * @param item The item to add to the order
     */
    public void addItem(MenuItem item) {
        items.add(item);
    }

    /**
     * Adds a comment to the order.
     * 
     * @param comment The comment to add
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * Adds a default comment to the order.
     */
    public void addComment() {
        comments.add(" ");
    }

    /**
     * Retrieves the comment at the specified index.
     * 
     * @param index The index of the comment to retrieve
     * @return The comment at the specified index
     */
    public String getComment(int index) {
        return comments.get(index);
    }

    /**
     * Sets the status of the order.
     * 
     * @param orderStatus The new status of the order
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Retrieves the status of the order.
     * 
     * @return The status of the order
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Removes an item from the order.
     * 
     * @param item The item to remove from the order
     */
    public void removeItem(MenuItem item) {
        comments.remove(items.indexOf(item));
        items.remove(item);
    }

    /**
     * Finds an item in the order by its name.
     * 
     * @param itemName The name of the item to find
     * @return The found item, or null if not found
     */
    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null; 
    }

    /**
     * Finds all items in the order by their category.
     * 
     * @param category The category of items to find
     * @return The list of items found in the specified category
     */
    public List<MenuItem> findItemsByCategory(String category) {
        List<MenuItem> foundItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getCategory().equals(category)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    /**
     * Calculates the total price of the order.
     * 
     * @return The total price of the order
     */
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}