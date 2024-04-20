package main.java.applications;
import main.java.boundaries.CustomerPaymentForm;
import main.java.utils.loggers.CustomerSession;

public class CustomerPaymentAction implements Actions{
    public void execute(CustomerSession session){
        CustomerPaymentForm paymentForm = new CustomerPaymentForm(session);
        paymentForm.promptPaymentMethod();
    }
}
