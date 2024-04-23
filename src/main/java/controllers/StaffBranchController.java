package main.java.controllers;

import main.java.daos.BranchDAO;
import main.java.models.Branch;

public class StaffBranchController {
    private BranchDAO branchDAO;

    public StaffBranchController(BranchDAO branchDAO) {
        this.branchDAO = branchDAO;
    }

    public boolean openBranch(String name, String location, int capacity) {
        if (branchDAO.findElement(name) == null) { // Assuming branches names are unique
            Branch newBranch = new Branch(name, location, capacity);
            branchDAO.addElement(newBranch);
            branchDAO.saveData();
            return true;
        }
        return false;
    }

    public boolean closeBranch(String name) {
        Branch branch = branchDAO.findElement(name);
        if (branch != null) {
            branchDAO.removeElement(name);
            branchDAO.saveData();
            return true;
        }
        return false;
    }
}
