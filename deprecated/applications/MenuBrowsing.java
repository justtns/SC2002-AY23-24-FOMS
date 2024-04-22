package main.java.deprecated.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import main.java.deprecated.handlers.MenuHandler;
import main.java.deprecated.handlers.OrderHandler;
import main.java.models.MenuItem;
import main.java.models.Order;

import java.io.IOException;

// TO BE MERGED INTO CUSTOMER APP
public class MenuBrowsing {

    private static MenuHandler menuHandler = new MenuHandler();
    private static OrderHandler orderHandler = OrderHandler.getInstance(menuHandler); // singleton instance! 
    
    private static Scanner scanner = new Scanner(System.in); 
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // fr string input

     // create a static variable so orderID is unique
    private static int orderID = 0;
    private static String branch = null;

    public static void main(String[] args) {
        
        
        System.out.println("Welcome to the SC2002 Customer Ordering System!");

        System.out.println("Enter the branch:");
        branch = scanner.nextLine();
        
        System.out.println("You have selected branch: " + branch);
        
        createOrder();

        boolean exit = false;
        while (!exit){
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Display Menu");
        //    System.out.println("2. Create New Order");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Delete Item from Cart");
            System.out.println("5. Update Item in Cart");
            System.out.println("6. View Cart");
            System.out.println("7. Takeaway Option");
            System.out.println("8. Checkout");
            System.out.println("9. Exit");

            try {
                System.out.println("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        displayMenu();
                        break;
             //       case 2:
             //           createOrder();
             //           break;
                    case 3:
                        addItem();
                        break;
                    case 4:
                        deleteItem();
                        break;
                    case 5:
                        updateItem();
                        break;
                    case 6:
                        viewCart();
                        break;
                    case 7:
                        takeawayOption();
                        break;
    //                case ?:
    //                  checkout();
        //                break;
                    case 9:
                        exit = true;
                        System.out.println("Thank you for using the Customer Ordering System!");
                        break;
                    default:
                        System.out.println("Invalid option. Please input an integer from 1-9.");
                }

            } catch (Exception e) {
                System.out.println("Invalid Input. Please input an integer from 1-9.");
                scanner.next();
            }
        }
        scanner.close();
        
        }
    

    public static void displayMenu() {
        System.out.println("Current Menu:");
        menuHandler.listElement();
    }

    public static void createOrder() {
        
        // increment orderID so that each order has a unique ID - starting from 1 
        long timestamp = System.currentTimeMillis(); // curr time in milliseconds
        Order newOrder = new Order(++orderID, null, 0, timestamp, 'N', false);
        orderHandler.addElement(newOrder);
        
        System.out.println("Order created successfully! Order ID: " + newOrder.getOrderID());
        System.out.println("Your cart is currently empty. Please add items to your order.");
        System.out.println();
    }

    public static void addItem() {
        viewCart();

        System.out.println("Enter Item Name to add to your order: ");
        try {
            String itemName = reader.readLine();

            System.out.println("Enter Quantity: ");
            int quantity = scanner.nextInt();

            System.out.println("Enter Customisations: ");
            String customisations = reader.readLine();

            orderHandler.addElement(orderID, itemName, quantity, customisations, branch);

        } catch (IOException e) {
            System.out.println("Invalid Input. Please try again.");
        }
    }


    public static void deleteItem(){
        
        viewCart();
        System.out.println("Enter Item to delete from your order: ");
        try {
            String itemName = reader.readLine();

            orderHandler.removeElementbyAttributes(orderID, itemName, branch);
        }
        catch (IOException e) {
            System.out.println("Invalid Input. Please try again.");
        }
        
    }

    public static void updateItem() {
        viewCart();
        System.out.println("Enter Item you want to modify: ");
        String itemName = scanner.next();
        System.out.println("Enter the new quantity ");
        int quantity = scanner.nextInt();

        orderHandler.updateOrderItemQty(orderID, itemName, quantity, branch);
    }

    public static void viewCart() {
        Order orderToView = orderHandler.findElementById(orderID); 
        System.out.println(orderToView);
    }

    public static void takeawayOption() {
        System.out.println("Would you like to takeaway your order? (Y/N)");
        char takeawayOption = scanner.next().charAt(0);
        orderHandler.updateTakeawayOption(orderID, takeawayOption);

        System.out.println("You have selected takeaway option: " + takeawayOption);
    }

}
