package test.java.handlers;

import main.java.handlers.MenuHandler;
import main.java.entities.MenuItem;

public class MenuTester {

    public static void main(String[] args) {
        MenuHandler menuHandler = new MenuHandler();

        System.out.println("Adding a new menu item...");
        MenuItem newItem = new MenuItem("Test Item", "Test Category", "Test Branch", 9.99f);
        try {
            menuHandler.addElement(newItem);
            System.out.println("New item added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nListing all menu items...");
        menuHandler.listElement();

        System.out.println("\nUpdating an existing menu item...");
        MenuItem updatedItem = new MenuItem("Test Item Updated", "Test Category", "Test Branch", 10.99f);
        try {
            menuHandler.updateElement(newItem, updatedItem);
            System.out.println("Item updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nRemoving an existing menu item...");
        try {
            menuHandler.removeElement(updatedItem);
            System.out.println("Item removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nListing all menu items to verify removal...");
        menuHandler.listElement();
    }
}
