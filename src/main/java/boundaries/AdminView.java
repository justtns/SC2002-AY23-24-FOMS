package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
// import admin actions when done
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class AdminView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = ScannerProvider.getScanner();
        FormFactory actionFactory = new FormFactory();
        Form form;
        Boolean loop=true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());

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

            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-6)");
                continue;
            }

            if (choice < 1 |choice > 6){
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

        ScannerProvider.closeScanner();
    }
}