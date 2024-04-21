package main.java.applications;
import java.util.Scanner;

import main.java.boundaries.CustomerPaymentForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerPaymentAction implements Actions{
    public void execute(CustomerSession session, Scanner scanner){
        CustomerPaymentForm paymentForm = new CustomerPaymentForm(session, scanner);
        paymentForm.promptPaymentMethod();
    }
}
