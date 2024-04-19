
import java.util.InputMismatchException;

import java.util.Scanner;

public class FOMS {


    static Scanner scanner = new Scanner(System.in);




    public static void showAdminInterface(Admin admin) {
         AdminInterface adminInterface=new AdminInterface(admin);
        boolean loop=true;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Admin Menu---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Staff Management\n" +
                    "                         2.Assign Manager to Baranch\n" +
                    "                         3.Promote a Staff to a Branch manager\n" +
                    "                         4.Transfer a staff/manager among branches\n" +
                    "                         5.Add/Remove payment method\n" +
                    "                         6.Open/Close branch\n" +
                    "                         7.Logout\n"+
                    "                         8.Change Password\n"+
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-7): \n");
            int choice = -1;
            try {

                choice = Integer.parseInt(scanner.next());
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input...");
                continue;
            }

            switch (choice)
            {
                case 1:
                    adminInterface.staffOperations();
                    break;
                case 2:
                    adminInterface.assignManagerToBranch();
                    break;
                case 3:
                    adminInterface.promoteStaffToManager();
                    break;

                case 4:
                    adminInterface.transferStaffOrManagerAmongBranches();
                    break;

                case 5:
                    adminInterface.addRemovePaymentMethods();
                    break;

                case 6:
                    adminInterface.openCloseBranch();
                    break;

                case 7:
                    loop=false;
                    break;
                case 8:
                    adminInterface.changePassword();
                    break;


                default:
                    System.out.println("Invalid Choice...");
                    break;
            }
        }



    }


    /*
    Customer actions:
• Select “customer” between “customer” and “staff” options.
• Select branch.
• Check placed order status using order ID.
• New order:
• Menu Browsing:
• Organized display of menu items.
• Add, edit, delete menu items from the cart.
• Choose either takeaway or dine-in for the order.
• Check out cart.
• Make payment for the order (no need to implement, just
have an option for them to simulate payment).
• Print receipt with order ID.
• Collect food to make the order status change from “Ready
to pickup” to “completed”.
Order Placement:
• Facilitate the selection of items, customization, and confirmation of orders.
• Item, quantity, price calculation.
• Paymen
Place a new order, check the order status using the order ID, and collect
the food.
• Verify that the order status changes from "Ready to pickup" to
"completed.
     */
    private static void showCustomerInterface() {
        CustomerInterface customerInterface=new CustomerInterface();
        System.out.println("Choose a Branch: ");
        if(customerInterface.chooseBranch())
        {

            boolean loop=true;
            while (loop) {
                System.out.println("-------------------------------------------------------------------\n" +
                        "-----------------------------Customer Menu---------------------------\n" +
                        "-------------------------------------------------------------------\n" +
                        "                         Choose an option:\n" +
                        "                         1.View Menu Items\n" +
                        "                         2.Place an Order\n" +
                        "                         3.View Order Status\n" +
                        "                         4.Pickup Order\n" +
                        "                         5.Logout\n" +

                        "---------------------------------------------------------------------\n" +
                        "\n" +
                        "Enter your choice (1-7): \n");
                int choice = -1;
                try {

                    choice = Integer.parseInt(scanner.next());
                } catch (InputMismatchException e) {
                    System.out.println("Invalid Input...");
                    continue;
                }

                switch (choice) {
                    case 1:
                        customerInterface.showMenuItems();
                        break;
                    case 2:
                        customerInterface.placeAnOrder();
                        break;
                    case 3:
                        customerInterface.viewOrderStatus();
                        break;

                    case 4:
                        customerInterface.pickupOrder();
                        break;
                    case 5:
                        loop=false;
                        System.out.println();
                        break;
                }
            }


        }







    }



    private static void showStaffInterface(Staff staff) {
        StaffInterface staffInterface=new StaffInterface(staff);
        boolean loop=true;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Staff Menu---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Display new Orders\n" +
                    "                         2.View Details of an Order\n" +
                    "                         3.Process the order\n" +
                    "                         4.Logout\n" +
                    "                         5.Change Password\n" +
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-5): \n");
            int choice = -1;
            try {

                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input...");
                continue;
            }

            switch (choice) {
                case 1:
                    staffInterface.displayNewOrders();
                    break;

                case 2:
                  staffInterface.viewParticularOrder();
                    break;
                case 3:
                    staffInterface.processOrder();
                    break;
                case 4:
                    loop=false;
                    System.out.println();
                    break;
                case 5:
                    staffInterface.changePassword();
                    break;
            }
        }

    }


    public static void main(String[] args) {
        DataManager.initialize();
        DataManager.saveAllData();

        boolean loop=true;
        while (loop)
        {
            displayMenu();
            int choice=-1;
            try {

                choice = Integer.parseInt(scanner.next());
            }
            catch (InputMismatchException e)
            {
                System.out.println("Invalid Input...");
                continue;
            }

            switch (choice)
            {
                case 1:

                    Staff staff=staffLogin();
                    if(staff!=null && staff.getRole()==Role.Admin) {
                        System.out.println("Login success!");
                        showAdminInterface((Admin) staff);
                    }
                    else
                        System.out.println("Login Failed..");
                    break;
                case 2:

                     staff=staffLogin();
                    if(staff!=null && staff.getRole()==Role.Staff) {
                        System.out.println("Login success!");
                        showStaffInterface(staff);
                    }
                    else
                        System.out.println("Login Failed..");

                    break;
                case 3:
                    staff=staffLogin();
                    if(staff!=null && staff.getRole()==Role.Manager) {
                        System.out.println("Login success!");
                        showManagerInterface((BranchManager) staff);
                    }
                    else
                        System.out.println("Login Failed..");
                    break;
                case 4:
                        showCustomerInterface();
                    break;
                case 5:
                    System.out.println("Exiting....");
                    loop=false;
                    break;
                default:
                    System.out.println("Invalid choice..");




            }


        }

    }


    /*
    Everything a staff is able to do.
• Display the staff list in the branch supervised by the manager.
• Add, edit, or remove menu items, price, and availability. The menu
items and prices might vary among branches.
     */
    private static void showManagerInterface(BranchManager manager) {
        ManagerInterface managerInterface=new ManagerInterface(manager);
        boolean loop=true;
        while (loop) {
            System.out.println("-------------------------------------------------------------------\n" +
                    "-----------------------------Manager Menu---------------------------\n" +
                    "-------------------------------------------------------------------\n" +
                    "                         Choose an option:\n" +
                    "                         1.Display new Orders\n" +
                    "                         2.View Details of an Order\n" +
                    "                         3.Process the order\n" +
                    "                         4.Display The list of staff\n" +
                    "                         5.Add, edit, or remove menu items, price, and availability\n" +
                    "                         6.Logout\n" +
                    "                         7.Change Password\n" +
                    "---------------------------------------------------------------------\n" +
                    "\n" +
                    "Enter your choice (1-5): \n");
            int choice = -1;
            try {

                choice = Integer.parseInt(scanner.next());
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input...");
                continue;
            }

            switch (choice) {
                case 1:
                    managerInterface.displayNewOrders();
                    break;

                case 2:
                    managerInterface.viewParticularOrder();
                    break;
                case 3:
                    managerInterface.processOrder();
                    break;
                case 4:
                    managerInterface.displayStaff();
                    break;
                case 5:
                    managerInterface.menuOptions();
                    break;
                case 6:
                    loop=false;
                    System.out.println();
                    break;
                case 7:
                    managerInterface.changePassword();
                    break;

            }
        }

    }


    public static void displayMenu() {
        System.out.println("-------------------------------------------------------------------------------------------\n" +
                "                  Welcome To Fastfood ordering and management System (FOMS)\n" +
                "-------------------------------------------------------------------------------------------\n" +
                "                  Choose an option:\n" +
                "                  1.Admin Login\n" +
                "                  2.Staff Login\n" +
                "                  3.Branch manager\n" +
                "                  4.Customer\n" +
                "                  5.Exit\n" +
                "-------------------------------------------------------------------------------------------\n" +
                "\n" +
                "Enter your choice (1-4): \n");
    }

    private static Staff staffLogin()
    {
        System.out.println("Enter Login ID: ");
        String id=scanner.next();
        System.out.println("Enter password: ");
        String password=scanner.next();
        Staff staff=DataManager.authenticateUser(id,password);
        return staff;
    }
}
