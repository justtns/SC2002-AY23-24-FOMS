package main.java.controllers;

import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.models.Staff;
import main.java.models.MenuItem;
import main.java.daos.StaffDAO;
import main.java.utils.types.OrderStatus;

import java.util.List;

/**
 * The StaffOrderController class manages orders placed by staff members.
 * It provides methods to display new orders for a staff member's branch, view a particular order, and process an order.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 *
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class StaffOrderController {
    /** The data access object (DAO) for staff */
    private StaffDAO staffDAO;
    /** The data access object (DAO) for orders */
    private OrderDAO orderDAO;

    /**
     * Constructs a new StaffOrderController with the specified StaffDAO and OrderDAO.
     *
     * @param staffDAO  the StaffDAO instance
     * @param orderDAO  the OrderDAO instance
     */
    public StaffOrderController(StaffDAO staffDAO, OrderDAO orderDAO) {
        this.staffDAO = staffDAO;
        this.orderDAO = orderDAO;
    }

    /**
     * Displays new orders for the staff member's branch.
     * Prompts the user to enter their staff ID, retrieves their branch code,
     * and displays new orders for that branch.
     *
     * @param staffId the ID of the staff member
     */
    public void displayNewOrder(String staffId) {
        Staff user = staffDAO.findElement(staffId);
        String branchCode = user.getBranch();
        if (branchCode == null) {
            System.out.println("No branch found for this staff ID.");
            return;
        }
        List<Order> orders = orderDAO.getElements();
        boolean found = false;
        for (Order order : orders) {
            if (order.getBranch().equals(branchCode) && order.getOrderStatus() == OrderStatus.PAID) {
                System.out.println(order.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No new orders found for branch: " + branchCode);
        }
    }

    /**
     * Views a particular order based on the provided order ID.
     *
     * @param orderID the ID of the order to view
     */
    public void viewParticularOrder(int orderID) {
        String orderIdString = String.valueOf(orderID);
        Order order = orderDAO.findElement(orderIdString);
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            if(order.isDineIn()){
                System.out.println("Dine in");
            }
            else{
                System.out.println("Takeaway");
            }
            System.out.println("Order items:");
            for (MenuItem item : order.getItems()) {
                System.out.printf("%s - $%.2f%n", item.getName(), item.getPrice());
            }
            System.out.printf("Total Price: $%.2f%n", order.calculateTotalPrice());
            System.out.println("Order Status: " + order.getOrderStatus());
            return;
        } else {
            System.out.println("Order not found with ID: " + orderID);
            return;
        }
    }

    /**
     * Processes an order by changing its status to READY.
     *
     * @param orderID the ID of the order to process
     */
    public void processOrder(int orderID) {
        String orderIdString = String.valueOf(orderID);
        Order oldOrder = orderDAO.findElement(orderIdString);
        Order newOrder = orderDAO.findElement(orderIdString);
        if (newOrder != null) {
            newOrder.setOrderStatus(OrderStatus.READY);
            orderDAO.updateElement(oldOrder, newOrder);
            orderDAO.saveData();
            System.out.println("Order Status for Order ID: " + newOrder.getOrderId() + " has been changed to " + newOrder.getOrderStatus());
            return;
        }
        else{
            System.out.println("Order not found with ID: " + orderID);
            return;
        }
    }
}