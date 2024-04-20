package main.java.applications;

import main.java.boundaries.CustomerPostOrderForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerPostOrderAction implements Actions{
    public void execute(CustomerSession session){
        CustomerPostOrderForm orderForm = new CustomerPostOrderForm(session);
        orderForm.postOrderView();
    }
}
