package main.java.controllers;

import java.util.List;
import main.java.daos.MenuDAO;
import main.java.models.MenuItem;

/**
 * The CustomerMenuController class implements the business logic layer operations related to menus for customers.
 * It provides methods to interact with menu data and performs necessary business logic operations.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerMenuController {

    /** The data access object (DAO) for menus. */
    private MenuDAO menuDAO;

    /**
     * Constructs a CustomerMenuController object with the specified MenuDAO.
     * 
     * @param menuDAO The MenuDAO object to be used by the controller
     */
    public CustomerMenuController(MenuDAO menuDAO){
        this.menuDAO = menuDAO;
    }

    /**
     * Retrieves a list of all menu items.
     * 
     * @return A list of MenuItem objects representing all menu items
     */
    public List<MenuItem> getitems(){
        return menuDAO.getElements();
    }

    /**
     * Finds a menu item by its name and branch.
     * 
     * @param name The name of the menu item to find
     * @param branch The branch associated with the menu item
     * @param menuItems The list of menu items to search within
     * @return The MenuItem object found, or null if not found
     */
    public MenuItem findMenuItemByName(String name, String branch, List<MenuItem> menuItems) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranch().equalsIgnoreCase(branch)) {
                return item;
            }
        }
        return null;
    }
}