package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.StaffOrderForm;
import main.java.utils.loggers.StaffSession;

public class ManagerMenuAction implements ManagerActions{
    public void execute(StaffSession session, Scanner scanner){
        StaffOrderForm ManagerMenuForm = new StaffOrderForm(session, scanner);
    }
}
