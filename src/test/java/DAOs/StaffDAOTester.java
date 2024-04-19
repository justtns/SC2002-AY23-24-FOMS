package test.java.DAOs;
import main.java.dao.StaffDAO;
import main.java.domain.models.Staff;
import main.java.domain.types.Role;

public class StaffDAOTester {
    public static void main(String[] args) {
        StaffDAO staffDao = new StaffDAO();

        // Test adding a staff member
        Staff newStaff = new Staff("John Doe", "102", "password123", Role.Staff, "Male", 30, "Main Branch");
        staffDao.addElement(newStaff);
        System.out.println("Add Staff Test: " + (staffDao.getElements().contains(newStaff) ? "Passed" : "Failed"));

        // Test finding a staff member
        Staff foundStaff = staffDao.findElement("John Doe");
        System.out.println("Find Staff Test: " + (foundStaff != null ? "Passed" : "Failed"));

        // Test updating a staff member
        Staff updatedStaff = new Staff("John Doe", "102", "newpassword", Role.Staff, "Male", 30, "Main Branch");
        staffDao.updateElement(newStaff, updatedStaff);
        foundStaff = staffDao.findElement("John Doe");
        System.out.println("Update Staff Test: " + (foundStaff.getPassword().equals("newpassword") ? "Passed" : "Failed"));

        // Test removing a staff member
        staffDao.removeElement("John Doe");
        foundStaff = staffDao.findElement("John Doe");
        System.out.println("Remove Staff Test: " + (foundStaff == null ? "Passed" : "Failed"));
    }
}

