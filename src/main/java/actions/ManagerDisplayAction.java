package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.ManagerDisplayForm;
import main.java.utils.loggers.StaffSession;

public class ManagerDisplayAction implements ManagerActions{
    public void execute(StaffSession session, Scanner scanner){
        ManagerDisplayForm managerDisplayForm = new ManagerDisplayForm(session, scanner);
    }
}
