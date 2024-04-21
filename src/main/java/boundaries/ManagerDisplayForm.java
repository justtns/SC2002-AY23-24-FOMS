package main.java.boundaries;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.java.controllers.StaffDisplayController;
import main.java.daos.OrderDAO;
import main.java.daos.StaffDAO;
import main.java.models.MenuItem;
import main.java.models.Order;
import main.java.utils.loggers.CustomerSession;
import main.java.utils.loggers.StaffSession;
import main.java.utils.types.OrderStatus;
import main.java.utils.types.StaffRole;

public class ManagerDisplayForm {

    private StaffDAO staffDAO = new StaffDAO();
    private StaffDisplayController displayController = new StaffDisplayController(staffDAO);
    private String staffUserID;
    private StaffRole staffRole;
    private Scanner scanner;

    public ManagerDisplayForm(StaffSession session, Scanner scanner){
        this.staffUserID = session.getStaffUserID();
        this.staffRole = session.getStaffRole();
        this.scanner = scanner;
    }

    
    

}
