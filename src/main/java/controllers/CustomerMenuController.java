package main.java.controllers;

import java.util.List;
import main.java.daos.MenuDAO;
import main.java.models.MenuItem;

public class CustomerMenuController {
    private MenuDAO menuDAO;

    public CustomerMenuController(MenuDAO menuDAO){
        this.menuDAO = menuDAO;
    }

    public  List<MenuItem> getitems(){
        return menuDAO.getElements();
    }

    public MenuItem findMenuItemByName(String name,String branch, List<MenuItem> menuItems) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranch().equalsIgnoreCase(branch)) {
                return item;
            }
        }
        return null;
   }
}
