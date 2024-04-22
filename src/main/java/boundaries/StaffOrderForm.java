package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.java.controllers.StaffOrderController;
import main.java.daos.OrderDAO;
import main.java.models.MenuItem;
import main.java.models.Order;
import main.java.utils.loggers.StaffSession;
import main.java.utils.types.OrderStatus;
import main.java.utils.types.StaffRole;

public class StaffOrderForm {

    private OrderDAO orderDAO = new OrderDAO();
    private StaffOrderController orderController = new StaffOrderController(orderDAO);
    private String staffUserID;
    private StaffRole staffRole;
    private Scanner scanner;

    public StaffOrderForm(StaffSession session, Scanner scanner){
        this.staffUserID = session.getStaffUserID();
        this.staffRole = session.getStaffRole();
        this.scanner = scanner;
    }

    public void staffOrderView(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Staff Order Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Display the new orders\n" +
                    "                         2.View the details of a particular order\n" +
                    "                         3.Process order\n" +
                    "                         4.Logout\n" +                            
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-4): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    displayOrders();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    int orderID;
                    try {
                        orderID = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input. Please enter a number.");
                        scanner.nextLine(); // Consume the invalid input
                        continue;
                    }
                    viewOrder(orderID);
                    break;
                case 3:
                    scanner.nextLine(); // Consume the newline character
                    try {
                        orderID = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input. Please enter a number.");
                        scanner.nextLine(); // Consume the invalid input
                        continue;
                    }
                    updateOrder(orderID);
                    break;
                case 4:
                    loop=false;
                    System.out.println("Logging out...");
                    break;
                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    private void displayOrders() {
        orderController.displayNewOrder();
    }

    private void viewOrder(int orderID){
        Order order = orderController.viewParticularOrder(orderID);
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
            System.out.println("Total Price: $" + order.calculateTotalPrice());
            System.out.println("Order Status: " + order.getOrderStatus());
        } else {
            System.out.println("Order not found with ID: " + orderID);
        }
    }

    private void updateOrder(int orderID){
        orderController.processOrder(orderID);
    }
    

}
