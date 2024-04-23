package main.java.controllers;

import main.java.models.Staff;
import java.util.List;


import main.java.daos.StaffDAO;

public class StaffDisplayController {
    private static StaffDAO staffDAO;

    public StaffDisplayController(StaffDAO staffDAO){
        StaffDisplayController.staffDAO = staffDAO;
    }

    public void displayStaffList(String staffUserID){  // implement display using STAFF DAO
        Staff currentUser = staffDAO.findElement(staffUserID);
        
        String branch = currentUser.getBranch();
            List<Staff> staffList = staffDAO.getElements();

            System.out.println("Staff list for branch: " + branch);
            for (Staff staff : staffList) {
                if (staff.getBranch().equals(branch)) {
                    System.out.println(staff); // Assuming Staff has a meaningful toString() implementation
                }
            }
    }
}
