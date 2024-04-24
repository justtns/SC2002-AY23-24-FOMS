package main.java.boundaries;

import java.util.Scanner;

import main.java.utils.ScannerProvider;
import main.java.utils.types.LoginRole;

public class FOMSApp {
    Scanner scanner = ScannerProvider.getScanner();
    private LoginRole loginRole;

    public void execute() {
        boolean loop=true;
        int choice;
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
        while(loop){
            choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
                continue;
            }
            if (choice == 3){
                System.out.println("Exiting System...");
                loop = false;
                break;
            }
            if (choice == 1 | choice == 2){
                enterRole(choice);
                AppDisplay app;
                switch(loginRole) {
                    case CUSTOMER:
                        app = new CustomerApp();
                        app.execute(scanner);
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
                        break;
                    case STAFF:
                        app = new StaffApp();
                        app.execute(scanner);
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
                        break;
                }
            }
            else{
                System.out.println("Invalid Input. Please enter a number (1-3).");
            }
        }
        ScannerProvider.closeScanner();
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
