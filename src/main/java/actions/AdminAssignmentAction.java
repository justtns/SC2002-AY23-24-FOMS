package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.AdminAssignmentForm;
import main.java.utils.loggers.StaffSession;

public class AdminAssignmentAction implements AdminActions{
    public void execute(StaffSession session, Scanner scanner){
        AdminAssignmentForm adminAssignmentForm = new AdminAssignmentForm(session, scanner);
        adminAssignmentForm.adminAssignmentView();
    }
}
