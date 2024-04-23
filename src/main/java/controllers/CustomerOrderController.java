package main.java.controllers;

import main.java.models.Order;
import main.java.models.MenuItem;
import main.java.daos.OrderDAO;

/**
 * The CustomerOrderController class implements the business logic layer operations related to customer orders.
 * It provides methods to create, modify, and manage customer orders.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class CustomerOrderController {
    
    /** The data access object (DAO) for orders. */
    private OrderDAO orderDAO;

    /**
     * Constructs a CustomerOrderController object with the specified OrderDAO.
     * 
     * @param orderDAO The OrderDAO object to be used by the controller
     */
    public CustomerOrderController(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    /**
     * Creates a new customer order with the given order ID and branch.
     * 
     * @param orderID The ID of the order
     * @param branch The branch where the order is placed
     * @return The created Order object
     */
    public Order createCustomerOrder(int orderID, String branch){
        Order customerOrder = new Order(orderID, branch);
        return customerOrder;
    }

    /**
     * Sets the dine-in status for the customer order.
     * 
     * @param customerOrder The customer order to update
     * @param choice The dine-in status choice (true for dine-in, false for takeout)
     * @return The updated Order object
     */
    public Order chooseDineIn(Order customerOrder, boolean choice){
        customerOrder.setDineIn(choice);
        return customerOrder;
    }

    /**
     * Adds items to the customer order with specified quantity and comments.
     * 
     * @param customerOrder The customer order to update
     * @param cartItem The item to add to the order
     * @param quantity The quantity of the item to add
     * @param comments Any additional comments for the item
     * @return The updated Order object
     */
    public Order addItem(Order customerOrder, MenuItem cartItem, int quantity, String comments){
        for (int i = 0; i < quantity; i++){
            customerOrder.addItem(cartItem);
            customerOrder.addComment(comments);
        }
        return customerOrder;
    }

    /**
     * Deletes items from the customer order with specified quantity.
     * 
     * @param customerOrder The customer order to update
     * @param cartItem The item to remove from the order
     * @param quantity The quantity of the item to remove
     * @return The updated Order object
     */
    public Order deleteItem(Order customerOrder, MenuItem cartItem, int quantity){
        for(int i = 0; i < quantity; i++){
            customerOrder.removeItem(cartItem);
        }
        return customerOrder;
    }

    /**
     * Finds the customer order with the specified order ID.
     * 
     * @param orderID The ID of the order to find
     * @return The found Order object, or null if not found
     */
    public Order findOrder(int orderID){
        return orderDAO.findElement(Integer.toString(orderID));
    }

    /**
     * Saves the customer order to the data store.
     * 
     * @param customerOrder The customer order to save
     */
    public void saveOrder(Order customerOrder){
        orderDAO.addElement(customerOrder);
        orderDAO.saveData();
        orderDAO.readData();
    }

    /**
     * Updates the customer order in the data store.
     * 
     * @param customerOrder The customer order to update
     */
    public void updateOrder(Order customerOrder){
        orderDAO.updateElement(orderDAO.findElement(Integer.toString(customerOrder.getOrderId())), customerOrder);
    }

    /**
     * Calculates and retrieves the total price of the customer order.
     * 
     * @param customerOrder The customer order
     * @return The total price of the order
     */
    public double getTotal(Order customerOrder){
        return customerOrder.calculateTotalPrice();
    }
}