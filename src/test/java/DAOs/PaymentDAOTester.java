package test.java.DAOs;
import main.java.daos.PaymentDAO;
import main.java.models.PaymentMethod;
import main.java.utils.types.PaymentType;

public class PaymentDAOTester {
    public static void main(String[] args) {
        PaymentDAO paymentDao = new PaymentDAO();

        // Test adding a payment method
        PaymentMethod newMethod = new PaymentMethod("Visa", "Credit");
        paymentDao.addElement(newMethod);
        System.out.println("Add Payment Method Test: " + (paymentDao.getElements().contains(newMethod) ? "Passed" : "Failed"));

        // Test finding a payment method
        PaymentMethod foundMethod = paymentDao.findElement("Visa");
        System.out.println("Find Payment Method Test: " + (foundMethod != null ? "Passed" : "Failed"));

        // Test updating a payment method
        PaymentMethod updatedMethod = new PaymentMethod("Visa", "Online");
        paymentDao.updateElement(newMethod, updatedMethod);
        foundMethod = paymentDao.findElement("Visa");
        System.out.println("Update Payment Method Test: " + (foundMethod.getType() == "Online" ? "Passed" : "Failed"));

        // Test removing a payment method
        paymentDao.removeElement("Visa");
        foundMethod = paymentDao.findElement("Visa");
        System.out.println("Remove Payment Method Test: " + (foundMethod == null ? "Passed" : "Failed"));
    }
}
