package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.ManagerMenuForm;
import main.java.utils.loggers.StaffSession;

public class ManagerMenuAction implements ManagerActions{
    public void execute(StaffSession session, Scanner scanner){
        ManagerMenuForm menuForm = new ManagerMenuForm(session, scanner);
        menuForm.managerMenuView();
    }
}
