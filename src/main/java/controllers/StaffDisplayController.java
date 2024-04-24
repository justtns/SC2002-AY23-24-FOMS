package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.models.Staff;
import main.java.daos.BranchDAO;
import main.java.models.Branch;
import main.java.utils.types.StaffRole;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The StaffDisplayController class handles the display of staff information based on various filters.
 * It provides methods to manage staff-related operations such as adding, editing, and removing staff records.
 * This class acts as an intermediary between the presentation layer (UI) and the data access layer (DAO).
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class StaffDisplayController {

    /** 
    * The data access object (DAO) for staff 
    */
    private StaffDAO staffDAO;

    /** 
    * The BranchDAO instance to interact with branch data.
    */
    private BranchDAO branchDAO;

    /**
     * Constructs a StaffDisplayController object with the specified StaffDAO.
     * 
     * @param staffDAO The StaffDAO object to be used by the controller
     * @param branchDAO The BranchDAO object to be used by the controller
     */
    public StaffDisplayController(StaffDAO staffDAO, BranchDAO branchDAO){
        this.staffDAO = staffDAO;
        this.branchDAO = branchDAO;
    }

    /**
     * Displays the full list of staff. Different from PrintStaffList which is a helper method.
     */
    public void displayStaffList() { 
        List<Staff> staffList = staffDAO.getElements();
        printStaffList(staffList); 
    }

    /**
     * Displays the list of staff for the manager's branch.
     * 
     * @param staffUserID The user ID of the manager
     */
    public void managerDisplayStaffList(String staffUserID){
        Staff currentUser = staffDAO.findElement(staffUserID);
        String branch = currentUser.getBranch();
        displayStaffListByBranch(branch);
    }

    /**
     * Displays the list of staff filtered by branch.
     * Checks if branch exists or not.
     * 
     * @param branch The branch name for filtering
     */
    public void displayStaffListByBranch(String branchName) { 
        Branch branch = branchDAO.findElement(branchName);
        if(branch == null){
            System.out.println("Branch not found: " + branchName);
            return;
        }
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getBranch().equalsIgnoreCase(branchName))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    /**
     * Displays the list of staff filtered by role.
     * 
     * @param role The staff role for filtering
     */
    public void displayStaffListByRole(StaffRole role) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getRole().equals(role))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    /**
     * Displays the list of staff filtered by gender.
     * 
     * @param gender The gender for filtering
     */
    public void displayStaffListByGender(String gender) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    /**
     * Displays the list of staff filtered by age.
     * 
     * @param age The age for filtering
     */
    public void displayStaffListByAge(int age) { 
        List<Staff> staffList = staffDAO.getElements().stream()
                .filter(staff -> staff.getAge() == age)
                .collect(Collectors.toList());
        printStaffList(staffList);
    }

    // Helper methods for above methods

    /**
     * Helper method that prints the list of staff to the console upon filtering using previous methods.
     * 
     * @param staffList The list of staff to be printed
     */
    private void printStaffList(List<Staff> staffList) {
        if (staffList.isEmpty()) {
            System.out.println("No staff found.");
        } else {
            staffList.forEach(staff -> System.out.println(staff.toString()));
        }
    }
}