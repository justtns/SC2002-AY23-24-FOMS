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
                           "----------------------------------------------------------------------\n");

        enterRole(scanner);
        
        boolean loop = true;
        while(loop){
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            this.staffLogin = new StaffLogin();
            
            boolean usernameAuthenticated = staffLogin.enterUsername(username);
            boolean passwordAuthenticated = staffLogin.enterPassword(password);

            if (usernameAuthenticated && passwordAuthenticated) {
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
            System.out.println("Please enter your role (Admin/Manager/Staff): ");
            String roleInput = scanner.nextLine();
            
            switch (roleInput.toLowerCase()) {
                case "admin":
                    this.staffRole = StaffRole.ADMIN;
                    return;
                case "manager":
                    this.staffRole = StaffRole.MANAGER;
                    return;
                case "staff":
                    this.staffRole = StaffRole.STAFF;
                    return;
                default:
                    System.out.println("Invalid role entered.");
                    break;
            }
        }
    }
}
