package main.java.entities;

public class OnlinePayment implements PaymentInterface{
    private String email;
    private String password;
    private String domain;

    // Constructor
    public OnlinePayment(String email, String password, String domain) {
        this.email = email;
        this.password = password;
        this.domain = domain;
    }

    public int populateDetails(){
        /**
         * Prints parameters required excluding customerID, Returns number of parameters required including customerID
         */
        System.out.println("Please input your PayPal account email and password.");
        return 2;
    }
    public String getDomain() {
        return domain;
    }

    public String getEmail() {
        return email;
    }

    public boolean validate() {
        return (validateEmail(this.email) && validatePassword(this.password));
    }
    
    public String getPassword(){
        return password;
    }

    private boolean validateEmail(String email) {
        if (!email.matches( "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
            System.out.println("Submitted Email is invalid.");
            return false;
        }
        return true;
    }
    
    private boolean validatePassword(String password) {
        System.out.println("Authenticating with " + domain +" Service...");
        return true;
    }
}