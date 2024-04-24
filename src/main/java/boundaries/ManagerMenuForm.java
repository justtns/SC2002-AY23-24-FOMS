package main.java.boundaries;

import java.util.Scanner;

import main.java.controllers.StaffMenuController;
import main.java.daos.MenuDAO;
import java.util.InputMismatchException;

/**
 * The ManagerMenuForm class is a form that is a boundary level object for managers to perform menu modification actions
 * such as adding, editing, or removing menu items, changing item availability, and editing menu item descriptions.
 * This form is implemented from the Form interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class ManagerMenuForm implements Form {

    /**
     * The controller for managing menu actions.
     */
    private StaffMenuController menuController;

    /**
     * The scanner object used for user input.
     */
    private Scanner scanner;

    /**
     * Constructs a ManagerMenuForm with the given scanner.
     * 
     * @param scanner The scanner object used for user input.
     */
    public ManagerMenuForm(Scanner scanner){
        this.menuController = new StaffMenuController(new MenuDAO()); 
        this.scanner = scanner;
    }

    /**
     * Generates the manager menu form.
     * 
     * This method presents the manager with options to add (1), edit (2), or remove menu items (3),
     * change item availability (4), edit menu item descriptions (5), or return to the homescreen (6).
     * Checks if manager input is valid within options 1-6.
     */
    @Override
    public void generateForm(){
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Manager Menu Actions-------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Add Menu Item                                  |\n" +
                               "|                   2.Edit Menu Item                                 |\n" +
                               "|                   3.Remove Menu Item                               |\n" +
                               "|                   4.Change Menu Item Availability                  |\n" +
                               "|                   5.Edit Menu Item Description                     |\n" +
                               "|                   6.Go to Homescreen                               |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-6): \n");            
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    // add MenuItems
                    scanner.nextLine(); // Consume the newline character
                    addMenuItemDetails();
                    break;
                
                case 2:
                    // Edit Menu Items
                    scanner.nextLine(); // Consume the newline character
                    editMenuItemDetails();
                    break; 

                case 3:
                    // Remove Menu Items
                    scanner.nextLine(); // Consume the newline character
                    removeMenuItemDetails();
                    break; 

                case 4:
                    // Change Menu Item availability
                    scanner.nextLine(); // Consume the newline character
                    changeMenuItemAvailability();
                    break; 

                case 5:
                    // Edit Menu Item Description
                    scanner.nextLine(); // Consume the newline character
                    editMenuItemDescription();
                    break; 

                case 6:
                    loop=false;
                    System.out.println("Returning to Homescreen...");
                    break;

                default:
                    scanner.nextLine(); // Consume the newline character
                    System.out.println("Invalid Key! Enter your choice (1-5)");
                    break;
            }
        }
    }

    /**
     * This method prompts the manager to enter details of a new menu item, including name,
     * category, description, price, and branch.
     * Checks if the input for new price is valid or not.
     */
    public void addMenuItemDetails(){
        System.out.println("Adding a new menu item.");
        
        System.out.println("Enter the name of the new menu item:");
        String name = scanner.nextLine();

        System.out.println("Enter the category of the new menu item:");
        String category = scanner.nextLine();

        System.out.println("Enter the description of the new menu item:");
        String description = scanner.nextLine();

        double price = -1;
        while (price == -1) {
            System.out.println("Enter the new price for the menu item:");
            try {
                price = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character after a number input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price (e.g., 9.99).");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        System.out.println("Enter the branch of the new menu item:");
        String branch = scanner.nextLine();

        menuController.addMenuItem(name, category, branch, description, price);
    }

    /**
     * This method prompts the manager to enter details of the menu item to edit, including name,
     * category, description, and price.
     * Checks if the input for new price is valid or not.
     */
    public void editMenuItemDetails(){
        System.out.println("Editing an existing menu item.");
        
        System.out.println("Enter the name of the menu item to edit:");
        String name = scanner.nextLine();

        System.out.println("Enter the branch of the menu item:");
        String branch = scanner.nextLine();

        System.out.println("Enter the description of the menu item:");
        String newDescription = scanner.nextLine();
        
        System.out.println("Enter the new category for the menu item:");
        String newCategory = scanner.nextLine();

        double newPrice = -1;
        while (newPrice == -1) {
            System.out.println("Enter the new price for the menu item:");
            try {
                newPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character after a number input
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price (e.g., 9.99).");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        
        menuController.editMenuItem(name, branch, newCategory, newDescription, newPrice);
    }

    /**
     * This method prompts the manager to enter the name of the menu item to remove.
     */
    public void removeMenuItemDetails(){
        System.out.println("Removing a menu item.");

        System.out.println("Enter the name of the menu item to remove:");
        String itemName = scanner.nextLine();

        System.out.println("Enter the branch of the menu item:");
        String branch = scanner.nextLine();
        
        menuController.removeMenuItem(itemName, branch);
    }

    /**
     * This method prompts the manager to enter the name of the menu item and its new availability.
     * Checks if the input for availability is valid or not (i.e. either true or false).
     */
    public void changeMenuItemAvailability(){
        System.out.println("Changing item availability.");
                    
        System.out.println("Enter the name of the menu item to change availability:");
        String itemNameToChangeAvailability = scanner.nextLine();

        System.out.println("Enter the branch of the menu item:");
        String branch = scanner.nextLine();
        
        System.out.println("Enter new availability (true for available, false for not available):");
        Boolean newAvailability = null;
        while (newAvailability == null) {
            try {
                newAvailability = scanner.nextBoolean();
                scanner.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter true or false.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        menuController.changeAvailability(itemNameToChangeAvailability, branch, newAvailability);
    }

    /**
     * This method prompts the manager to enter the name of the menu item and its new description.
     */
    public void editMenuItemDescription(){
        System.out.println("Editing item description.");          
        
        System.out.println("Enter the name of the menu item:");
        String itemNameToEditDescription = scanner.nextLine();

        System.out.println("Enter the branch of the menu item:");
        String branch = scanner.nextLine();
        
        System.out.println("Enter new description:");
        String newDescription = scanner.nextLine();
        
        menuController.editDescription(itemNameToEditDescription, branch, newDescription);
    }
}