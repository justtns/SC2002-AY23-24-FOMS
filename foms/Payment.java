
public class Payment {
    private int paymentId;
    private double amount;
    private String paymentMethod;
    private int OrderId;
    private static int counter=0;

    // Constructor

    public Payment(double amount, String paymentMethod, int orderId) {
        this.paymentId = counter++;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        OrderId = orderId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }
    // Getters and Setters
    // Implement as needed based on your requirements
}
