package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;

/**
 * The ManagerView class represents the view for a manager user.
 * This view is a boundary level object that provides options for for a manager user to execute actions such as managing orders,
 * managing the menu, displaying staff lists, and logging out.
 * This form is implemented from StaffUserView.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class ManagerView implements StaffUserView {

    /**
     * Executes and generates a list of managers actions based on the staff session.
     * The option chosen by manager will lead to the various forms - StaffOrderForm (1), ManagerMenuForm (2),
     * ManagerDisplayForm (3), with option 4 being logout.
     * It checks if manager input is within options 1-4.
     * 
     * @param session The staff session object
     */
    @Override
    public void execute(StaffSession session){
        Scanner scanner = ScannerProvider.getScanner();
        FormFactory actionFactory = new FormFactory();
        Form form;
        
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        Boolean loop=true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------Manager Actions------------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Manage Orders                                  |\n" +
                               "|                   2.Manage Menu                                    |\n" +
                               "|                   3.Display Staff List                             |\n" +
                               "|                   4.Logout                                         |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-4): \n");              
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-4)");
                continue;
            }

            if (choice < 1 | choice > 4){
                System.out.println("Invalid Input. Please enter (1-4)");
            }
            else if (choice == 4){
                loop=false;
                System.out.println("Logging out...");
            }
            else{
                form = actionFactory.getForm(session, scanner, choice);
                form.generateForm();
            } 
        }
    }
}