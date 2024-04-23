package main.java.boundaries;

import java.util.Scanner;
import main.java.utils.types.LoginRole;
import java.util.InputMismatchException;

public class FOMSApp {
    Scanner scanner = new Scanner(System.in);
    private LoginRole loginRole;

    public void execute() {
        System.out.println(
        "        ________  ______   __       __   ______  \n" +
        "       /        |/      \\ /  \\     /  | /      \\ \n" +
        "       $$$$$$$$//$$$$$$  |$$  \\   /$$ |/$$$$$$  |\n" +
        "       $$ |__   $$ |  $$ |$$$  \\ /$$$ |$$ \\__$$/ \n" +
        "       $$    |  $$ |  $$ |$$$$  /$$$$ |$$      \\ \n" +
        "       $$$$$/   $$ |  $$ |$$ $$ $$/$$ | $$$$$$  |\n" +
        "       $$ |     $$ \\__$$ |$$ |$$$/ $$ |/  \\__$$ |\n" +
        "       $$ |     $$    $$/ $$ | $/  $$ |$$    $$/ \n" +
        "       $$/       $$$$$$/  $$/      $$/  $$$$$$/  "
        );
        System.out.println("------------------------------------------------------------------------\n" +
                           "|----------------Fastfood ordering and management System --------------|\n" +
                           "------------------------------------------------------------------------\n" +
                           "|                      Choose an option:                               |\n" +
                           "|                      1. Customer                                     |\n" +
                           "|                      2. Staff                                        |\n" +
                           "|                      3. Exit                                         |\n" +
                           "------------------------------------------------------------------------\n");
        boolean loop=true;
        int choice;
        while(loop){
            choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }
            if (choice == 3){
                System.out.println("Exiting System...");
                loop = false;
                break;
            }
            else{
                enterRole(choice);
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
        }

    }

    public void enterRole(int choice) {
            switch (choice) {
                case 1:
                    this.loginRole = LoginRole.CUSTOMER;
                    break;
                case 2:
                    this.loginRole = LoginRole.STAFF;
                    break;
                default:
                    System.out.println("Invalid Key! Enter your choice (1-2)");
                    break;
            }
    }
}
