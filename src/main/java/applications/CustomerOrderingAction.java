package main.java.applications;

import main.java.boundaries.CustomerOrderingForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerOrderingAction implements Actions{
    public void execute(CustomerSession session){
        CustomerOrderingForm orderForm = new CustomerOrderingForm(session);
        orderForm.orderingView();
    }
}
