package main.java.factories;

import java.util.Scanner;

import main.java.boundaries.*;
import main.java.utils.loggers.*;

public class ActionFactory {
    public Form getAction(CustomerSession session, Scanner scanner, int actionType){
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
    public Form getAction(StaffSession session, Scanner scanner, int actionType){

    }
}
