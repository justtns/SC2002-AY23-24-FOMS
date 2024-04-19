package test.java.DAOs;
import main.java.domain.models.PaymentMethod;
import main.java.domain.types.PaymentType;
import main.java.dao.PaymentDAO;

public class PaymentDAOTester {
    public static void main(String[] args) {
        PaymentDAO paymentDao = new PaymentDAO();

        // Test adding a payment method
        PaymentMethod newMethod = new PaymentMethod("Visa", PaymentType.Credit);
        paymentDao.addElement(newMethod);
        System.out.println("Add Payment Method Test: " + (paymentDao.getElements().contains(newMethod) ? "Passed" : "Failed"));

        // Test finding a payment method
        PaymentMethod foundMethod = paymentDao.findElement("Visa");
        System.out.println("Find Payment Method Test: " + (foundMethod != null ? "Passed" : "Failed"));

        // Test updating a payment method
        PaymentMethod updatedMethod = new PaymentMethod("Visa", PaymentType.Online);
        paymentDao.updateElement(newMethod, updatedMethod);
        foundMethod = paymentDao.findElement("Visa");
        System.out.println("Update Payment Method Test: " + (foundMethod.getType() == PaymentType.Online ? "Passed" : "Failed"));

        // Test removing a payment method
        paymentDao.removeElement("Visa");
        foundMethod = paymentDao.findElement("Visa");
        System.out.println("Remove Payment Method Test: " + (foundMethod == null ? "Passed" : "Failed"));
    }
}
