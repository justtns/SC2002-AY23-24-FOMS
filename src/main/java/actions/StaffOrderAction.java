package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.StaffOrderForm;
import main.java.utils.loggers.StaffSession;

public class StaffOrderAction implements StaffActions{
    public void execute(StaffSession session){
        StaffOrderForm orderForm = new StaffOrderForm(session, scanner);
    }
}
