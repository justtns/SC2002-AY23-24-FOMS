package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.utils.types.StaffRole;
import main.java.models.Staff;
import java.util.List;
import java.util.stream.Collectors;


public class StaffAssignmentController {
    private StaffDAO staffDAO;

    public StaffAssignmentController(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

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

    public boolean transferStaff(String staffID, String oldBranch, String newBranch) {
        Staff oldstaff = staffDAO.findElement(staffID);
        Staff staff = staffDAO.findElement(staffID);
        if(staff == null){
            System.out.println("Error: Staff is not found.");
            return false;
        }
        else if(staff.getBranch() != oldBranch){
            System.out.println("Error: Staff does not belong to" + oldBranch);
            return false;
        }
        else if(staff.getBranch() == newBranch){
            System.out.println("Error: Staff already belongs to" + newBranch);
            return false;
        }
        else{
            StaffRole role = staff.getRole();
            switch (role) {
                case STAFF:
                    staff.setBranch(newBranch);
                    staffDAO.updateElement(oldstaff, staff);
                    staffDAO.saveData();
                    return true;
            
                case MANAGER:
                    return assignManager(staffID, newBranch);

                case ADMIN:
                    return false;

                default:
                    return false;
            }
        }
    }

    public boolean assignManager(String staffID, String branch){
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
    private boolean calculateManagerQuota(String branch) {
        // Count number of Staff
        List<Staff> staffInBranch = staffDAO.getElements().stream()
                .filter(s -> s.getBranch().equals(branch) && s.getRole().equals(StaffRole.STAFF))
                .collect(Collectors.toList());
        int staffCount = staffInBranch.size();

        // Count number of Managers
        List<Staff> managersInBranch = staffDAO.getElements().stream()
                .filter(s -> s.getBranch().equals(branch) && s.getRole().equals(StaffRole.MANAGER))
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
