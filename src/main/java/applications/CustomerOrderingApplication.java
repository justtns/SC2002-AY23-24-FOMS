package main.java.applications;

import main.java.boundaries.CustomerOrderingForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerOrderingApplication implements Application{

    public void execute(CustomerSession session){
        CustomerOrderingForm orderForm = new CustomerOrderingForm(session);
        orderForm.placeOrder();
        orderForm.getOrderConfirmation();
        orderForm.getOrderDineIn();
    }
}
