package main.java.boundaries;

import java.util.Scanner;
import main.java.utils.ScannerProvider;
import java.util.InputMismatchException;
import java.util.List;

import main.java.models.MenuItem;
import main.java.controllers.CustomerMenuController;
import main.java.controllers.CustomerOrderController;
import main.java.daos.MenuDAO;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.types.OrderStatus;

public class CustomerOrderingForm {
    private OrderDAO orderDAO = new OrderDAO();
    private MenuDAO menuDAO = new MenuDAO();
    private CustomerOrderController orderController = new CustomerOrderController(orderDAO);
    private CustomerMenuController menuController = new CustomerMenuController(menuDAO);
    private String branch;
    private int orderId;
    private Order customerOrder;
    private Scanner scanner;

    public CustomerOrderingForm(CustomerSession session, Scanner scanner){
        this.branch = session.getBranch();
        this.orderId = session.getOrderId();
        this.scanner = scanner;
    }

    public void orderingView(){
        System.out.println("Thank you for ordering with us.");
        boolean loop=true;
        int choice;
            while (loop) {
                System.out.println("-------------------------------------------------------------------\n" +
                        "-----------------------------Order Menu---------------------------\n" +
                        "-------------------------------------------------------------------\n" +
                        "                         Choose an option:\n" +
                        "                         1.View Menu\n" +
                        "                         2.Place Order\n" +
                        "                         3.Go to Homescreen\n" +
                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Enter your choice (1-3): \n");
                choice = -1;
                try {
                    choice = Integer.parseInt(scanner.next());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input...");
                    continue;
                }

                switch (choice) {
                    case 1:
                        scanner.nextLine();
                        showMenuItems();
                        break;
                    case 2:
                        System.out.println("Placing an Order");
                        Order custOrder = orderController.createCustomerOrder(orderId);
                        custOrder.setOrderStatus(OrderStatus.New);
                        List<MenuItem> selectedItems = menuController.getitems();
                        //ordering method
                        while(true){
                            scanner.nextLine();
                            showMenuItems();
                            System.out.print("Enter the name of the item to order (type 'done' to finish): ");
                            String itemName = scanner.nextLine();
                            if (itemName.equalsIgnoreCase("done")) {
                                break;
                            }
                            MenuItem selectedItem = menuController.findMenuItemByName(itemName, branch, selectedItems);
                            if (selectedItem == null || !selectedItem.getBranch().equals(branch)) {
                                printInvalidItem();
                                continue;
                            }
                            String comment = getComment();
                            int quantity = getQty();
                            custOrder = orderController.addItem(custOrder, selectedItem, quantity, comment);
                            this.customerOrder = custOrder;
                        }
                        if (customerOrder.getItems().isEmpty()){
                            System.out.println("Order is Empty! Returning to Homescreen...");
                            loop = false;
                            break;
                        }
                        if (this.customerOrder.getItems().isEmpty()) {
                            System.out.println("No items selected. Order canceled.");
                            break;
                        }
                        System.out.println("1.Take Away\t2.Dine-in");
                        int method=scanner.nextInt();
                        boolean m=method==2?true:false;
                        this.customerOrder.setDineIn(m);

                        if (this.customerOrder.getItems().isEmpty()) {
                            System.out.println("No items selected. Order canceled.");
                            break;
                        }
                        
                        System.out.println("You have selected the following items:");
                        for (MenuItem item : this.customerOrder.getItems()) {
                            System.out.printf("%s - $%.2f%n", item.getName(), item.getPrice());
                        }
                        System.out.println("1. Submit Order");
                        System.out.println("2. Cancel Order");

                        while(true){
                            method=scanner.nextInt();
                            if (method == 1){
                                orderDAO.addElement(customerOrder);
                                System.out.println("Thank you for your order. Please proceed to make payment...");
                                orderDAO.saveData();
                                break;
                            }
                            else if (method == 2){
                                break;
                            }
                            else{
                                System.out.println("Invalid Input.");
                            }
                        }

                        loop = false;
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Returning to Homescreen...");
                        loop = false;
                        break;
                    default:
                        scanner.nextLine();
                        System.out.println("Invalid Key! Enter your choice (1-3)");
                        break;
                }
            }
        }

    private void showMenuItems() {
        List<MenuItem> menuItems = menuController.getitems();
        System.out.println("Menu Items in " + branch + ":");
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }
        // Print table header
        System.out.printf("%-20s | %-15s | %-10s | %-20s",
                "Name", "Category", "Price ($)", "Branch");
        System.out.println("------------------------------------------------------------------------------------------");
        // Print menu items
        for (MenuItem item : menuItems) {
            if (item.getBranch().equals(branch)) {
                System.out.printf("%-20s | %-15s | %-10.2f | %-20s %n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranch());
            }
        }
    }
    private void printInvalidItem(){
        System.out.println("Invalid item or not available in this branch. Please try again.");
    }

    private String getComment(){
        System.out.print("Enter the item's special requests: ");
        String comments = scanner.nextLine();
        if (comments == null){
            comments = " ";
        }
        return comments;
    }

    private int getQty(){
        System.out.print("Enter the number of items: ");
        int qty = scanner.nextInt();
        return qty;
    }
}
