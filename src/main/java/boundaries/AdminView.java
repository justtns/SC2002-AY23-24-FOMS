package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.actions.AdminActions;
// import admin actions when done
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class AdminView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = new Scanner(System.in);
        AdminActions action; 
        System.out.println("User ID:" + session.getStaffUserID());
        int choice;

        System.out.println("----------------------------------------------------------------------\n" +
                           "|-----------------------------Admin Actions--------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                   Choose an option:                                |\n" +
                           "|                   1.Add, Edit, or Remove Staff Accounts            |\n" +
                           "|                   2.Display Staff List                             |\n" +
                           "|                   3.Manage Branch                                  |\n" +
                           "|                   4.Manage Staff                                   |\n" +
                           "|                   5.Manage Payment Methods                         |\n" +
                           "|                   6.Logout                                         |\n" +                           
                           "----------------------------------------------------------------------\n" +
                           "\n" +
                           "Enter your choice (1-6): \n");
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
                action = // to implement
                action.execute(session, scanner);
                break;
            case 2:
                action = // to implement
                action.execute(session, scanner);
                break;
            case 3:
                action = // to implement
                action.execute(session, scanner);
                break;
            case 4:
                action = new AdminAssignmentAction();
                action.execute(session, scanner);
                break;
            case 5:
                action = new AdminPaymentAction();
                action.execute(session, scanner);
                break;
            case 6:
                loop=false;
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid Input. Please enter (1-7)");
                break;
        }

        ScannerProvider.closeScanner();
    }
}