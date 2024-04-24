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

    public void generateForm() {
        boolean loop = true;
        while (loop) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("|-----------------------Customer Order Menu--------------------------|");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("|                   Choose an option:                                |");
            System.out.println("|                   1. View Menu                                     |");
            System.out.println("|                   2. Start New Order                               |");
            System.out.println("|                   3. Display Cart                                  |");
            System.out.println("|                   4. Submit Order                                  |");
            System.out.println("|                   5. Go to Homescreen                              |");
            System.out.println("----------------------------------------------------------------------");
            System.out.print("\nEnter your choice (1-5): ");
    
            int choice = -1;
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
                    startNewOrder();
                    break;
                case 3:
                    displayCart(this.orderId);
                    break;
                case 4:
                    if (!orderController.findOrder(this.orderId).getItems().isEmpty()) {
                        submitOrder(orderController.findOrder(this.orderId));
                    } else {
                        System.out.println("No items in the cart to submit. Please add items first.");
                    }
                    loop = false;
                    break;
                case 5:
                    System.out.println("Returning to Homescreen...");
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-5)");
                    break;
            }
        }
    }
    
    private void startNewOrder() {
        Order customerOrder = orderController.createCustomerOrder(orderId, branch);
        customerOrder.setOrderStatus(OrderStatus.NEW);
    
        addItemsToCart(customerOrder);
    
        if (customerOrder.getItems().isEmpty()) {
            System.out.println("Order is Empty! Returning to Homescreen...");
            return;
        }
    
        if (!chooseDineInOrTakeAway(customerOrder)) return; // handle dine-in or takeaway choice
        orderController.saveOrder(customerOrder);
    }

    private void addItemsToCart(Order customerOrder) {
        List<MenuItem> menuItems = menuController.getitems();
        showMenuItems(); 
        String itemName;
        while (true) {
            System.out.print("Enter the name of the item to order (type 'done' to finish): ");
            itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) break;
            
            MenuItem selectedItem = menuController.findMenuItemByName(itemName, branch, menuItems);
            if (selectedItem == null) {
                System.out.println("Invalid item. Please try again.");
                continue;
            }
            
            String comment = getComment();
            int quantity = getQty();
            customerOrder = orderController.addItem(customerOrder, selectedItem, quantity, comment);
        }
    }
    

    private boolean chooseDineInOrTakeAway(Order customerOrder) {
        System.out.println("1. Take Away\t2. Dine-in");
        int choice;
        while (true) {
            System.out.print("Enter your choice (1 for Take Away, 2 for Dine-in): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1 || choice == 2) {
                    customerOrder.setDineIn(choice == 2); // Assuming Order class has a setDineIn method
                    return true;
                } else {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    private void submitOrder(Order customerOrder) {
        System.out.println("Confirming Order Submission...");
        // Display the order summary
        displayCart(customerOrder.getOrderId());
        System.out.println("Do you want to submit this order? (yes/no):");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if ("yes".equals(confirmation)) {
            orderController.saveOrder(customerOrder);
            System.out.println("Order submitted successfully. Thank you for your order. Please proceed to make payment...");
        } else {
            System.out.println("Order submission cancelled. You can continue editing your cart.");
        }
    }

    private void displayCart(int orderId) {
        Order customerOrder = orderController.findOrder(orderId);
        if (customerOrder == null) {
            System.out.println("No order found with ID: " + orderId);
            return;
        }
    
        List<MenuItem> items = customerOrder.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is currently empty.");
            return;
        }
    
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("|-------------------------------------- YOUR CART -------------------------------------------|");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-10s | %-24s |%n", "Item Name", "Price ($)", "Special Requests");
        System.out.println("|---------------------------------------------------------------------------------------------|");
    
        double total = 0.0;
        List<String> comments = customerOrder.getComments(); 
        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            String comment = (comments.size() > i) ? comments.get(i) : ""; 
            System.out.printf("| %-20s | %-10.2f | %-24s |%n",
                    item.getName(), item.getPrice(), comment);
            total += item.getPrice(); 
        }
    
        System.out.println("|---------------------------------------------------------------------------------------------|");
        System.out.printf("| %-20s | %-10.2f | %-24s |%n", "Total", total, "");
        System.out.println("----------------------------------------------------------------------------------------------");
    }
        
    

    private void placeOrder1() {
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
        while (true) {
            try {
                System.out.println("You have selected the following items:");
                for (MenuItem item : customerOrder.getItems()) {
                    System.out.printf("%s - $%.2f%n", item.getName(), item.getPrice());
                }
                System.out.println("1. Submit Order");
                System.out.println("2. Remove Items");
                System.out.println("3. Cancel Order");
                method = Integer.parseInt(scanner.nextLine().trim());
                if (method == 1) {
                    orderController.saveOrder(customerOrder);
                    System.out.println("Thank you for your order. Please proceed to make payment...");
                    return;
                } else if (method == 2) {
                    boolean loop2 = true;
                    while (loop2){
                        System.out.println("Please enter the item you wish to remove");
                        String itemName = scanner.nextLine();
                        int quantity = getQty();
                        MenuItem selectedItem = menuController.findMenuItemByName(itemName, branch, selectedItems);
                        if (selectedItem == null || !selectedItem.getBranch().equals(branch)) { // Corrected check
                            System.out.println("Invalid item. Please try again.");
                            continue;
                        }
                        try {
                            customerOrder = orderController.deleteItem(customerOrder, selectedItem, quantity);
                            loop2=false;
                        } catch (Exception e) {
                            System.out.println("Please enter a valid quantity");
                        }
                    }
                    continue;
                } else if (method == 3) {
                    customerOrder = orderController.createCustomerOrder(orderId, branch);
                    System.out.println("Order cancelled. Returning to main menu.");
                    return;

                } else {
                    System.out.println("Invalid input. Please choose 1 to submit or 2 to cancel.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); 
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
