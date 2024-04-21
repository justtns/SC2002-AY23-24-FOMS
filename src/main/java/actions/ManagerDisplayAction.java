package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.StaffOrderForm;
import main.java.utils.loggers.StaffSession;

public class ManagerDisplayAction implements StaffActions{
    public void execute(StaffSession session, Scanner scanner){
        StaffOrderForm ManagerDisplayForm = new StaffOrderForm(session, scanner);
    }
}
