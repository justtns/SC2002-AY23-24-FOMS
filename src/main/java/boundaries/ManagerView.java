package main.java.boundaries;

import main.java.utils.loggers.StaffSession;
// import staff actions when done
import main.java.factories.FormFactory;
import main.java.utils.ScannerProvider;
import java.util.Scanner;
import java.util.InputMismatchException;


public class ManagerView implements StaffUserView{
    public void execute(StaffSession session){
        Scanner scanner = ScannerProvider.getScanner();
        FormFactory actionFactory = new FormFactory();
        Form form;
        Boolean loop=true;
        while (loop) {
            System.out.println("User ID:" + session.getStaffUserID());

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
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter (1-4)");
                continue;
            }

            if (choice < 1 |choice > 4){
                System.out.println("Invalid Input. Please enter (1-2)");
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
        ScannerProvider.closeScanner();
    }
}
