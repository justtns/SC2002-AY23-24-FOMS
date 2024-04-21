package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.actions.StaffActions;
import main.java.actions.StaffOrderAction;
// import staff actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;


public class StaffView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        StaffActions action; // undone
        System.out.println("User ID:" + session.getStaffUserID());

        action = new StaffOrderAction(); //havent implement
        action.execute(session, scanner);
        ScannerProvider.closeScanner();
    }
}
