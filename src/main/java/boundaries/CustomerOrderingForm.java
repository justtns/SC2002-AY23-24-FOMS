package main.java.boundaries;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import main.java.models.MenuItem;
import main.java.daos.MenuDAO;
import main.java.models.Order;

public class CustomerOrderingForm {
    static Scanner scanner = new Scanner(System.in);

    public static void initaiteOrder(){
        System.out.println("Placing an Order");
    }

    private static void showMenuItems(String branch, List<MenuItem> menuItems) {
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
    public static void printInvalidItem(){
        System.out.println("Invalid item or not available in this branch. Please try again.");
    }

    public static String getComment(){
        System.out.print("Enter the item's special requests: ");
        String comments = scanner.nextLine();
        return comments;
    }

    public static int getQty(){
        System.out.print("Enter the number of items: ");
        int qty = scanner.nextInt();
        return qty;
    }

    public static String getOrderInput(String branch, List<MenuItem> menuItems) {
        showMenuItems(branch, menuItems);
        System.out.print("Enter the name of the item to order (type 'done' to finish): ");
        String itemName = scanner.nextLine();
        return itemName;
    }

    public static void getOrderConfirmation(Order custOrder){
        if (custOrder.getItems().isEmpty()) {
            System.out.println("No items selected. Order canceled.");
            return;
        }
        
        System.out.println("You have selected the following items:");
        for (MenuItem item : custOrder.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
    }

    public static boolean getOrderDineIn(){
        System.out.println("1.Take Away\t2.Dine-in");
        int method=scanner.nextInt();
        boolean m=method==2?true:false;
        return m;
    }
}
