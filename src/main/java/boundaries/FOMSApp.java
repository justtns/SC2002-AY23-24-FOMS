package main.java.boundaries;

import java.util.Scanner;
import main.java.utils.types.LoginRole;
import java.util.InputMismatchException;

public class FOMSApp {
    Scanner scanner = new Scanner(System.in);
    private LoginRole loginRole;

    public void execute() {
        System.out.println("----------------------------------------------------------------------\n" +
                           "|----------------Fastfood ordering and management System -------------|\n" +
                           "----------------------------------------------------------------------\n" +
                           "|                      Choose an option:                               |\n" +
                           "|                      1.Customer                                      |\n" +
                           "|                      2.Staff                                         |\n" +
                           "----------------------------------------------------------------------\n")

        enterRole();

        AppDisplay app;
        switch(loginRole) {
            case CUSTOMER:
                app = new CustomerApp();
                app.execute();
                break;
            case STAFF:
                app = new StaffApp();
                app.execute();
                break;
        }
    }

    public void enterRole() {
            int choice = -1;
            while(choice == -1){
                try {
                    choice = Integer.parseInt(scanner.next());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input. Please enter a number.");
                    scanner.nextLine(); // Consume the invalid input
                    continue;
                }
            }
            
            switch (choice) {
                case 1:
                    this.loginRole = loginRole.CUSTOMER;
                    break;
                case 2:
                    this.loginRole = loginRole.STAFF;
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-2)");
                    break;
            }
    }
}
