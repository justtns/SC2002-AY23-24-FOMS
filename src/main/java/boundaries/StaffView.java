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
        int choice;

        System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Staff Actions---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.View Order Actions\n" +
                    "                         2.Logout\n" +                         
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
                    action.execute(session, scanner);, scanner);
                    ScannerProvider.closeScanner();
                    break;
                case 2:
                    loop=false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-2)");
                    break;
                
            }

        
        ScannerProvider.closeScanner();
    }
}
