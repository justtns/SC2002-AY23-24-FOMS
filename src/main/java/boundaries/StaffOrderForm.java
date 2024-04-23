package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffOrderController;
import main.java.daos.OrderDAO;
import main.java.daos.StaffDAO;
import main.java.models.MenuItem;
import main.java.models.Order;

public class StaffOrderForm implements Form{

    private OrderDAO orderDAO = new OrderDAO();
    private StaffDAO staffDAO = new StaffDAO();
    // private ScannerProvider scanner;
    private Scanner scanner; // help with scanner please
    private StaffOrderController orderController = new StaffOrderController(staffDAO, orderDAO, scanner);
    

    public StaffOrderForm(Scanner scanner){
        this.scanner = scanner;
    }

    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Staff Order Actions--------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Display the new orders                         |\n" +
                               "|                   2.View the details of a particular order         |\n" +
                               "|                   3.Process order                                  |\n" +
                               "|                   4.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
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
                    System.out.println("Returning to Homescreen...");
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
