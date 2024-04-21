package main.java.controllers;

import java.util.List;
import main.java.daos.BranchDAO;
import main.java.models.Branch;

public class CustomerBranchController {
    private BranchDAO branchDAO;

    public CustomerBranchController(BranchDAO branchDAO){
        this.branchDAO = branchDAO;
    }

    public List<Branch> getBranches(){
        return branchDAO.getElements();
    }

    public Boolean verifyBranch(String branchName){
        if (branchDAO.findElement(branchName) == null){
            return false;
        }
        return true;
    }
}
