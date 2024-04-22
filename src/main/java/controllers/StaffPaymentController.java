package main.java.controllers;

import main.java.daos.PaymentDAO;
import main.java.models.PaymentMethod;
import main.java.utils.types.PaymentType;

public class StaffPaymentController {
    private PaymentDAO paymentDAO = new PaymentDAO();

    public StaffPaymentController(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

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
