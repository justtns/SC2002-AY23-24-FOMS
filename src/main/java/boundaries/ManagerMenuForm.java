package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.controllers.StaffMenuController;
import main.java.daos.MenuDAO;
import main.java.models.MenuItem;
import main.java.utils.loggers.StaffSession;

public class ManagerMenuForm {

    private StaffMenuController menuController;
    private Scanner scanner;

    public ManagerMenuForm(StaffSession session, Scanner scanner){
        this.menuController = new StaffMenuController(new MenuDAO()); 
        this.scanner = scanner;
    }

    public void ManagerMenuView(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Manager Menu Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Add Menu Items\n" +
                    "                         2.Edit Menu Items\n" +
                    "                         3.Remove Menu Items\n" +
                    "                         4.Change Menu Item Availability\n" +
                    "                         5.Logout\n" +    
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-5): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // add MenuItems
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Adding a new menu item.");
                    
                    System.out.println("Enter the name of the new menu item:");
                    String name = scanner.nextLine();
                    System.out.println("Enter the category of the new menu item:");
                    String category = scanner.nextLine();
                    System.out.println("Enter the price of the new menu item:");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline character after a number input
                    System.out.println("Enter the branch of the new menu item:");
                    String branch = scanner.nextLine();
                    // Boolean availability = true;
                    MenuItem newItem = new MenuItem(name, category, branch, price);

                    menuController.addMenuItem(newItem);
                    break;
                
                case 2:
                    // Edit Menu Items
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Editing an existing menu item.");
                    
                    System.out.println("Enter the name of the menu item to edit:");
                    String oldName = scanner.nextLine();
                    MenuItem oldItem = MenuDAO.findElement(oldName, branch);
                    if (oldItem == null) {
                        System.out.println("Menu item not found.");
                    } else {
                        System.out.println("Enter the new description for the menu item:");
                        String newDescription = scanner.nextLine();
                        System.out.println("Enter the new price for the menu item:");
                        double newPrice = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                        MenuItem updatedItem = new MenuItem(oldItem.getName(), newDescription, oldItem.getBranch, newPrice );
                        menuController.editMenuItem(oldItem, updatedItem);
                    }
                    break; 

                case 3:
                    // Remove Menu Items
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Removing a menu item.");
                    System.out.println("Enter the name of the menu item to remove:");

                    String itemName = scanner.nextLine();
                    MenuItem itemToRemove = MenuDAO.findElement(oldName, branch);
                    if (itemToRemove == null) {
                        System.out.println("Menu item not found.");
                    } else {
                        menuController.removeMenuItem(itemToRemove);
                    }
                    break; 

                case 4:
                    // Change Menu Item availability
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Changing item availability.");
                    
                    System.out.println("Enter the name of the menu item to change availability:");
                    String itemNameToChangeAvailability = scanner.nextLine();
                    MenuItem itemToChange = MenuDAO.findElement(oldName, branch);
                    if (itemToChange == null) {
                        System.out.println("Menu item not found.");
                    } else {
                        System.out.println("Enter new availability (true for available, false for not available):");
                        Boolean newAvailability = scanner.nextBoolean();
                        scanner.nextLine(); // Consume newline
                        menuController.changeAvailability(itemToChange, newAvailability);
                    }
                    break; 

                case 5:
                    loop=false;
                    System.out.println("Logging out...");
                    break;

                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-4)");
                    break;
            }
        }
    }

}
