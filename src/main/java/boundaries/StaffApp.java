package main.java.boundaries;

import java.util.Scanner;

import main.java.utils.types.StaffRole;
import main.java.utils.loggers.StaffSession;

public class StaffApp implements AppDisplay {
    private StaffRole staffRole;
    private StaffLogin staffLogin;
    private StaffSession staffSession;

    public void execute(Scanner scanner) {
        
        System.out.println("----------------------------------------------------------------------\n" +
                           "|--------------------------Login Portal------------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                      Choose an option:                             |\n" +
                           "|                      1. Admin                                      |\n" +
                           "|                      2. Staff                                      |\n" +
                           "|                      3. Manager                                    |\n" +
                           "----------------------------------------------------------------------\n");
        
        boolean loop = true;
        while(loop){
            enterRole(scanner);

            System.out.println("Enter username:");
            String username = scanner.nextLine();
            
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            this.staffLogin = new StaffLogin();
            
            boolean usernameAuthenticated = staffLogin.enterUsername(username);
            boolean passwordAuthenticated = staffLogin.enterPassword(password);

            if (usernameAuthenticated && passwordAuthenticated) {
                if(!staffLogin.verifyRole(staffRole)){
                    System.out.println("User: " + username + " does not belong to " + staffRole + " role. Please try again.");
                    continue;
                }
                
                System.out.println("Login successful for role: " + staffRole + " and user: " + username);
                this.staffSession = new StaffSession(username, staffRole);
                StaffUserView view;
                switch(staffRole){
                    case ADMIN:
                        view = new AdminView();
                        view.execute(staffSession);
                        loop = false;
                        break;
                    case MANAGER:
                        view = new ManagerView();
                        view.execute(staffSession);
                        loop = false;
                        break;
                    case STAFF:
                        view = new StaffView();
                        view.execute(staffSession);
                        loop = false;
                        break;
                }
            } else {
                System.out.println("Login failed. Please try again.");
            }
        }
    }

    public void enterRole(Scanner scanner) {
        
        while(true){
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
                scanner.nextLine(); // Consume the invalid input
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-3)");
                continue;
            }
            
            switch (choice) {
                case 1:
                    this.staffRole = StaffRole.ADMIN;
                    return;
                case 2:
                    this.staffRole = StaffRole.STAFF;
                    return;
                case 3:
                    this.staffRole = StaffRole.MANAGER;
                    return;
                default:
                    System.out.println("Invalid Input. Please enter (1-3).");
                    break;
            }
        }
    }
}
