package main.java.controllers;

import main.java.daos.StaffDAO;
import main.java.utils.types.StaffRole;
import main.java.models.Staff;

public class StaffManagementController {
    private StaffDAO staffDAO;

    public StaffManagementController(StaffDAO staffDAO){
        this.staffDAO = staffDAO;
    }

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