package main.java.boundaries;

import main.java.controllers.CustomerOrderController;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.types.OrderStatus;

import java.util.Scanner;

public class CustomerPostOrderForm implements Form{
    // Choice in the post-order menu (1, 2, or 3).
    // Choice in the picking up order
    // Order ID when viewing order status or picking up an order.

    private Scanner scanner;
    private OrderDAO orderDAO = new OrderDAO();
    private CustomerOrderController orderController = new CustomerOrderController(orderDAO);
    private String branch;

    public CustomerPostOrderForm(CustomerSession session, Scanner scanner){
        this.branch = session.getBranch();
        this.scanner = scanner;
    }

    @Override
    public void generateForm(){
        System.out.println("Thank you for ordering with us.");
        boolean loop=true;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|----------------------------Post Order Menu-------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.View Order Status                              |\n" +
                               "|                   2.Pickup Order                                   |\n" +
                               "|                   3.Logout                                         |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-3): ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input...");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    viewOrderStatus();
                    break;
                case 2:
                    scanner.nextLine();
                    pickupOrder();
                    break;
                case 3:
                    scanner.nextLine();
                    loop = false;
                    System.out.println("Logging Out....");
                    break;
                default:
                    scanner.nextLine();
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    public void viewOrderStatus() {
        System.out.println("Welcome to our " + this.branch + " branch.");
        System.out.print("Please enter your order ID: ");
        int orderId;
        try {
            orderId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input...");
            return;
        }
        Order order = orderController.findOrder((orderId));
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Total Price: $" + order.calculateTotalPrice());
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }

    public void pickupOrder() {
        System.out.print("Enter the order ID: ");
        int orderId;
        try {
            orderId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input...");
            return;
        }
        Order order = orderController.findOrder((orderId));
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Total Price: $" + order.calculateTotalPrice());
            if (order.getOrderStatus() == OrderStatus.READY) {
                System.out.println("1.Pickup\t2.Not yet");
                int c;
                try {
                    c = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input...");
                    return;
                }
                if (c == 1) {
                    order.setOrderStatus(OrderStatus.COMPLETED);
                    order.setCompleted(true);
                    System.out.println("The order has been Completed..");
                    orderController.updateOrder(order);
                }
            } else {
                System.out.println("You cannot pickup, Order is not Ready Yet..");
            }
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }
}
