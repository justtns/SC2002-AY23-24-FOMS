package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.daos.BranchDAO;
import main.java.models.Branch;
import main.java.utils.types.StaffRole;
import main.java.models.Staff;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The StaffAssignmentController class manages staff assignments and promotions within the restaurant chain.
 * It provides methods to promote staff to managers, transfer staff between branches, and assign managers to branches.
 * StaffAssignmentController interacts with the StaffDAO to retrieve and update staff information.
 * It also calculates and enforces manager quotas for each branch, taking into account the constaints given in the assignment.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.4
 * @since 24-Apr-2024
 */
public class StaffAssignmentController {

    /** 
    * The data access object (DAO) for staff 
    */
    private StaffDAO staffDAO;

    /** 
    * The BranchDAO instance to interact with branch data.
    */
    private BranchDAO branchDAO;

    /**
     * Constructor for StaffAssignmentController.
     * Initializes the StaffDAO and branchDAO instance.
     * 
     * @param staffDAO the Data Access Object for managing Staff objects
     */
    public StaffAssignmentController(StaffDAO staffDAO, BranchDAO branchDAO) {
        this.staffDAO = staffDAO;
        this.branchDAO = branchDAO;
    }

    /**
     * Promotes a staff member to manager role.
     * 
     * @param staffID the ID of the staff member to be promoted
     * @return true if promotion is successful, false otherwise
     */
    public boolean promoteToManager(String staffID) {
        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);
        if(staff == null){
            System.out.println("Error: Staff is not found.");
            return false;
        }
        else if(staff.getRole() == StaffRole.MANAGER){
            System.out.println("Error: Staff is already Manager.");
            return false;
        }
        else{
            String branch = staff.getBranch();
            if(calculateManagerQuota(branch)){
                staff.setRole(StaffRole.MANAGER);
                staffDAO.updateElement(oldstaff, staff);
                staffDAO.saveData();
                return true;
            }
            else{
                System.out.println("The branch already has the required number of managers.");
                return false;
            }
        }
    }

    /**
     * Transfers a staff member from one branch to another.
     * Checks if the newBranch capacity is over the maximum staff quota for the newBranch.
     * 
     * @param staffID the ID of the staff member to be transferred
     * @param oldBranch the current branch of the staff member
     * @param newBranch the new branch to transfer the staff member to
     * @return true if transfer is successful, false otherwise
     */
    public boolean transferStaff(String staffID, String oldBranch, String newBranch) {
        Branch branch = branchDAO.findElement(newBranch);
        if(branch == null){
            System.out.println("Branch not found: " + newBranch);
            return false;
        }

        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);

        // get branchCapacity and get staffCount in branch!
        int branchCapacity = branch.getCapacity();
        List<Staff> staffInBranch = staffDAO.getElements().stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch.getName()) && s.getRole().equals(StaffRole.STAFF))
                .collect(Collectors.toList());
        int staffCount = staffInBranch.size();

        if(staff == null){
            System.out.println("Staff ID is not found.");
            return false;
        }
        else if(!staff.getBranch().equalsIgnoreCase(oldBranch)){
            System.out.println("Staff does not belong to branch: " + oldBranch);
            return false;
        }
        else if(staff.getBranch().equalsIgnoreCase(newBranch)){
            System.out.println("Staff already belongs to branch: " + newBranch);
            return false;
        }
        else{

            StaffRole role = staff.getRole();
            switch (role) {
                case STAFF:
                    if(staffCount >= branchCapacity){
                        System.out.println("Number of Staff will exceed Branch Capacity");
                        return false;
                    }
                    else{
                        staff.setBranch(newBranch);
                        staffDAO.updateElement(oldstaff, staff);
                        staffDAO.saveData();
                        return true;
                    }
            
                case MANAGER:
                    return assignManager(staffID, newBranch);

                case ADMIN:
                    return false;

                default:
                    return false;
            }
        }
    }

    /**
     * Assigns a manager to a specific branch.
     * 
     * @param staffID the ID of the manager to be assigned
     * @param branch the branch to which the manager should be assigned
     * @return true if assignment is successful, false otherwise
     */
    public boolean assignManager(String staffID, String branch) {
        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);
        if (staff == null || !staff.getRole().equals(StaffRole.MANAGER)) {
            System.out.println("Invalid staff ID.");
            return false;
        }

        if (calculateManagerQuota(branch)) {
            staff.setBranch(branch);
            staffDAO.updateElement(oldstaff, staff);
            staffDAO.saveData();
            return true;
        } else {
            System.out.println("The branch already has the required number of managers.");
            return false;
        }

    }

    // Helper function
    /**
     * Calculates whether a branch has reached its manager quota.
     * 
     * @param branch the branch for which to calculate the manager quota
     * @return true if the branch can accommodate more managers, false otherwise
     */
    private boolean calculateManagerQuota(String branch) {
        // Count number of Staff
        List<Staff> staffInBranch = staffDAO.getElements().stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch) && s.getRole().equals(StaffRole.STAFF))
                .collect(Collectors.toList());
        int staffCount = staffInBranch.size();

        // Count number of Managers
        List<Staff> managersInBranch = staffDAO.getElements().stream()
                .filter(s -> s.getBranch().equalsIgnoreCase(branch) && s.getRole().equals(StaffRole.MANAGER))
                .collect(Collectors.toList());
        int managerCount = managersInBranch.size();

        int managerQuota;
        
        if (staffCount <= 4) {
            managerQuota = 1;
        } else if (staffCount <= 8) {
            managerQuota = 2;
        } else if (staffCount <= 15) {
            managerQuota = 3;
        } else {
            managerQuota = 3;
        }

        if (managerCount < managerQuota) {
            return true;
        } else {
            return false;
        }
    }
}