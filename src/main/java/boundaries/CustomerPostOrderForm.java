package main.java.boundaries;

import main.java.controllers.CustomerOrderController;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.types.OrderStatus;

import java.util.Scanner;

/**
 * A form for customers as a boundary level object for post-order activities such as viewing order status and picking up orders.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerPostOrderForm implements Form {
    
    /**
     * Scanner object for user input
     */
    private Scanner scanner;

    /**
     * Data Access Object for orders
     */
    private OrderDAO orderDAO = new OrderDAO(); 

    /**
     * Controller for managing orders
     */
    private CustomerOrderController orderController = new CustomerOrderController(orderDAO); 

    /**
     * The branch associated with current customer session
     */
    private String branch;

    /**
     * Constructs a CustomerPostOrderForm object with the specified session and scanner.
     * 
     * @param session The customer session.
     * @param scanner The scanner object for user input.
     */
    public CustomerPostOrderForm(CustomerSession session, Scanner scanner) {
        this.branch = session.getBranch();
        this.scanner = scanner;
    }

    /**
     * Generates the post-order form menu and handles customer input for post order activities
     * It includes options of viewing order status (1), picking up order (2) or logging out (3).
     * Checks if customer input is valid within options 1-3. 
     */
    @Override
    public void generateForm() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Thank you for ordering with us.");
        boolean loop = true;
        while (loop) {

            System.out.println("----------------------------------------------------------------------\n" +
                               "|----------------------------Post Order Menu-------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1. View Order Status                             |\n" +
                               "|                   2. Pickup Order                                  |\n" +
                               "|                   3. Logout                                        |\n" +
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

                    // time delay
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                default:
                    scanner.nextLine();
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    /**
     * Method to display the order status based on the provided order ID.
     * Checks if order id can be found.
     */
    public void viewOrderStatus() {
        System.out.println("View orders in the " + this.branch + " branch.");
        System.out.print("Please enter your order ID: ");
        int orderId;
        try {
            orderId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input...");
            return;
        }
        Order order = orderController.findOrder(orderId);
        StringBuilder receipt = new StringBuilder();
        if (order != null) {
            receipt.append("============================================\n");
            receipt.append(String.format("Order ID: %d\n", order.getOrderId()));
            receipt.append("============================================\n");
            
            receipt.append("Order Details:\n");
            receipt.append(String.format("Order Status: %s\n", order.getOrderStatus().toString()));
            receipt.append(String.format("Total Price: $%.2f\n", order.calculateTotalPrice()));
            receipt.append(String.format("Dine-in: %s\n", order.isDineIn() ? "Yes" : "No"));
            
            receipt.append("--------------------------------------------\n");
            order.getItems().forEach(item -> {
                receipt.append(String.format("%s - $%.2f\n", item.getName(), item.getPrice()));
            });
            receipt.append("============================================\n");
            receipt.toString();
            System.out.println(receipt);

            System.out.println("Press enter to return to the Post Order Menu...");
            scanner.nextLine();

            System.out.print("\033[H\033[2J");
            System.out.flush();

        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }

    /**
     * Method to handle and simulate the process of a customer picking up an order.
     * Checks if order is ready for pickup or if order id is not found.
     */
    public void pickupOrder() {
        System.out.print("Enter the order ID: ");
        int orderId;
        try {
            orderId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input...");
            return;
        }
        Order order = orderController.findOrder(orderId);
        StringBuilder receipt = new StringBuilder();
        if (order != null) {
            receipt.append("============================================\n");
            receipt.append(String.format("Order ID: %d\n", order.getOrderId()));
            receipt.append("============================================\n");
            
            receipt.append("Order Details:\n");
            receipt.append(String.format("Order Status: %s\n", order.getOrderStatus().toString()));
            receipt.append(String.format("Total Price: $%.2f\n", order.calculateTotalPrice()));
            receipt.append(String.format("Dine-in: %s\n", order.isDineIn() ? "Yes" : "No"));
            
            receipt.append("--------------------------------------------\n");
            order.getItems().forEach(item -> {
                receipt.append(String.format("%s - $%.2f\n", item.getName(), item.getPrice()));
            });
            receipt.append("============================================\n");
            receipt.toString();
            System.out.println(receipt);
            if (order.getOrderStatus() == OrderStatus.READY) {
                System.out.println("1. Pickup\t2. Not yet");
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
                    System.out.println("The order has been Completed.");
                    orderController.updateOrder(order);
                }
            } else {
                System.out.println("You cannot pickup, Order is not Ready Yet. \n");

                System.out.println("Press enter to return to the Main Menu...");
                scanner.nextLine();

                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }
}