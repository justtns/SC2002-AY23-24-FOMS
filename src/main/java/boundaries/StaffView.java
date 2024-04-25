package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;

/**
 * Represents the view for staff actions accessible to the staff role.
 * This view is a boundary level object that provides option for staff to perform the actions of managing orders and logging out.
 * This form is implemented from StaffUserView.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffView implements StaffUserView {

    /**
     * Default Constructor for Staffview
     */
    public StaffView(){
    }
    
    /**
     * Executes and generates a list of staff actions based on staff session.
     * The option chosen by staff will lead to various forms - StaffOrderForm (1), with option 2 being logout.
     * It checks if user input is within options 1-2.
     * 
     * @param session The staff session associated with the user.
     */
    @Override
    public void execute(StaffSession session) {
        Scanner scanner = ScannerProvider.getScanner();
        FormFactory actionFactory = new FormFactory();
        Form form;

        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        Boolean loop = true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());
            System.out.println("----------------------------------------------------------------------\n" +
                               "|-----------------------------Staff Actions--------------------------|\n" +
                               "----------------------------------------------------------------------\n" +
                               "|                   Choose an option:                                |\n" +
                               "|                   1.Manage Orders                                  |\n" +
                               "|                   2.Logout                                         |\n" +
                               "----------------------------------------------------------------------\n" +
                               "\n" +
                               "Enter your choice (1-2): \n");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
            
            if (choice < 1 | choice > 2) {
                System.out.println("Invalid Input. Please enter (1-2)");
            } else if (choice == 2) {
                loop = false;
                System.out.println("Logging out...");
            } else {
                form = actionFactory.getForm(session, scanner, choice);
                form.generateForm();
            }            
        }
    }
}