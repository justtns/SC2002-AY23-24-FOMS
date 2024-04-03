package main.java.applications;

import java.util.List;
import java.util.Scanner;

import main.java.entities.MenuItem;
import main.java.entities.Order;
import main.java.handlers.MenuHandler;
import main.java.handlers.OrderHandler;

// TO BE MERGED INTO CUSTOMER APP
public class MenuBrowsing {

    private static MenuHandler menuHandler = new MenuHandler();
    private static OrderHandler orderHandler = OrderHandler.getInstance(menuHandler); // singleton instance! 
    
    private static Scanner scanner = new Scanner(System.in); 

     // create a static variable so orderID is unique
    private static int orderID = 0;
    private static String branch = null;


    public static void main(String[] args) {
        
        
        System.out.println("Welcome to the SC2002 Customer Ordering System!");

        // branch selection (temporary)
        
        System.out.println("Please select a branch: ");
        System.out.println("1. Branch 1");
        System.out.println("2. Branch 2");
        System.out.println("3. Branch 3");

        String branch = scanner.next();
        System.out.println("You have selected branch " + branch);

        boolean exit = false;
        while (!exit) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Display Menu");
            System.out.println("2. Create New Order");
            System.out.println("3. Add Item to Order");
            System.out.println("4. Delete Item from Cart");
            System.out.println("5. Update Item in Cart");
            System.out.println("6. View Cart");
            System.out.println("7. Takeaway Option");
            System.out.println("8. Checkout");
            System.out.println("9. Exit");


            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayMenu();
                    break;
                case 2:
                    createOrder();
                    break;
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
                    System.out.println("Invalid option. Please try again.");
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

        Order newOrder = new Order(++orderID, null, null);
        orderHandler.addElement(newOrder);
        
        System.out.println("Order created successfully! Order ID: " + newOrder.getOrderID());
        System.out.println("Your cart is currently empty. Please add items to your order.");
        System.out.println();
    }

    public static void addItem() {
        viewCart();
        displayMenu();
        System.out.println("Enter Item ID to add to your order: ");
        String itemId = scanner.next();
        
        orderHandler.addElement(orderID, itemId, branch);
    }
    

    public static void deleteItem() {
        
        viewCart();
        System.out.println("Enter Item ID to delete from your order: ");
        String itemId = scanner.next();

        orderHandler.removeElement(orderID, itemId, branch);
    }

    public static void updateItem() {
        viewCart();
        System.out.println("Enter Item ID to update in your order: ");
        String itemId = scanner.next();
        System.out.println("Enter new Item ID: ");
        String newItemId = scanner.next();

        orderHandler.updateElement(orderID, itemId, newItemId, branch);
    }

    public static void viewCart() {
        Order orderToView = orderHandler.findElementById(orderID); 
        System.out.println(orderToView);
    }

    public static void takeawayOption() {
        System.out.println("Would you like to takeaway your order? (Y/N)");
        char takeawayOption = scanner.next().charAt(0);
        orderHandler.updateElement(orderID, takeawayOption);

        System.out.println("You have selected takeaway option: " + takeawayOption);
    }

}
