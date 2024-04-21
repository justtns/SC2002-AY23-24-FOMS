package main.java.boundaries;

import main.java.utils.loggers.CustomerSession;
import main.java.utils.loggers.StaffSession;
import main.java.actions.StaffActions;
import main.java.actions.StaffOrderAction;
import main.java.boundaries.StaffUserView;
// import staff actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ManagerView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        StaffActions action; 
        StaffActions action; // undone
        System.out.println("User ID:" + session.getStaffUserID());
        int choice;

        System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Manager Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.View Order Actions\n" +
                    "                         2.View Menu Actions\n" +
                    "                         3.Display Staff Action\n" +
                    "                         4.Logout\n" +                         
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-4): \n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    action = new StaffOrderAction();
                    action.execute(session, scanner);
                    ScannerProvider.closeScanner();
                    break;
                case 2:
                    action = new ManagerMenuAction(); // to implement
                    action.execute(session, scanner);
                    ScannerProvider.closeScanner();
                    break;
                case 3:
                    action = new ManagerDisplayAction(); // to implement
                    action.execute(session, scanner);
                    ScannerProvider.closeScanner();
                    break;
            }


    }
}
