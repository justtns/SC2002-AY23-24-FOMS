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
            case STAFF:
                switch (actionType) {
                    case 1:
                        return (Form) new StaffOrderForm(session, scanner);                
                    default:
                        return null;
                }
            
            case MANAGER:
                switch (actionType) {
                    case 1:
                        return (Form) new StaffOrderForm(session, scanner);
                    case 2:
                        return (Form) new ManagerMenuForm(scanner);
                    case 3:
                        return (Form) new ManagerDisplayForm(session, scanner);          
                    default:
                        return null;
                }

            case ADMIN:
                switch (actionType) {
                    case 1:
                        return (Form) new AdminManagementForm(scanner);
                    case 2:
                        return (Form) new AdminDisplayForm(scanner);
                    case 3:
                        return (Form) new AdminBranchForm(scanner);          
                    case 4:
                        return (Form) new AdminAssignmentForm(scanner);
                    case 5:
                        return (Form) new AdminPaymentForm(scanner); 
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}