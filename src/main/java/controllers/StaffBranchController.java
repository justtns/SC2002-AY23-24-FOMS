package main.java.controllers;

import main.java.daos.BranchDAO;
import main.java.models.Branch;

/**
 * The StaffBranchController class manages operations related to branches by staff members.
 * It provides methods to open and close branches.
 * This controller interacts with the BranchDAO to perform CRUD operations on branches.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffBranchController {
    
    /** The BranchDAO instance to interact with branch data. */
    private BranchDAO branchDAO;

    /**
     * Constructs a new StaffBranchController with the specified BranchDAO.
     *
     * @param branchDAO the BranchDAO instance
     */
    public StaffBranchController(BranchDAO branchDAO) {
        this.branchDAO = branchDAO;
    }

    /**
     * Opens a new branch with the given name, location, and capacity.
     *
     * @param name the name of the new branch
     * @param location the location of the new branch
     * @param capacity the capacity of the new branch
     * @return true if the branch was successfully opened, false otherwise
     */
    public boolean openBranch(String name, String location, int capacity) {
        if (branchDAO.findElement(name) == null) { // Assuming branches names are unique
            Branch newBranch = new Branch(name, location, capacity);
            branchDAO.addElement(newBranch);
            branchDAO.saveData();
            return true;
        }
        return false;
    }

    /**
     * Closes the branch with the given name.
     *
     * @param name the name of the branch to close
     * @return true if the branch was successfully closed, false otherwise
     */
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
