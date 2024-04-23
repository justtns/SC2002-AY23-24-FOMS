package main.java.factories;

import java.util.Scanner;

import main.java.boundaries.*;
import main.java.utils.loggers.*;
import main.java.utils.types.StaffRole;

public class FormFactory {
    public Form getForm(CustomerSession session, Scanner scanner, int actionType){
        switch (actionType) {
            case 1:
                return (Form) new CustomerOrderingForm(session, scanner);
            case 2:
                return (Form) new CustomerPaymentForm(session, scanner);            
            case 3:
                return (Form) new CustomerPostOrderForm(session, scanner);        
            default:
                return null;
        }
    }
    public Form getForm(StaffSession session, Scanner scanner, int actionType){
        StaffRole role = session.getStaffRole();
        switch (role) {
            case Staff:
                switch (actionType) {
                    case 1:
                        return (Form) new StaffOrderForm(session, scanner);                
                    default:
                        return null;
                }
            
            case Manager:
                switch (actionType) {
                    case 1:
                        return (Form) new StaffOrderForm(session, scanner);
                    case 2:
                        return (Form) new ManagerMenuForm(session, scanner);
                    case 3:
                        return (Form) new ManagerDisplayForm(session, scanner);          
                    default:
                        return null;
                }

            case Admin:
                switch (actionType) {
                    case 1:
                        return (Form) new x(session, scanner);
                    case 2:
                        return (Form) new AdminDisplayForm(session, scanner);
                    case 3:
                        return (Form) new AdminBranchForm(session, scanner);          
                    case 4:
                        return (Form) new AdminAssignmentForm(session, scanner);
                    case 5:
                        return (Form) new AdminPaymentForm(session, scanner); 
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}