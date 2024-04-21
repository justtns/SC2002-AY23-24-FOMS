package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import main.java.utils.loggers.StaffSession;
import main.java.actions.StaffActions;
import main.java.boundaries.StaffUserView;
// import staff actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ManagerView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        StaffActions action; 

        Boolean loop=true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());
            System.out.println("-------------------------------------------------------------------\n" +
            "-----------------------------Manager Actions---------------------------\n" +
            "-------------------------------------------------------------------\n" +
            "                         Choose an option:\n" +
            "                         1.View menu and order\n" +
            "                         2.Make Payment\n" +
            "                         3.Check on my order\n" +
            "                         4.Logout\n" +

            "---------------------------------------------------------------------\n" +
            "\n" +
            "Enter your choice (1-4): \n");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-4)");
                continue;
            }
            switch (choice) {
                case 1:
                    action = new CustomerOrderingAction();
                    action.execute(session, scanner);
                    break;
                case 2:
                    action = new CustomerPaymentAction();
                    action.execute(session, scanner);
                    break;
                case 3:
                    action = new CustomerPostOrderAction();
                    action.execute(session, scanner);
                    break;
                case 4:
                    loop=false;
                    System.out.println("Logging out...");
                    break;
                
            
                default:
                System.out.println("Invalid Input. Please enter (1-4)");
                    break;
            }
        }
        ScannerProvider.closeScanner();
    }
}
