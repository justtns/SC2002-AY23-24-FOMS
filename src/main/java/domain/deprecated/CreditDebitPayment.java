package main.java.domain.deprecated;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditDebitPayment implements PaymentInterface{
    private String name;
    private String cardNumber;
    private String cvc;
    private String expiryDate; // Format: MM/YY
    private String type;

    // Constructor
    public CreditDebitPayment(String name, String cardNumber, String cvc, String expiryDate, String type) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expiryDate = expiryDate;
        this.type = type;
    }

    public int populateDetails(){
        /**
         * Prints parameters required, Returns number of parameters required.
         */
        System.out.println("Please input your Name, Card Number, CVC, and Expiry Date (MM/YY).");
        return 4;
    }
    
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCVC() {
        return cvc;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public boolean validate() {
        return validateCardNumber(this.getCardNumber()) && validateCVC(this.getCVC()) && validateExpiryDate(this.getExpiryDate());
    }
    
    private boolean validateCardNumber(String cardNumber) {
        // Validate card number to be 15, 16, or 19 digits
        int length = cardNumber.length();
        if (!(length == 15 || length == 16 || length == 19)){
            System.out.println("Submitted card number is invalid.");
        }
        return length == 15 || length == 16 || length == 19;
    }

    private boolean validateCVC(String cvc) {
        // Validate CVC to be exactly 3 digits
        if (!cvc.matches("\\d{3}")){
            System.out.println("Submitted card has invalid CVC.");
        }
        return cvc.matches("\\d{3}");
    }

    private boolean validateExpiryDate(String expiryDate) {
        // Validate expiry date is not in the past
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        try {
            YearMonth expiryYM = YearMonth.parse(expiryDate, formatter);
            LocalDate expiry = expiryYM.atEndOfMonth();
            if (LocalDate.now().isAfter(expiry)) {
                System.out.println("Submitted card is expired.");
            }
            return !LocalDate.now().isAfter(expiry);
        }
        catch (DateTimeParseException e) {
            System.out.println("Submitted expiry date format is wrong. Please follow MM/yy format.");
        }
        return false;
    }
}
