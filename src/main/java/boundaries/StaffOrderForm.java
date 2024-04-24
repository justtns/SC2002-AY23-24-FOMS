package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffOrderController;
import main.java.daos.OrderDAO;
import main.java.daos.StaffDAO;
import main.java.utils.loggers.StaffSession;

public class StaffOrderForm implements Form{

    private Scanner scanner;
    private StaffSession session;
    private StaffOrderController orderController = new StaffOrderController(new StaffDAO(), new OrderDAO());
    

    public StaffOrderForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
    }

    @Override
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            int orderID;

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    displayOrders();
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    viewOrder();
                    break;
                case 3:
                    scanner.nextLine(); // Consume the newline character
                    updateOrder();
                    break;
                case 4:
                    scanner.nextLine(); // Consume the newline character
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    private void displayOrders() {
        String staffId = session.getStaffUserID();
        orderController.displayNewOrder(staffId);
    }

    private void viewOrder(){
        int orderID = -1;
        System.out.println("Enter Order ID:");
        while (orderID == -1) {
            try {
                orderID = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
        }
        if(!orderController.viewParticularOrder(orderID)){
            System.out.println("Order not found with ID: " + orderID);
        }
    }

    private void updateOrder(){
        int orderID = -1;
        System.out.println("Enter Order ID:");
        while (orderID == -1) {
            try {
                orderID = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
        }

        if(!orderController.processOrder(orderID)){
            System.out.println("Order not found with ID: " + orderID);
        }
    }
    

}
