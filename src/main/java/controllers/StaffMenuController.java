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

    public void addMenuItem(MenuItem item) {
        if (MenuDAO.findElement(item.getName(), item.getBranch()) == null) {
            menuDAO.addElement(item);
            System.out.println("Menu item added: " + item.getName());
        } else {
            System.err.println("Menu item already exists in branch: " + item.getBranch());
        }
    }

    public void editMenuItem(MenuItem oldItem, MenuItem updatedItem) {

        MenuItem currentItem = MenuDAO.findElement(oldItem.getName(), oldItem.getBranch());
        if (currentItem != null) {
            menuDAO.updateElement(currentItem, updatedItem);
            System.out.println("Menu item updated: " + updatedItem.getName());
        } else {
            System.err.println("Menu item with name " + oldItem.getName() + " not found in branch: " + oldItem.getBranch());
        }
    }

    public void removeMenuItem(MenuItem item) {
        MenuItem currentItem = MenuDAO.findElement(item.getName(), item.getBranch());
        if (currentItem != null) {
            menuDAO.removeElement(currentItem.getName(), currentItem.getBranch());
            System.out.println("Menu item removed: " + item.getName());
        } else {
            System.err.println("Menu item with name " + item.getName() + " not found in branch: " + item.getBranch());
        }
    }

    public void changeAvailability(MenuItem item, boolean availability) {
        MenuItem currentItem = MenuDAO.findElement(item.getName(), item.getBranch());
        if (currentItem != null) {
            currentItem.setAvailable(availability);
            System.out.println("Menu item availability changed: " + item.getName());
        } else {
            System.err.println("Menu item with name " + item.getName() + " not found in branch: " + item.getBranch());
        }
    }
}


