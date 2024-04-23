package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.models.Staff;
import main.java.utils.types.StaffRole;
import java.util.List;
import java.util.stream.Collectors;

public class StaffDisplayController {
    private StaffDAO staffDAO;

    public StaffDisplayController(StaffDAO staffDAO){
        this.staffDAO = staffDAO;
    }

    // Retrieve the full list of staff
    public void displayStaffList() { 
        List<Staff> staffList = staffDAO.getElements();
        printStaffList(staffList); 
    }

    // Manager Display Function
    public void managerDisplayStaffList(String staffUserID){
        Staff currentUser = staffDAO.findElement(staffUserID);
        
        String branch = currentUser.getBranch();
        displayStaffListByBranch(branch); // Uses displayStaffListByBranch()
    }

    // Filter by Branch
    public void displayStaffListByBranch(String branch) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getBranch().equalsIgnoreCase(branch))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    // Filter by Role
    public void displayStaffListByRole(StaffRole role) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getRole().equals(role))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    // Filter by Gender
    public void displayStaffListByGender(String gender) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    // Filter by Age
    public void displayStaffListByAge(int age) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getAge() == age)
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    // Helper methods
    private void printStaffList(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
        } else {
            staffList.forEach(staff -> System.out.println(staff.toString()));
        }
    }
}
