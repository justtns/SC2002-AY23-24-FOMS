package main.java.boundaries;

import java.util.Scanner;

import main.java.utils.types.StaffRole;
import main.java.utils.loggers.StaffSession;

/**
 * The StaffApp class represents the entry point for staff users.
 * It is a boundary level object that interacts with staff users.
 * This class provides functionality for staff users to log in, choose their role,
 * and access corresponding functionalities based on their role.
 * This App is implemented from AppDisplay interface.
 * 
 * @author SDDA Team 1
 * @version 1.5
 * @since 24-Apr-2024
 */
public class StaffApp implements AppDisplay {

    /**
     * Default Constructor for StaffApp
     */
    public StaffApp(){
    }

    /**
     * The role of the staff (Admin, Staff or Manager)
     */
    private StaffRole staffRole;

    /**
     * Instance of StaffLogin to handle login processes
     */
    private StaffLogin staffLogin;

    /**
     * Session information for the staff user
     */
    private StaffSession staffSession;

    /**
     * Executes the login page display for staff.
     * 
     * This method displays a login portal where staff users can choose to either login (1), or change password (2) 
     * It checks if staff input is valid within 1-2.
     * 
     * @param scanner The scanner object for user input.
     */
    public void execute(Scanner scanner) {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        boolean loop=true;
        int choice;
        while (loop) {
            System.out.println("----------------------------------------------------------------------\n" +
                           "|--------------------------Login Portal------------------------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                      Choose an option:                             |\n" +
                           "|                      1. Login                                      |\n" +
                           "|                      2. Change Password                            |\n" +
                           "----------------------------------------------------------------------\n");
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.next());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter (1-2).");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume the newline character
                    loginProcess(scanner);
                    loop = false;
                    break;
                case 2:
                    scanner.nextLine(); // Consume the newline character
                    changePasswordProcess(scanner);
                    break;
                default:
                    System.out.println("Invalid Input. Please enter (1-2).");
                    break;
            }
        }
    }
    
    /**
     * Executes the login process.
     * This method asks staff for their username, password, and role.
     * It then checks and authenticates the username and password to verify that the details are correct.
     * If authenticated, it will create views according to their role, otherwise it displays an appropriate error message.
     * 
     * @param scanner The scanner object for user input.
     */
    private void loginProcess(Scanner scanner) {
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
                if(!usernameAuthenticated){
                    System.out.println("Username not found.");
                }
                else if(!passwordAuthenticated){
                    System.out.println("Username and password do not match.");
                }                
                System.out.println("Login failed. Please try again.\n");
            }
        }
    }

    /**
     * This method prompts staff to choose their role from the login portal.
     * It validates the staff's input and sets the staffRole attribute accordingly.
     * 
     * @param scanner The scanner object for user input.
     */
    private void enterRole(Scanner scanner) {
        
        while(true){
            System.out.println("Enter role: 1/2/3 \n" +
                                "1. Admin, 2. Staff, 3. Manager");
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
    
    /**
     * This method facilitates the changing of password for all staff members
     * It asks users to enter their username, old and new password.
     * It then prints out a message according to whether the password has been successfully changed or not.
     * 
     * @param scanner The scanner object for user input.
     */
    private void changePasswordProcess(Scanner scanner) {
        System.out.println("Enter username:");
            String username = scanner.nextLine();
            
            System.out.println("Enter old password:");
            String oldPassword = scanner.nextLine();
            System.out.println("Enter new password:");
            String newPassword = scanner.nextLine();

            this.staffLogin = new StaffLogin();

            if(staffLogin.changePassword(username, oldPassword, newPassword)){
                System.out.println("Password changed successfully.");
            }
            else{
                System.out.println("Password cannot be changed.");
            }

        System.out.println("Press enter to return to the Login Menu...");
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}