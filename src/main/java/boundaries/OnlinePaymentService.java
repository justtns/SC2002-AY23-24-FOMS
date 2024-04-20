package main.java.boundaries;

public class OnlinePaymentService implements PaymentService{
    @Override
    public boolean simulatePayment() {

        System.out.println("Online payment chosen.");
        return true; 
    }
}
