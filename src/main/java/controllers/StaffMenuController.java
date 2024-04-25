package main.java.controllers;

import main.java.daos.MenuDAO;
import main.java.models.MenuItem;
import main.java.daos.BranchDAO;
import main.java.models.Branch;
import java.util.List;

/**
 * The StaffMenuController class manages operations related to the menu by staff members.
 * It provides methods to retrieve, add, edit, remove, and change the availability of menu items.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class StaffMenuController {
    
    /** 
     * The MenuDAO instance to interact with menu item data. 
     */
    private MenuDAO menuDAO;

    /** 
     * The BranchDAO instance to interact with menu item data. 
     */
    private BranchDAO branchDAO;

    /**
     * Constructs a new StaffMenuController with the specified MenuDAO.
     *
     * @param menuDAO The menuDAO object to be used by the controller
     * @param branchDAO The branchDAO object to be used by the controller
     */
    public StaffMenuController(MenuDAO menuDAO, BranchDAO branchDAO) {
        this.menuDAO = menuDAO;
        this.branchDAO = branchDAO;
    }

    /**
     * Retrieves all menu items.
     *
     * @return the list of all menu items
     */
    public List<MenuItem> getItems(){
        return menuDAO.getElements();
    }

    /**
     * Adds a new menu item with the given details.
     * Checks if branch can be found and if menu item already exists in the branch.
     *
     * @param name the name of the menu item
     * @param category the category of the menu item
     * @param branchName the branch where the menu item is available
     * @param description the description of the menu item
     * @param price the price of the menu item
     */
    public void addMenuItem(String name, String category, String branchName, String description, double price) {
        Branch branch = branchDAO.findElement(branchName);
        if(branch == null){
            System.out.println("Branch not found: " + branchName);
            return;
        }

        MenuItem item = new MenuItem(name, category, branchName, description, price, true);
        if (MenuDAO.findElement(item.getName(), item.getBranch()) == null) {
            menuDAO.addElement(item);
            menuDAO.saveData();
            System.out.println("Menu item added: " + item.getName());
        } else {
            System.err.println("Menu item already exists in branch: " + item.getBranch());
        }
    }

    /**
     * Edits the details of an existing menu item.
     * Checks if branch can be found and if menu item is found in the branch.
     *
     * @param name the name of the menu item to edit
     * @param branchName the branch where the menu item is available
     * @param newCategory the new category for the menu item
     * @param newDescription the new description for the menu item
     * @param newPrice the new price for the menu item
     */
    public void editMenuItem(String name, String branchName, String newCategory, String newDescription,double newPrice) {
        Branch branch = branchDAO.findElement(branchName);
        if(branch == null){
            System.out.println("Branch not found: " + branchName);
            return;
        }
        
        MenuItem item = MenuDAO.findElement(name, branchName);
        if (item == null) {
            System.out.println("Menu item not found.");
        }
        else{
            MenuItem updatedItem = new MenuItem(name, newCategory, branchName, newDescription, newPrice, true);
            menuDAO.updateElement(item, updatedItem);
            menuDAO.saveData();
            System.out.println("Menu item updated: " + updatedItem.getName());
        }
    }

    /**
     * Removes an existing menu item.
     * Checks if branch can be found and if menu item is found in the branch.
     *
     * @param name the name of the menu item to remove
     * @param branchName the branch where the menu item is available
     */
    public void removeMenuItem(String name, String branchName) {
        Branch branch = branchDAO.findElement(branchName);
        if(branch == null){
            System.out.println("Branch not found: " + branchName);
            return;
        }

        MenuItem itemToRemove = MenuDAO.findElement(name, branchName);
        if (itemToRemove != null) {
            menuDAO.removeElement(name, branchName);
            menuDAO.saveData();
            System.out.println("Menu item removed: " + itemToRemove.getName());
        } else {
            System.err.println("Menu item with name " + name + " not found in branch: " + branchName);
        }
    }

    /**
     * Changes the availability of a menu item.
     * Checks if branch can be found and if menu item is found in the branch.
     *
     * @param name the name of the menu item
     * @param branchName the branch where the menu item is available
     * @param availability the new availability status of the menu item
     */
    public void changeAvailability(String name, String branchName, boolean availability) {
        Branch branch = branchDAO.findElement(branchName);
        if(branch == null){
            System.out.println("Branch not found: " + branchName);
            return;
        }

        MenuItem itemToChangeAvailability = MenuDAO.findElement(name, branchName);
        if (itemToChangeAvailability != null) {
            itemToChangeAvailability.setAvailable(availability);
            menuDAO.saveData();
            System.out.println("Menu item availability changed: " + itemToChangeAvailability.getName());
        } else {
            System.err.println("Menu item with name " + name + " not found in branch: " + branchName);
        }
    }

    /**
     * Edits the description of a menu item.
     * Checks if branch can be found and if menu item is found in the branch.
     *
     * @param itemNameToEditDescription the name of the menu item to edit
     * @param branch the branch where the menu item is available
     * @param newDescription the new description for the menu item
     */
    public void editDescription(String itemNameToEditDescription, String branch, String newDescription) {
        MenuItem itemToEditDescription = MenuDAO.findElement(itemNameToEditDescription, branch);
        
        if (itemToEditDescription != null) {
            itemToEditDescription.setDescription(newDescription);
            menuDAO.saveData();
            System.out.println("Menu item description changed: " + itemToEditDescription.getName());
        } else {
            System.err.println("Menu item with name " + itemNameToEditDescription + " not found in branch: " + branch);
        }
    }

    /**
     * Displays a formatted list of available menu items for a specified branch.
     * The method first checks if the branch exists. If the branch is found, it retrieves and prints the details of each available 
     * menu item, including the name, category, price, branch, and description in a table format.
     * If no menu items are available or the branch does not exist, a corresponding message is displayed.
     *
     * @param branchName The name of the branch for which to display the menu items.
     */
    public void viewMenuItems(String branchName) {

        Branch branch = branchDAO.findElement(branchName);
        if (branch == null) {
            System.out.println("Branch not found: " + branchName);
            return ;
        }

        List<MenuItem> menuItems = getItems();
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
            if (item.getBranch().equalsIgnoreCase(branchName) & item.isAvailable()) { 
                System.out.printf("| %-20s | %-15s | %-10.2f | %-10s | %-24s|%n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranch(), item.getDescription());
            }
        }
        System.out.println("|--------------------------------------------------------------------------------------------|");

    }

}