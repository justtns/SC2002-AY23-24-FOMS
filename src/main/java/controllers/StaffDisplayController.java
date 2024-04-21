package main.java.controllers;

import main.java.models.Order;
import main.java.daos.OrderDAO;
import main.java.models.Staff;
import main.java.utils.types.OrderStatus;
import main.java.daos.StaffDAO;

public class StaffDisplayController {
    private StaffDAO staffDAO;

    public StaffDisplayController(StaffDAO staffDAO){
        this.staffDAO = staffDAO;
    }


}