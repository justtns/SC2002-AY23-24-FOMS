package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffOrderController;
import main.java.daos.OrderDAO;
import main.java.daos.StaffDAO;
import main.java.utils.loggers.StaffSession;

/**
 * The StaffOrderForm class is a boundary level object that represents the form for staff and managers to perform order actions. 
 * This class provides methods to display new orders, view order details, and process orders.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffOrderForm implements Form{

    /**
     * The scanner object for input.
     */
    private Scanner scanner;

    /**
     * The staff session associated with the order form.
     */
    private StaffSession session;

    /**
     * The controller for staff order actions.
     */
    private StaffOrderController orderController;

    /**
     * Initializes a new instance of the StaffOrderForm class.
     * 
     * @param session The staff session associated with the order form.
     * @param scanner The scanner object for input.
     */
    public StaffOrderForm(StaffSession session, Scanner scanner){
        this.session = session;
        this.scanner = scanner;
        this.orderController = new StaffOrderController(new StaffDAO(), new OrderDAO());
    }

    /**
     * Generates the form for staff and manager order actions.
     * Checks if user input is within options 1-4.
     */
    @Override
    public void generateForm(){

        System.out.print("\033[H\033[2J");
        System.out.flush();
        
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

                    //time delay
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

    /**
     * Method to display the new orders.
     */
    private void displayOrders() {
        String staffId = session.getStaffUserID();
        orderController.displayNewOrder(staffId);

        System.out.println("Press enter to return to the Staff Order Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Method to views the details of a particular order, by asking staff/managers for OrderID.
     * Checks if input is valid and whether order exists.
     */
    private void viewOrder(){
        int orderID = -1;
        System.out.println("Enter Order ID:");
        while (orderID == -1) {
            try {
                orderID = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid order ID.");
                continue;
            }
        }
        orderController.viewParticularOrder(orderID);
        
        System.out.println("Press enter to return to the Staff Order Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Method to process the order, by asking staff/managers for OrderID.
     * Checks if input is valid and whether order exists.
     */
    private void updateOrder(){
        int orderID = -1;
        System.out.println("Enter Order ID:");
        while (orderID == -1) {
            try {
                orderID = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a valid order ID.");
                continue;
            }
        }
        orderController.processOrder(orderID);

        System.out.println("Press enter to return to the Staff Order Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}