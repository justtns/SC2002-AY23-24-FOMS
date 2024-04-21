package main.java.applications;

import main.java.utils.loggers.CustomerSession;
import main.java.boundaries.CustomerBranchSelectionForm;

public class CustomerBranchSelectionAction {
    public CustomerSession execute(CustomerSession session){
        CustomerBranchSelectionForm selectionForm = new CustomerBranchSelectionForm(session);
        return selectionForm.branchSelectionView();
    }
}