package main.java.models;

/**
 * The PaymentMethod class represents a payment method used in transactions.
 * It is a business object that contains all necessary accessors and mutators for CRUD Operations performed by PaymentMethodDAO.
 * PaymentMethod class is in a 'has-a' relation with PaymentMethodDAO (Composition).
 * It contains information such as the name and type of the payment method.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class PaymentMethod {

    /** The name of the payment method. */
    private String name;

    /** The type of the payment method. */
    private String type;

    /**
     * Constructs a PaymentMethod object with the given name and type.
     * 
     * @param name The name of the payment method
     * @param type The type of the payment method
     */
    public PaymentMethod(String name, String type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Retrieves the name of the payment method.
     * 
     * @return The name of the payment method
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the payment method.
     * 
     * @param name The new name of the payment method
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the type of the payment method.
     * 
     * @return The type of the payment method
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the payment method.
     * 
     * @param type The new type of the payment method
     */
    public void setType(String type) {
        this.type = type;
    }
}