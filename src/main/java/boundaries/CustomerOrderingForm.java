package main.java.boundaries;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import main.java.models.MenuItem;
import main.java.controllers.CustomerMenuController;
import main.java.controllers.CustomerOrderController;
import main.java.daos.MenuDAO;
import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;

public class CustomerOrderingForm {
    private Scanner scanner = new Scanner(System.in);
    private OrderDAO orderDAO = new OrderDAO();
    private MenuDAO menuDAO = new MenuDAO();
    private CustomerOrderController orderController = new CustomerOrderController(orderDAO);
    private CustomerMenuController menuController = new CustomerMenuController(menuDAO);
    private String branch;
    private int orderId;
    private Order customerOrder;

    public CustomerOrderingForm(CustomerSession session){
        this.branch = session.getBranch();
        this.orderId = session.getOrderId();
    }

    public void placeOrder(){
        System.out.println("Placing an Order");
        Order custOrder = orderController.createCustomerOrder(orderId);
        List<MenuItem> selectedItems = menuController.getitems();
        //ordering method
        while(true){
            String menuItem = getOrderInput(branch, selectedItems);
            if (menuItem.equalsIgnoreCase("done")) {
                break;
            }
            MenuItem selectedItem = menuController.findMenuItemByName(menuItem, branch, selectedItems);
            if (selectedItem == null || !selectedItem.getBranch().equals(branch)) {
                printInvalidItem();
                continue;
            }
            String comment = getComment();
            int quantity = getQty();
            custOrder = orderController.addItem(custOrder, selectedItem, quantity, comment);
            this.customerOrder = custOrder;
        }
    }

    private void showMenuItems(String branch, List<MenuItem> menuItems) {
        System.out.println("Menu Items in " + branch + ":");
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }
        // Print table header
        System.out.printf("%-20s | %-15s | %-10s | %-20s | %-10s | %-50s%n",
                "Name", "Category", "Price ($)", "Branch", "Available", "Description");
        System.out.println("------------------------------------------------------------------------------------------");
        // Print menu items
        for (MenuItem item : menuItems) {
            if (item.getBranch().equals(branch)) {
                System.out.printf("%-20s | %-15s | %-10.2f | %-20s | %-10s | %-50s%n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranch(),
                        item.isAvailable() ? "Yes" : "No");
            }
        }
    }
    private void printInvalidItem(){
        System.out.println("Invalid item or not available in this branch. Please try again.");
    }

    private String getComment(){
        System.out.print("Enter the item's special requests: ");
        String comments = scanner.nextLine();
        return comments;
    }

    private int getQty(){
        System.out.print("Enter the number of items: ");
        int qty = scanner.nextInt();
        return qty;
    }

    private String getOrderInput(String branch, List<MenuItem> menuItems) {
        showMenuItems(branch, menuItems);
        System.out.print("Enter the name of the item to order (type 'done' to finish): ");
        String itemName = scanner.nextLine();
        return itemName;
    }

    public void getOrderConfirmation(){
        if (this.customerOrder.getItems().isEmpty()) {
            System.out.println("No items selected. Order canceled.");
            return;
        }
        
        System.out.println("You have selected the following items:");
        for (MenuItem item : this.customerOrder.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.println("1. Submit Order");
        System.out.println("2. Cancel Order");
        String method;
        while(true){
            method=scanner.nextLine();
            if (method.equalsIgnoreCase("1")){
                orderDAO.addElement(customerOrder);
                orderDAO.saveData();
                return;
            }
            else if (method.equalsIgnoreCase("2")){
                return;
            }
            else{
                System.out.println("Invalid Input.");
                System.out.println("1. Submit Order");
                System.out.println("2. Cancel Order");
            }
        }
    }

    public void getOrderDineIn(){
        System.out.println("1.Take Away\t2.Dine-in");
        int method=scanner.nextInt();
        boolean m=method==2?true:false;
        this.customerOrder.setDineIn(m);
    }
}
