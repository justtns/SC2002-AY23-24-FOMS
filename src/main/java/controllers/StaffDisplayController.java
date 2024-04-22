package main.java.controllers;

import main.java.models.Staff;
import java.util.List;


import main.java.daos.StaffDAO;

public class StaffDisplayController {
    private StaffDAO staffDAO;
    private String branch;

    public StaffDisplayController(StaffDAO staffDAO, String branch){
        this.staffDAO = staffDAO;
        this.branch = branch;
    }

    public void displayStaffList(){  // implement display using STAFF DAO
        List<Staff> staffList = staffDAO.getElements();
        // Print the staff details who belong to the 'branch'
        System.out.println("Staff list for branch: " + branch);
        for (Staff staff : staffList) {
            if (staff.getBranch().equals(branch)) {
                System.out.println(staff);
            }
        }
    }

}