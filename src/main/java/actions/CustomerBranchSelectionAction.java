package main.java.actions;

import java.util.Scanner;
import main.java.utils.loggers.CustomerSession;
import main.java.boundaries.CustomerBranchSelectionForm;

public class CustomerBranchSelectionAction {
    public CustomerSession execute(CustomerSession session, Scanner scanner){
        CustomerBranchSelectionForm selectionForm = new CustomerBranchSelectionForm(session, scanner);
        return selectionForm.branchSelectionView();
    }
}