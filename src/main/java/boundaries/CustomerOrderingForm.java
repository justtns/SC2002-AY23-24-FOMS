package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.java.controllers.CustomerMenuController;
import main.java.controllers.CustomerOrderController;
import main.java.daos.MenuDAO;
import main.java.daos.OrderDAO;
import main.java.models.MenuItem;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.types.OrderStatus;

public class CustomerOrderingForm implements Form{
    // Input validation is added for all user inputs using try-catch blocks and loops to ensure that only valid inputs are accepted.
    // Error messages are displayed for invalid inputs to guide the user.

    //User Inputs
    // Choice in the main ordering menu (1, 2, or 3).
    // Item name when placing an order.
    // Number of items when specifying the quantity.
    // Choice for take away or dine-in (1 or 2).
    // Choice for submitting or canceling the order (1 or 2).

    private CustomerOrderController orderController = new CustomerOrderController(new OrderDAO());
    private CustomerMenuController menuController = new CustomerMenuController(new MenuDAO());
    private String branch;
    private int orderId;
    private Scanner scanner;

    public CustomerOrderingForm(CustomerSession session, Scanner scanner){
        this.branch = session.getBranch();
        this.orderId = session.getOrderId();
        this.scanner = scanner;
    }

    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Customer Order Menu--------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.View Menu                                      |\n" +
                               "|                   2.Place Order                                    |\n" +
                               "|                   3.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-3):");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showMenuItems();
                    break;
                case 2:
                    placeOrder();
                    loop = false;
                    break;
                case 3:
                    System.out.println("Returning to Homescreen...");
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-3)");
                    break;
            }
        }
    }

    private void placeOrder() {
        System.out.println("Placing an Order");
        Order customerOrder = orderController.createCustomerOrder(orderId, branch);
        customerOrder.setOrderStatus(OrderStatus.NEW);
        List<MenuItem> selectedItems = menuController.getitems();
    
        while (true) {
            showMenuItems();
            System.out.print("Enter the name of the item to order (type 'done' to finish): ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }
            MenuItem selectedItem = menuController.findMenuItemByName(itemName, branch, selectedItems);
            if (selectedItem == null || !selectedItem.getBranch().equals(branch)) { // Corrected check
                System.out.println("Invalid item. Please try again.");
                continue;
            }
            String comment = getComment();
            int quantity = getQty();
            customerOrder = orderController.addItem(customerOrder, selectedItem, quantity, comment);
        }
    
        if (customerOrder.getItems().isEmpty()) {
            System.out.println("Order is Empty! Returning to Homescreen...");
            return;
        }
    
        System.out.println("1.Take Away\t2.Dine-in");
        int method = -1;
        while (method != 1 && method != 2) {
            System.out.print("Enter your choice (1 for Take Away, 2 for Dine-in): ");
            try {
                method = Integer.parseInt(scanner.nextLine().trim());
                if (method != 1 && method != 2) {
                    System.out.println("Invalid Input. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    
        boolean m = method == 2;
        customerOrder.setDineIn(m);
    
        System.out.println("You have selected the following items:");
        for (MenuItem item : customerOrder.getItems()) {
            System.out.printf("%s - $%.2f%n", item.getName(), item.getPrice());
        }
        System.out.println("1. Submit Order");
        System.out.println("2. Cancel Order");
    
        while (true) {
            try {
                method = Integer.parseInt(scanner.nextLine().trim());
                if (method == 1) {
                    orderController.saveOrder(customerOrder);
                    System.out.println("Thank you for your order. Please proceed to make payment...");
                } else if (method == 2) {
                    System.out.println("Order cancelled. Returning to main menu.");
                    return;
                } else {
                    System.out.println("Invalid input. Please choose 1 to submit or 2 to cancel.");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }
    

    private void showMenuItems() {
        List<MenuItem> menuItems = menuController.getitems();
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("|----------------------------------------MENU ITEMS------------------------------------------|");
        System.out.println("----------------------------------------------------------------------------------------------");
        if (menuItems.isEmpty()) {
            System.out.println("|--------------------------------No menu items available-------------------------------------|");
            return;
        }
        // Print table header

        System.out.printf("| %-20s | %-15s | %-10s | %-10s | %-24s|%n",
                "Name", "Category", "Price ($)", "Branch", "Description");
        System.out.println("|--------------------------------------------------------------------------------------------|");
        // Print menu items
        for (MenuItem item : menuItems) {
            if (item.getBranch().equals(branch)) {
                System.out.printf("| %-20s | %-15s | %-10.2f | %-10s | %-24s|%n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranch(), item.getDescription());
            }
        }
        System.out.println("|--------------------------------------------------------------------------------------------|");
    }

    private String getComment() {
        System.out.print("Enter the item's special requests: ");
        String comments = scanner.nextLine();
        if (comments == null){
            comments = " ";
        }
        return comments;
    }

    private int getQty() {
        int qty = -1;
        while (qty <= 0) {
            try {
                System.out.print("Enter the number of items: ");
                qty = Integer.parseInt(scanner.nextLine().trim());
                if (qty <= 0) {
                    System.out.println("Invalid Input. Please enter a positive number.");
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
        return qty;
    }
}
