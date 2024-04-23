package main.java.controllers;

import main.java.models.Order;

import java.util.List;
import java.util.Scanner;

import main.java.daos.OrderDAO;
import main.java.models.Staff;
import main.java.utils.types.OrderStatus;
import main.java.daos.StaffDAO;

public class StaffOrderController {
    private StaffDAO staffDAO;
    private OrderDAO orderDAO;
    private Scanner scanner;

    public StaffOrderController(StaffDAO staffDAO, OrderDAO orderDAO, Scanner scanner) {
        this.staffDAO = staffDAO;
        this.orderDAO = orderDAO;
        this.scanner = scanner;
    }

    public void displayNewOrder(){ 
        // Ask for staff ID
        System.out.println("Enter your staff ID:");
        String staffId = scanner.nextLine();

        // Retrieve the branch code for this staff ID
        Staff user = staffDAO.findElement(staffId);
        String branchCode = user.getBranch();
        if (branchCode == null) {
            System.out.println("No branch found for this staff ID.");
            return;
        }

        // Retrieve and display new orders for this branch
        List<Order> orders = orderDAO.getElements();
        boolean found = false;
        for (Order order : orders) {
            if (order.getBranch().equals(branchCode) && order.getOrderStatus() == OrderStatus.NEW) {
                System.out.println(order);  // Assuming Order has a suitable toString() method
                found = true;
            }
        }
        if (!found) {
            System.out.println("No new orders found for branch: " + branchCode);
        }
    }


    public Order viewParticularOrder(int orderID){
        // Convert int orderID to String
        String orderIdString = String.valueOf(orderID);
        // Pass the converted string to the findElement method
        return orderDAO.findElement(orderIdString);
    }

    public void processOrder(int orderID){
        String orderIdString = String.valueOf(orderID);
        Order oldOrder = orderDAO.findElement(orderIdString);
        Order newOrder = orderDAO.findElement(orderIdString);
        newOrder.setOrderStatus(OrderStatus.READY);
        orderDAO.updateElement(oldOrder, newOrder);
        orderDAO.saveData();
    }
}