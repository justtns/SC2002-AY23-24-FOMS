package main.java.boundaries;

public class CreditDebitPaymentService implements PaymentService{
    @Override
    public boolean simulatePayment() {
        System.out.println("Credit/Debit payment chosen.");
        return true; 
    }
}
