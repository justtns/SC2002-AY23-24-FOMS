package main.java.boundaries;

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

/**
 * A form for customers as a boundary level object to order items and modify their order cart.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class CustomerOrderingForm implements Form {

    /**
     * The controller for handling customer orders.
     */
    private CustomerOrderController orderController = new CustomerOrderController(new OrderDAO());

    /**
     * The controller for handling customer menu operations.
     */
    private CustomerMenuController menuController = new CustomerMenuController(new MenuDAO());

    /**
     * The branch where the order is placed.
     */
    private String branch;

    /**
     * The ID of the order.
     */
    private int orderId;

    /**
     * The Scanner object for user input.
     */
    private Scanner scanner;

    /**
     * Constructs a CustomerOrderingForm with the specified customer session and scanner.
     * 
     * @param session The customer session.
     * @param scanner The Scanner object for user input.
     */
    public CustomerOrderingForm(CustomerSession session, Scanner scanner){
        this.branch = session.getBranch();
        this.orderId = session.getOrderId();
        this.scanner = scanner;
    }

    /**
     * Generates the ordering form and handles customer's input for orders.
     * It includes options such as view menu (1), starting new order (2), display cart (3),
     * edit cart (4), submit order (5) and return to homescreen.
     * Checks if customer input is within available options 1-6.
     */
    @Override
    public void generateForm() {
        boolean loop = true;
        Order customerOrder = orderController.findOrder(orderId);
        if (customerOrder == null){
            customerOrder = orderController.createCustomerOrder(orderId, branch);
            customerOrder.setOrderStatus(OrderStatus.NEW);
        }
        if (customerOrder.getOrderStatus() == OrderStatus.PAID){
            System.out.println("Your order has been confirmed.");
            System.out.println("Please proceed to pick it up when ready, or begin a new session if you wish to order more. Thank you!");
            return;
        };
        while (loop) {
            System.out.println("----------------------------------------------------------------------");
            System.out.println("|-----------------------Customer Order Menu--------------------------|");
            System.out.println("----------------------------------------------------------------------");
            System.out.println("|                   Choose an option:                                |");
            System.out.println("|                   1. View Menu                                     |");
            System.out.println("|                   2. Start New Order                               |");
            System.out.println("|                   3. Display Cart                                  |");
            System.out.println("|                   4. Edit Cart                                     |");
            System.out.println("|                   5. Submit Order                                  |");
            System.out.println("|                   6. Go to Homescreen                              |");
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
                    customerOrder = startNewOrder(customerOrder);
                    break;
                case 3:
                    displayCart(customerOrder);
                    break;
                case 4:
                    customerOrder = editCart(customerOrder);
                    break;
                case 5:
                    if (!customerOrder.getItems().isEmpty()) {
                        submitOrder(customerOrder);
                    } else {
                        System.out.println("No items in the cart to submit. Please add items first.");
                    }
                    loop = false;
                    break;
                case 6:
                    System.out.println("Returning to Homescreen...");
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-5)");
                    break;
            }
        }
    }

    /**
     * Method to edit the items in the customer's cart, asking customers if they would like to add 
     * or remove cart items or if they are done.
     * Checks if customer input is within available options 1-3.
     * 
     * @param customOrder The customer's order.
     * @return The updated customer's order after editing the cart.
     */
    private Order editCart(Order customOrder) {
        List<MenuItem> items = customOrder.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is currently empty. Please add items first.");
            return customOrder;
        }
        
        boolean loop4 = true;
        while (loop4) {
            displayCart(customOrder);
            System.out.println("1. Add Items\t2. Remove Items\t3. Change Dine-In Option\t4. Done\t");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                continue;
            }
            switch (choice) {
                case 1:
                    customOrder = addItemsToCart(customOrder);
                    break;
                case 2:
                    customOrder = removeItem(customOrder);
                    break;
                case 3:
                    customOrder = changeDineInOption(customOrder);
                    break;
                case 4:
                    return customOrder;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-2)");
                    break;
            }
        }
        return customOrder;
    }

    /**
     * Method that helps customers change the dinein status.
     * Check if input 
     * 
     * @param customerOrder The customer's order.
     * @return The updated customer's order after updating the dinein status.
     */
    private Order changeDineInOption(Order customerOrder) {
        System.out.println("Current Dine-in Option: " + (customerOrder.isDineIn() ? "Dine-in" : "Takeaway"));
        if (customerOrder.isDineIn()){
            System.out.println("Would you like to change to Takeaway? (yes/no)");
        }
        else {
            System.out.println("Would you like to change to Dine-in? (yes/no)");
        }
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("yes")){
            customerOrder.setDineIn(!customerOrder.isDineIn());
        }

        else if (choice.equals("no")){
            System.out.println("Dine-in option remains the same.");
        }
        else {
            System.out.println("Invalid input. Please try again.");
        }
        return customerOrder;
    }

    /**
     * Method that removes an item from the customer's cart.
     * Checks for valid input of either take away (1) or dine-in (2).
     * 
     * @param customerOrder The customer's order.
     * @return The updated customer's order after removing the item.
     */
    private Order removeItem(Order customerOrder) {
        List<MenuItem> menuItems = menuController.getitems();
        boolean loop2 = true;
        if (customerOrder.getItems().size() == 0){
            System.out.println("Cart is empty!");
            return customerOrder;
        }

        while (loop2){
            displayCart(customerOrder);
            System.out.println("Please enter the item you wish to remove (type 'done' to finish)");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")){
                loop2 = false;
                continue;
            }
            MenuItem selectedItem = menuController.findMenuItemByName(itemName, branch, menuItems);
            if (selectedItem == null || !selectedItem.getBranch().equals(branch)) { // Corrected check
                System.out.println("Invalid item. Please try again.");
                continue;
            }
            int quantity = getQty();
            if (quantity == customerOrder.getItems().size()){
                System.out.println("Last item removed, cart is now empty.");
                customerOrder = orderController.createCustomerOrder(orderId, branch);
                customerOrder.setOrderStatus(OrderStatus.NEW);
                continue;
            }

            try {
                customerOrder = orderController.deleteItem(customerOrder, selectedItem, quantity);
                continue;
            } catch (Exception e) {
                System.out.println("Please enter a valid quantity");
                continue;
            }
        }
        return customerOrder;
    }
    
    /**
     * Method to starts a new order for the customer.
     * Checks if order list is empty.
     * 
     * @param customerOrder The customer's order.
     * @return The updated customer's order after starting a new order.
     */
    private Order startNewOrder(Order customerOrder) {
        customerOrder = addItemsToCart(customerOrder);
        if (customerOrder.getItems().isEmpty()) {
            System.out.println("Order is Empty! Returning to Homescreen...");
            return customerOrder;
        }
        customerOrder = chooseDineInOrTakeAway(customerOrder);
        return customerOrder;
    }

    /**
     * Method to add items to the customer's cart.
     * Checks if Item to order is not available or invalid.
     * 
     * @param customerOrder The customer's order.
     * @return The updated customer's order after adding items to the cart.
     */
    private Order addItemsToCart(Order customerOrder) {
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
        return customerOrder;
    }
    
    /**
     * Method to choose whether the order is for dine-in or take-away.
     * Checks for valid input of either take away (1) or dine-in (2).
     * 
     * @param customerOrder The customer's order.
     * @return The updated customer's order with the chosen option.
     */
    private Order chooseDineInOrTakeAway(Order customerOrder) {
        System.out.println("1. Take Away\t2. Dine-in");
        int choice;
        while (true) {
            System.out.print("Enter your choice (1 for Take Away, 2 for Dine-in): ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice == 1 || choice == 2) {
                    customerOrder.setDineIn(choice == 2); // Assuming Order class has a setDineIn method
                    return customerOrder;
                } else {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Method to submit or cancel the customer's order, once they are done.
     * 
     * @param customerOrder The customer's order.
     */
    private void submitOrder(Order customerOrder) {
        System.out.println("Confirming Order Submission...");
        displayCart(customerOrder);
        System.out.println("Do you want to submit this order? (yes/no):");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if ("yes".equals(confirmation)) {
            if (orderController.findOrder(customerOrder.getOrderId())== null){
                orderController.saveOrder(customerOrder);
            }
            else {
                orderController.updateOrder(customerOrder);
            }
            System.out.println("Order submitted successfully. Thank you for your order. Please proceed to make payment...");
        } else {
            System.out.println("Order submission cancelled. You can continue editing your cart.");
        }
    }

    /**
     * Method to displays the customer's cart.
     * Checks if no order is found or if cart is empty.
     * 
     * @param customerOrder The customer's order.
     */
    private void displayCart(Order customerOrder) {
        if (customerOrder == null) {
            System.out.println("No order found with ID: " + orderId);
            return;
        }
    
        List<MenuItem> items = customerOrder.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is currently empty. Please add items first.");
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
        System.out.println("|---------------------------------------------------------------------------------------------|");
        System.out.println("| Dine-in Option Selected: " + (customerOrder.isDineIn() ? "Dine-in" : "Takeaway"));
        System.out.println("----------------------------------------------------------------------------------------------");
    }
    
    /**
     * Method to display the menu items available for ordering.
     * Prints a message if no menu items are available.
     * 
     */
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

        for (MenuItem item : menuItems) {
            if (item.getBranch().equals(branch)) {
                System.out.printf("| %-20s | %-15s | %-10.2f | %-10s | %-24s|%n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranch(), item.getDescription());
            }
        }
        System.out.println("|--------------------------------------------------------------------------------------------|");
    }

    /**
     * Method to add special requests for an item in the order as a comment.
     * 
     * @return The special requests provided by the user.
     */
    private String getComment() {
        System.out.print("Enter the item's special requests: ");
        String comments = scanner.nextLine();
        if (comments == null){
            comments = " ";
        }
        return comments;
    }

    /**
     * Method to retrieve the quantity of items.
     * Checks if quantity inputted is less than 0, which is invalid.
     * 
     * @return The quantity of items provided by the user.
     */
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
            catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
            }
        }
        return qty;
    }
}