package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.actions.ManagerActions;
import main.java.actions.ManagerDisplayAction;
import main.java.actions.ManagerMenuAction;
import main.java.actions.StaffOrderAction;
// import staff actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ManagerView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        ManagerActions action; 
        System.out.println("User ID:" + session.getStaffUserID());
        int choice;

        System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Manager Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Manage Orders\n" +
                    "                         2.Manage Menu\n" +
                    "                         3.Display Staff List\n" +
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
                action = (ManagerActions) new StaffOrderAction();
                action.execute(session, scanner);
                break;
            case 2:
                action = new ManagerMenuAction(); // to implement
                action.execute(session, scanner);
                break;
            case 3:
                action = new ManagerDisplayAction();
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

        ScannerProvider.closeScanner();
    }
}
