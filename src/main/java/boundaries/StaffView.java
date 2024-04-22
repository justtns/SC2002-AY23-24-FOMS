package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.actions.StaffActions;
import main.java.actions.StaffOrderAction;
import main.java.utils.ScannerProvider;
import java.util.Scanner;


public class StaffView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        StaffActions action;
        System.out.println("User ID:" + session.getStaffUserID());

        action = new StaffOrderAction();
        action.execute(session, scanner);
        ScannerProvider.closeScanner();
    }
}
