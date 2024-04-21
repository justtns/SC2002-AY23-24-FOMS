package main.java.actions;

import java.util.Scanner;

import main.java.boundaries.CustomerPostOrderForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerPostOrderAction implements Actions{
    public void execute(CustomerSession session, Scanner scanner){
        CustomerPostOrderForm orderForm = new CustomerPostOrderForm(session, scanner);
        orderForm.postOrderView();
    }
}
