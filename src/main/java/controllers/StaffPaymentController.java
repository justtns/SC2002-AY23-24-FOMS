package main.java.controllers;

import main.java.daos.PaymentDAO;
import main.java.models.PaymentMethod;

/**
 * The StaffPaymentController class manages operations related to payment methods by staff members.
 * It provides methods to add and remove payment methods.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffPaymentController {
    /** The PaymentDAO instance to interact with payment method data. */
    private PaymentDAO paymentDAO;

    /**
     * Constructs a new StaffPaymentController with the specified PaymentDAO.
     *
     * @param paymentDAO the PaymentDAO instance
     */
    public StaffPaymentController(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    /**
     * Adds a new payment method with the given name and type.
     *
     * @param name the name of the payment method
     * @param type the type of the payment method
     * @return true if the payment method was successfully added, false otherwise
     */
    public boolean addPaymentMethod(String name, String type) {
        PaymentMethod method = new PaymentMethod(name, type);
        try {
            paymentDAO.addElement(method);
            paymentDAO.saveData();
            return true;
        } catch (Exception e) {
            System.out.println("Error adding payment method: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes the payment method with the given name.
     *
     * @param name the name of the payment method to remove
     * @return true if the payment method was successfully removed, false otherwise
     */
    public boolean removePaymentMethod(String name) {
        try {
            paymentDAO.removeElement(name);
            paymentDAO.saveData();
            return true;
        } catch (Exception e) {
            System.out.println("Error removing payment method: " + e.getMessage());
            return false;
        }
    }
}