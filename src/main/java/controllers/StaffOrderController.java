package main.java.controllers;

import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.models.Staff;
import main.java.daos.StaffDAO;
import main.java.utils.types.OrderStatus;

import java.util.List;
import java.util.Scanner;

/**
 * The StaffOrderController class manages orders placed by staff members.
 * It provides methods to display new orders for a staff member's branch, view a particular order, and process an order.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 *
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffOrderController {
    /** The data access object (DAO) for staff */
    private StaffDAO staffDAO;
    /** The data access object (DAO) for orders */
    private OrderDAO orderDAO;
    /** The scanner object to read user input */
    private Scanner scanner;

    /**
     * Constructs a new StaffOrderController with the specified StaffDAO, OrderDAO, and Scanner.
     *
     * @param staffDAO  the StaffDAO instance
     * @param orderDAO  the OrderDAO instance
     * @param scanner   the Scanner instance for user input
     */
    public StaffOrderController(StaffDAO staffDAO, OrderDAO orderDAO, Scanner scanner) {
        this.staffDAO = staffDAO;
        this.orderDAO = orderDAO;
        this.scanner = scanner;
    }

    /**
     * Displays new orders for the staff member's branch.
     * Prompts the user to enter their staff ID, retrieves their branch code,
     * and displays new orders for that branch.
     */
    public void displayNewOrder() {
        System.out.println("Enter your staff ID:");
        String staffId = scanner.nextLine();
        Staff user = staffDAO.findElement(staffId);
        String branchCode = user.getBranch();
        if (branchCode == null) {
            System.out.println("No branch found for this staff ID.");
            return;
        }
        List<Order> orders = orderDAO.getElements();
        boolean found = false;
        for (Order order : orders) {
            if (order.getBranch().equals(branchCode) && order.getOrderStatus() == OrderStatus.NEW) {
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
     * @return the Order object corresponding to the provided order ID
     */
    public Order viewParticularOrder(int orderID) {
        String orderIdString = String.valueOf(orderID);
        return orderDAO.findElement(orderIdString);
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
        newOrder.setOrderStatus(OrderStatus.READY);
        orderDAO.updateElement(oldOrder, newOrder);
        orderDAO.saveData();
    }
}