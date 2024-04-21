package main.java.boundaries;

import main.java.controllers.StaffAuthenticationController;
import main.java.daos.StaffDAO;
import main.java.models.StaffLogin;
import main.java.utils.types.LoginRole;
import main.java.utils.types.StaffRole;
import main.java.session.StaffSession;
import main.java.utils.ScannerProvider;

public class StaffApp implements AppDisplay {
    Scanner scanner = new Scanner(System.in);
    private StaffRole loginRole;
    private StaffRole staffRole;
    private StaffLogin staffLogin;
    private StaffDAO staffDAO = new StaffDAO();
    private StaffAuthenticationController authController;

    @Override
    public void enterRole() {
        System.out.println("Please enter your role (Admin/Manager/Staff): ");
        String roleInput = scanner.nextLine();
        
        switch (roleInput.toLowerCase()) {
            case "admin":
                this.staffRole = StaffRole.Admin;
                break;
            case "manager":
                this.staffRole = StaffRole.Manager;
                break;
            case "staff":
                this.staffRole = StaffRole.Staff;
                break;
            default:
                System.out.println("Invalid role entered.");
                return;
        }
    }

    public void executeLogin() {
        System.out.println("Welcome to the Staff Login System");

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
            switch(staffRole){
                case "Admin":
                    form = new AdminForms();
                    form.execute(staffSession);
                    break;
                case "Manager":
                    form = new ManagerForms();
                    form.execute(staffSession);
                    break;
                case "Staff":
                    form = new StaffForms();
                    form.execute(staffSession);
                    break;
            }
        } else {
            System.out.println("Login failed. Please try again.");
        }
    }
}
