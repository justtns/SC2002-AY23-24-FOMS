package main.java.controllers;

import main.java.daos.MenuDAO;
import main.java.models.MenuItem;
import java.util.List;

/**
 * The StaffMenuController class manages operations related to the menu by staff members.
 * It provides methods to retrieve, add, edit, remove, and change the availability of menu items.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffMenuController {
    
    /** The MenuDAO instance to interact with menu item data. */
    private MenuDAO menuDAO;

    /**
     * Constructs a new StaffMenuController with the specified MenuDAO.
     *
     * @param menuDAO the MenuDAO instance
     */
    public StaffMenuController(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
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
     *
     * @param name the name of the menu item
     * @param category the category of the menu item
     * @param branch the branch where the menu item is available
     * @param description the description of the menu item
     * @param price the price of the menu item
     */
    public void addMenuItem(String name, String category, String branch, String description, double price) {
        MenuItem item = new MenuItem(name, category, branch, description, price);
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
     *
     * @param name the name of the menu item to edit
     * @param branch the branch where the menu item is available
     * @param newCategory the new category for the menu item
     * @param newDescription the new description for the menu item
     * @param newPrice the new price for the menu item
     */
    public void editMenuItem(String name, String branch, String newCategory, String newDescription,double newPrice) {
        MenuItem item = MenuDAO.findElement(name, branch);
        if (item == null) {
            System.out.println("Menu item not found.");
        }
        else{
            MenuItem updatedItem = new MenuItem(name, newCategory, branch, newDescription, newPrice);
            menuDAO.updateElement(item, updatedItem);
            menuDAO.saveData();
            System.out.println("Menu item updated: " + updatedItem.getName());
        }
    }

    /**
     * Removes an existing menu item.
     *
     * @param name the name of the menu item to remove
     * @param branch the branch where the menu item is available
     */
    public void removeMenuItem(String name, String branch) {
        MenuItem itemToRemove = MenuDAO.findElement(name, branch);
        if (itemToRemove != null) {
            menuDAO.removeElement(name, branch);
            menuDAO.saveData();
            System.out.println("Menu item removed: " + itemToRemove.getName());
        } else {
            System.err.println("Menu item with name " + name + " not found in branch: " + branch);
        }
    }

    /**
     * Changes the availability of a menu item.
     *
     * @param name the name of the menu item
     * @param branch the branch where the menu item is available
     * @param availability the new availability status of the menu item
     */
    public void changeAvailability(String name, String branch, boolean availability) {
        MenuItem itemToChangeAvailability = MenuDAO.findElement(name, branch);
        if (itemToChangeAvailability != null) {
            itemToChangeAvailability.setAvailable(availability);
            menuDAO.saveData();
            System.out.println("Menu item availability changed: " + itemToChangeAvailability.getName());
        } else {
            System.err.println("Menu item with name " + name + " not found in branch: " + branch);
        }
    }

    /**
     * Edits the description of a menu item.
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
}