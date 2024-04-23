package main.java.controllers;

import java.util.List;
import main.java.daos.BranchDAO;
import main.java.models.Branch;

/**
 * The CustomerBranchController class implements the business logic layer operations related to branches for customers.
 * It provides methods to interact with branch data and performs necessary business logic operations.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerBranchController {
    
    /** The data access object (DAO) for branches. */
    private BranchDAO branchDAO;

    /**
     * Constructs a CustomerBranchController object with the specified BranchDAO.
     * 
     * @param branchDAO The BranchDAO object to be used by the controller
     */
    public CustomerBranchController(BranchDAO branchDAO){
        this.branchDAO = branchDAO;
    }

    /**
     * Retrieves a list of all branches.
     * 
     * @return A list of Branch objects representing all branches
     */
    public List<Branch> getBranches(){
        return branchDAO.getElements();
    }

    /**
     * Verifies the existence of a branch with the specified name.
     * 
     * @param branchName The name of the branch to verify
     * @return True if the branch exists, false otherwise
     */
    public Boolean verifyBranch(String branchName){
        if (branchDAO.findElement(branchName) == null){
            return false;
        }
        return true;
    }
}