package main.java.boundaries;

import java.util.Scanner;
import main.java.utils.ScannerProvider;

import main.java.utils.types.StaffRole;
import main.java.utils.loggers.StaffSession;

public class StaffApp implements AppDisplay {
    Scanner scanner = ScannerProvider.getScanner();
    private StaffRole staffRole;
    private StaffLogin staffLogin;
    private StaffSession staffSession;

    public void execute() {
        System.out.println("----------------------------------------------------------------------\n" +
                           "|--------------------------Login Portal------------------------------|\n" +
                           "----------------------------------------------------------------------\n");

        enterRole();

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
                    break;
                case MANAGER:
                    view = new ManagerView();
                    view.execute(staffSession);
                    break;
                case STAFF:
                    view = new StaffView();
                    view.execute(staffSession);
                    break;
            }
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }

    public void enterRole() {
        boolean loop = true;

        while(loop){
            System.out.println("Please enter your role (Admin/Manager/Staff): ");
            String roleInput = scanner.nextLine();
            
            switch (roleInput.toLowerCase()) {
                case "admin":
                    this.staffRole = StaffRole.ADMIN;
                    loop = false;
                    break;
                case "manager":
                    this.staffRole = StaffRole.MANAGER;
                    loop = false;
                    break;
                case "staff":
                    this.staffRole = StaffRole.STAFF;
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid role entered.");
                    return;
            }
        }
    }
}
