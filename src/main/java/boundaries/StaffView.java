package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class StaffView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = ScannerProvider.getScanner();
        FormFactory actionFactory = new FormFactory();
        Form form;
        Boolean loop=true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());

            System.out.println("-------------------------------------------------------------------\n" +
                        "-----------------------------Staff Actions---------------------------\n" +
                        "-------------------------------------------------------------------\n" +
                        "                         Choose an option:\n" +
                        "                         1.Manage Orders\n" +
                        "                         2.Logout\n" +                         
                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Enter your choice (1-2): \n");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-2)");
                continue;
            }
            
            if (choice < 1 |choice > 2){
                System.out.println("Invalid Input. Please enter (1-2)");
            }
            else if (choice == 2){
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
