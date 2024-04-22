package main.java.controllers;

import main.java.daos.MenuDAO;
import main.java.models.MenuItem;
import java.util.List;

public class StaffMenuController {
    private MenuDAO menuDAO;

    public StaffMenuController(MenuDAO menuDAO) {
        this.menuDAO = menuDAO;
    }

    public List<MenuItem> getItems(){
        return menuDAO.getElements();
    }

    public void addMenuItem(String name, String category, String branch, double price) {
        MenuItem item = new MenuItem(name, category, branch, price);
        if (MenuDAO.findElement(item.getName(), item.getBranch()) == null) {
            menuDAO.addElement(item);
            System.out.println("Menu item added: " + item.getName());
        } else {
            System.err.println("Menu item already exists in branch: " + item.getBranch());
        }
    }

    public void editMenuItem(String name, String branch, String newCategory, double newPrice) {
        
        MenuItem item = MenuDAO.findElement(name, branch);
        if (item == null) {
            System.out.println("Menu item not found.");
        }
        else{
            MenuItem updatedItem = new MenuItem(name, newCategory, branch, newPrice);
            menuDAO.updateElement(item, updatedItem);
            System.out.println("Menu item updated: " + updatedItem.getName());
        }
        
    }

    public void removeMenuItem(String name, String branch) {
        MenuItem itemToRemove = MenuDAO.findElement(name, branch);
        if (itemToRemove != null) {
            menuDAO.removeElement(name, branch);
            System.out.println("Menu item removed: " + itemToRemove.getName());
        } else {
            System.err.println("Menu item with name " + itemToRemove.getName() + " not found in branch: " + itemToRemove.getBranch());
        }
    }

    public void changeAvailability(String name, String branch, boolean availability) {
        MenuItem itemToChangeAvailability = MenuDAO.findElement(name, branch);
        if (itemToChangeAvailability != null) {
            itemToChangeAvailability.setAvailable(availability);
            System.out.println("Menu item availability changed: " + itemToChangeAvailability.getName());
        } else {
            System.err.println("Menu item with name " + itemToChangeAvailability.getName() + " not found in branch: " + itemToChangeAvailability.getBranch());
        }
    }
}


