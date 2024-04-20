package main.java.boundaries;

import main.java.controllers.CustomerOrderController;
import main.java.daos.OrderDAO;

import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.types.OrderStatus;

public class CustomerPostOrderForm {
    private Scanner scanner = new Scanner(System.in);
    private OrderDAO orderDAO = new OrderDAO();
    private CustomerOrderController orderController = new CustomerOrderController(orderDAO);
    private String branch;

    public CustomerPostOrderForm(CustomerSession session){
        this.branch = session.getBranch();
    }

    public void postOrderView(){
        System.out.print("Thank you for ordering with us.");
        boolean loop=true;
            while (loop) {
                System.out.println("-------------------------------------------------------------------\n" +
                        "-----------------------------Post Order Menu---------------------------\n" +
                        "-------------------------------------------------------------------\n" +
                        "                         Choose an option:\n" +
                        "                         1.View Order Status\n" +
                        "                         2.Pickup Order\n" +
                        "                         3.Logout\n" +
                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Enter your choice (1-3): \n");
                int choice = -1;
                try {
                    choice = Integer.parseInt(scanner.next());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input...");
                    continue;
                }

                switch (choice) {
                    case 1:
                        viewOrderStatus();
                        break;
                    case 2:
                        pickupOrder();
                        break;
                    case 3:
                        System.out.println("Logging Out....");
                        break;
                    default:
                        System.out.println("Invalid Key! Enter your choice (1-3)");
                        break;
                }
            }
    }

    public void viewOrderStatus() {
        System.out.print("Welcome to our" + this.branch + "branch.");
        System.out.print("Please enter your order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        Order order = orderController.findOrder(orderId);
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
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Order order = orderController.findOrder(orderId);
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Total Price: $" + order.calculateTotalPrice());
            if(order.getOrderStatus()== OrderStatus.Ready) {
                System.out.println("1.Pickup\t2.Not yet");
                int c = scanner.nextInt();
                if (c == 1) {
                    order.setOrderStatus(OrderStatus.Completed);
                    order.setCompleted(true);
                    System.out.println("The order has been Completed..");
                    orderController.updateOrder(order);
                }
            }
            else
            {
                System.out.println("You cannot pickup,Order is not Ready Yet..");
            }
        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }

}
