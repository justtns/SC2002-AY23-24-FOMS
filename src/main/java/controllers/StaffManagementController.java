package main.java.controllers;

import main.java.daos.BranchDAO;
import main.java.models.Branch;
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
 * @version 1.2
 * @since 24-Apr-2024
 */
public class StaffManagementController {
    /** 
     * The data access object (DAO) for staff 
    */
    private StaffDAO staffDAO;

    /** 
     * The BranchDAO instance to interact with branch data.
    */
    private BranchDAO branchDAO;

    /**
     * Constructs a StaffManagementController object with the specified StaffDAO.
     * 
     * @param staffDAO The StaffDAO object to be used by the controller
     */
    public StaffManagementController(StaffDAO staffDAO, BranchDAO branchDAO){
        this.staffDAO = staffDAO;
        this.branchDAO = branchDAO;
    }

    /**
     * Adds a new staff member to the system.
     * Checks if branch exists or not.
     * 
     * @param name The name of the staff member
     * @param loginId The login ID of the staff member
     * @param role The role of the staff member
     * @param gender The gender of the staff member
     * @param age The age of the staff member
     * @param branch The branch where the staff member works
     * @return True if the staff member is successfully added, false otherwise
     */
    public boolean addStaff(String name, String loginId,  StaffRole role, String gender, int age, String branchName) {

        if (role == StaffRole.ADMIN) {
            branchName = "NA";  // Override the branch name for admins
        } else { // For other roles, check if the branch exists
            Branch branch = branchDAO.findElement(branchName);
            if (branch == null) {
                System.out.println("Branch not found: " + branchName);
                return false;
            }
        }
        
        String defaultPassword = "password";
        Staff newStaff = new Staff(name, loginId, defaultPassword, role, gender, age, branchName);
        if(staffDAO.findElement(loginId) == null) {
            staffDAO.addElement(newStaff);
            staffDAO.saveData();
            return true;
        }
        else{
            System.out.println("Staff account already exists.");
            return false;
        }
    }

    /**
     * Edits the details of an existing staff member, excluding Branch details which is changed in StaffAssignmentController.
     * Checks if staff account exists using login id and if login id is already taken by another staff.
     * 
     * @param loginId The login ID of the staff member to be edited
     * @param newLoginId The new login ID for the staff member, indicate NIL if no changes
     * @param newPassword The new password for the staff member, indicate NIL if no changes
     * @return True if the staff member is successfully edited, false otherwise
     */
    public boolean editStaff(String loginId, String newLoginId, String newPassword) {
        Staff oldStaff = staffDAO.findElement(loginId);
        Staff staff = staffDAO.findElement(loginId);
        if(staff == null){
            System.out.println("Staff account does not exist.");
            return false;
        }
        
        if(staffDAO.findElement(newLoginId) != null) {
            System.out.println("LoginID is already taken.");
            return false;
        }

        if(!newLoginId.equalsIgnoreCase("nil")){
            staff.setLoginID(newLoginId);
        }
        
        if(!newPassword.equalsIgnoreCase("nil")){
            staff.setPassword(newPassword);
        }
        
        staffDAO.updateElement(oldStaff, staff);
        staffDAO.saveData();
        return true;
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
            System.out.println("Staff account does not exist.");
            return false;
        }
    }
}