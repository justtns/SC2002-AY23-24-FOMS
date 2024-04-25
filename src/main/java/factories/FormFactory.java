package main.java.factories;

import java.util.Scanner;

import main.java.boundaries.*;
import main.java.utils.loggers.*;
import main.java.utils.types.StaffRole;

/**
 * The FormFactory class is responsible for creating different types of forms based on the user session and action type.
 * It provides methods to generate forms for both customers and staff members.
 *
 * @author SDDA Team 1
 * @version 1.2
 * @since 25-Apr-2024
 */
public class FormFactory {

    /**
     * Default Constructor for FormFactory
     */
    public FormFactory(){
    }

    /**
     * Creates a form based on the customer session and action type.
     *
     * @param session    The customer session.
     * @param scanner    The scanner object for input.
     * @param actionType The type of action to perform.
     * @return A form corresponding to the action type, or null if the action type is not recognized.
     */
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

    /**
     * Creates a form based on the staff session and action type.
     *
     * @param session    The staff session.
     * @param scanner    The scanner object for input.
     * @param actionType The type of action to perform.
     * @return A form corresponding to the action type and staff role, or null if the action type or role is not recognized.
     */
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