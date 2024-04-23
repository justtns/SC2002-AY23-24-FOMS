package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.models.Staff;
import main.java.utils.types.StaffRole;

/**
 * The StaffManagementController class handles the management of staff, including adding, editing, and removing staff records.
 * It provides methods to perform CRUD (Create, Read, Update, Delete) operations on staff data.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.0
 * @since 24-Apr-2024
 */
public class StaffManagementController {
    /** The data access object (DAO) for staff */
    private StaffDAO staffDAO;

    /**
     * Constructs a StaffManagementController object with the specified StaffDAO.
     * 
     * @param staffDAO The StaffDAO object to be used by the controller
     */
    public StaffManagementController(StaffDAO staffDAO){
        this.staffDAO = staffDAO;
    }

    /**
     * Adds a new staff member to the system.
     * 
     * @param name The name of the staff member
     * @param loginId The login ID of the staff member
     * @param role The role of the staff member
     * @param gender The gender of the staff member
     * @param age The age of the staff member
     * @param branch The branch where the staff member works
     * @return True if the staff member is successfully added, false otherwise
     */
    public boolean addStaff(String name, String loginId,  StaffRole role, String gender, int age, String branch) {
        String defaultPassword = "password";
        Staff newStaff = new Staff(name, loginId, defaultPassword, role, gender, age, branch);
        if(staffDAO.findElement(loginId) == null) {
            staffDAO.addElement(newStaff);
            staffDAO.saveData();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Edits the details of an existing staff member.
     * 
     * @param loginId The login ID of the staff member to be edited
     * @param newLoginId The new login ID for the staff member
     * @param newPassword The new password for the staff member
     * @param newBranch The new branch for the staff member
     * @return True if the staff member is successfully edited, false otherwise
     */
    public boolean editStaff(String loginId, String newLoginId, String newPassword, String newBranch) {
        Staff oldStaff = staffDAO.findElement(loginId);
        Staff staff = staffDAO.findElement(loginId);
        if(staff != null) {
            staff.setPassword(newPassword);
            staff.setLoginID(newLoginId);
            staff.setBranch(newBranch);
            staffDAO.updateElement(oldStaff, staff);
            staffDAO.saveData();
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Removes an existing staff member from the system.
     * 
     * @param loginId The login ID of the staff member to be removed
     * @return True if the staff member is successfully removed, false otherwise
     */
    public boolean removeStaff(String loginId) {
        Staff staff = staffDAO.findElement(loginId);
        if(staff != null) {
            staffDAO.removeElement(loginId);
            staffDAO.saveData();
            return true;
        }
        else{
            return false;
        }
    }
}
