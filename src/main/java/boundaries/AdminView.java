package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Represents the view for administrative actions accessible to the admin role.
 * This view is a boundary level object that provides options for administrators to perform various actions such as 
 * managing staff accounts, displaying staff lists, managing branches, managing staff, managing payment methods, and logging out.
 * This form is implemented from StaffUserView.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
*/
public class AdminView implements StaffUserView {

    /**
     * Default Constructor for AdminView
     */
    public AdminView(){
    }

    /**
     * Executes and generates a list of administrative actions based on the staff session.
     * The option chosen by admin staff will lead to the various forms - AdminAssignmentForm (1), AdminDisplayForm (2), 
     * AdminBranchForm (3), AdminManagementForm (4), AdminPaymentForm (5), with option 6 being logout.
     * It checks if user input is within options 1-6.
     *
     * @param session the staff session object
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
                               "|-------------------------Admin Actions------------------------------|\n" +
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
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-6)");
                continue;
            }

            if (choice < 1 || choice > 6){
                System.out.println("Invalid Input. Please enter (1-6)");
            }
            else if (choice == 6){
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