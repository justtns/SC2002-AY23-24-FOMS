package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.AdminPaymentForm;
import main.java.utils.loggers.StaffSession;

public class AdminPaymentAction implements AdminActions{
    public void execute(StaffSession session, Scanner scanner){
        AdminPaymentForm adminPaymentForm = new AdminPaymentForm(session, scanner);
        adminPaymentForm.adminPaymentView();
    }
}
