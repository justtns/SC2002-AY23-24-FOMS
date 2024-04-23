package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.daos.BranchDAO;
import main.java.utils.types.StaffRole;
import main.java.models.Staff;
import main.java.models.BranchManager;
import main.java.models.Branch;


public class StaffAssignmentController {
    private StaffDAO staffDAO;
    private BranchDAO branchDAO;

    public StaffAssignmentController(StaffDAO staffDAO, BranchDAO branchDAO) {
        this.staffDAO = staffDAO;
        this.branchDAO = branchDAO;
    }

    public boolean promoteToManager(String staffID) {
        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);
        if(staff.getRole() == StaffRole.Manager){
            System.out.println("Error: Staff is already Manager");
            return false;
        }
        else{
            staff.setRole(StaffRole.Manager);
            staffDAO.updateElement(oldstaff, staff);
            return true;
        }
    }

    public boolean transferStaff(String staffID, String oldBranch, String newBranch) {
        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);
        if(staff.getBranch() == oldBranch){
            System.out.println("Error: Staff does not belong to" + oldBranch);
            return false;
        }
        else if(staff.getBranch() == newBranch){
            System.out.println("Error: Staff already belongs to" + newBranch);
            return false;
        }
        else{
            staff.setBranch(newBranch);
            staffDAO.updateElement(oldstaff, staff);
            return true;
        }
    }

    public boolean assignManager(String staffID, String branch){
        BranchManager manager = (BranchManager)staffDAO.findElement(staffID);
        Branch branchToAssign = branchDAO.findElement(branch);

        int capacity = branchToAssign.getCapacity();
    }
}
