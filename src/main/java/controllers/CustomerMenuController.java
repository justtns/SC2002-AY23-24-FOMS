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
}
