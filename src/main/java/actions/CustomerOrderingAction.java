package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.CustomerOrderingForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerOrderingAction implements Actions{
    public void execute(CustomerSession session, Scanner scanner){
        CustomerOrderingForm orderForm = new CustomerOrderingForm(session, scanner);
        orderForm.orderingView();
    }
}
