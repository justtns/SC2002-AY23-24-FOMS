package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import main.java.boundaries.StaffUserView;
// import staff actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class StaffView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        StaffActions action; // undone
        System.out.println("User ID:" + session.getUserId());

        action = new StaffOrderAction(); //havent implement
        action.execute(session, scanner);
        ScannerProvider.closeScanner();
    }
}
