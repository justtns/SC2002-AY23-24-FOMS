import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static BranchManagementStrategy branchStrategy=new BranchManagementStrategy();
    static StaffManagementStrategy staffStrategy=new StaffManagementStrategy();
    static OrdersManagementStrategy orderStrategy=new OrdersManagementStrategy();
    static MenuItemStrategy itemStrategy=new MenuItemStrategy();
    static PaymentMethodStrategy paymentStrategy =new PaymentMethodStrategy();



    public static void initialize() {
        readAllStaff();
        readBranches();
        readMenuItems();
        updateBranchesWithMenuItems();
        updateBranchesWithStaff();
        populatePaymentMethods();
        readOrders();
       Order.count=orderStrategy.orderList.get(orderStrategy.orderList.size()-1).getOrderId()+1;
    }

    public static void saveAllData() {
        saveAllStaff();
        saveMenuItems();
        saveAllBranches();
        writeOrders();
    }

    // An item is considered a duplicate if the name and branch name are the same.
    public boolean addItem(MenuItem menuItem) {
        for (MenuItem existingItem : itemStrategy.menuItems) {
            if (existingItem.getName().equalsIgnoreCase(menuItem.getName()) &&
                    existingItem.getBranchName().equalsIgnoreCase(menuItem.getBranchName())) {
                return false; // Item already exists, not added
            }
        }
        itemStrategy.addItem(menuItem);
        return true; // Item added successfully
    }

    // Remove an item from the list
    public boolean removeMenuItem(String itemName, String branchName) {
        for (MenuItem item : itemStrategy.menuItems) {
            if (item.getName().equalsIgnoreCase(itemName) &&
                    item.getBranchName().equalsIgnoreCase(branchName)) {
                itemStrategy.removeItem(item.getName());
                return true; // Item removed successfully
            }
        }
        return false;
        // Item not found
    }

    // Find an item
    public MenuItem findItem(String itemName, String branchName) {
        for (MenuItem item : itemStrategy.menuItems) {
            if (item.getName().equalsIgnoreCase(itemName) &&
                    item.getBranchName().equalsIgnoreCase(branchName)) {
                return item; // Item found
            }
        }
        return null; // Item not found
    }


    private static void readAllStaff() {
        String csvFile = "staff_list.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();//ignore the first line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                String name = data[0];
                String id=data[1];
                String password = data[2];
                Role role;
                switch (data[3]) {
                    case "S":
                        role = Role.Staff;
                        break;
                    case "M":
                        role = Role.Manager;
                        break;
                    case "A":
                        role = Role.Admin;
                        break;
                    default:
                        role = Role.Staff;
                        break;
                }
                String gender = data[4];
                int age = Integer.parseInt(data[5]);
                String branch = data[6];
                switch (role) {
                    case Staff:
                        staffStrategy.allStaff.add(new Staff(name,id, password, role, gender, age, branch));
                        break;
                    case Manager:
                        staffStrategy.allStaff.add(new BranchManager(name,id, password, gender, age, branch));
                        break;
                    case Admin:
                        staffStrategy.allStaff.add(new Admin(name,id, password, gender, age));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }

    private static void populatePaymentMethods() {
        paymentStrategy.addItem(new PaymentMethod("Card"));
        paymentStrategy.addItem(new PaymentMethod("Pay Pal"));

    }

    private static void populateOrders()
    {

        orderStrategy.addItem(new Order(itemStrategy.menuItems,true));
    }


    private static void saveAllStaff() {
        String csvFile = "staff_list.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            // Write header
            bw.write("Name,ID,Password,Role,Gender,Age,Branch\n");

            // Write staff data
            for (Staff staff : staffStrategy.allStaff) {
                bw.write(staff.getName() + "," + staff.getLoginID() + "," + staff.getPassword() + ","
                        + staff.getRole().toString().charAt(0) + "," + staff.getGender() + "," + staff.getAge() + ","
                        + staff.getBranch() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveAllBranches() {
        String csvFile = "branch_list.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            // Write header
            bw.write("Name,Location,Staff Quota\n");

            // Write branch data
            for (Branch branch : branchStrategy.allBranches) {
                bw.write(branch.getBranchName() + "," + branch.getLocation() + "," + branch.getTotalStaff() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveMenuItems() {
        String csvFile = "menu_list.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            // Write header
            bw.write("Name,Price,Branch,Category,Description\n");

            // Write menu item data
            for (MenuItem menuItem : itemStrategy.menuItems) {
                bw.write(menuItem.getName() + "," + menuItem.getPrice() + "," + menuItem.getBranchName() + ","
                        + menuItem.getCategory()+","+menuItem.getDescription() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static void readMenuItems() {
        String csvFile = "menu_list.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignore the first line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                String name = data[0];
                double price = Double.parseDouble(data[1]);
                String branch = data[2];
                String category = data[3];
                String description=data[4];
                itemStrategy.addItem(new MenuItem(name, category, price, branch, true,description)); // Assuming all menu items are available initially
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readBranches() {
        String csvFile = "branch_list.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Ignore the first line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                String name = data[0];
                String location = data[1];
                int staffQuota = Integer.parseInt(data[2]);
                branchStrategy.allBranches.add(new Branch(name, location, staffQuota));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void readOrders()
    {
        try {
            BufferedReader br=new BufferedReader(new FileReader("orders_list.csv"));

                    String line=br.readLine();
            while ((line=br.readLine())!=null)
            {
                String[] data = line.split(",");
                int orderId = Integer.parseInt(data[0]);

                Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(data[1]); // Parse order status from string to enum

                boolean isDine = Boolean.parseBoolean(data[2]);
                boolean isCompleted = Boolean.parseBoolean(data[3]);

                // Create and add the order to the order list
                Order order = new Order(orderId, orderStatus, isDine, isCompleted);
                orderStrategy.addItem(order);
            }

            br.close();

            //now read menu items;
            br=new BufferedReader(new FileReader("order_items.csv"));
            int orderId=-1;

            while ((line= br.readLine())!=null)
            {
                if(line.contains("orderid"))
                {
                    String parts[]=line.split(":");
                    orderId=Integer.parseInt(parts[1]);

                }
                else {

                    String[] data = line.split(",");
                    String name = data[0];
                    double price = Double.parseDouble(data[1]);
                    String branch = data[2];
                    String category = data[3];
                    String description=data[4];
                    MenuItem item=new MenuItem(name, category, price, branch, true,description);
                    setItemToOrder(orderId,item);

                }



            }

            br.close();


        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private static void setItemToOrder(int orderId, MenuItem item) {
        for (Order order: orderStrategy.orderList)
        {
            if(order.getOrderId()==orderId) {
                order.addItem(item);
                break;
            }
        }
    }

    private static void writeOrders() {
        try (BufferedWriter bwOrders = new BufferedWriter(new FileWriter("orders_list.csv"));
             BufferedWriter bwOrderItems = new BufferedWriter(new FileWriter("order_items.csv"))) {

            // Write header for orders_list.csv
            bwOrders.write("orderId,orderStatus,isDineIn,isCompleted\n");

            for (Order order : orderStrategy.orderList) {
                // Write order details to orders_list.csv
                bwOrders.write(order.getOrderId() + "," + order.getOrderStatus() + "," + order.isDineIn() + ","
                        + order.isCompleted() + "\n");

                // Write order ID and associated menu items to order_items.csv
                bwOrderItems.write("orderid:" + order.getOrderId() + "\n");
                for (MenuItem item : order.getItems()) {
                    bwOrderItems.write(item.getName() + "," + item.getPrice() + "," + item.getBranchName() + ","
                            + item.getCategory() + "," + item.getDescription() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private static void updateBranchesWithMenuItems() {
        for (Branch branch : branchStrategy.allBranches) {
            for (MenuItem menuItem : itemStrategy.menuItems) {
                if (menuItem.getBranchName().equalsIgnoreCase(branch.getBranchName())) {
                    branch.addMenuItem(menuItem);
                }
            }
        }
    }

    // This function assigns staff members from the staff list to each branch in the branch list
    private static void updateBranchesWithStaff() {
        for (Branch branch : branchStrategy.allBranches) {
            List<Staff> staffInBranch = new ArrayList<>();
            for (Staff staff : staffStrategy.allStaff) {
                if (staff.getBranch().equalsIgnoreCase(branch.getBranchName())) {
                    staffInBranch.add(staff);
                }
            }
            branch.setStaffList(staffInBranch);
        }
    }

    public static Staff authenticateUser(String id, String password) {

        for (Staff staff : staffStrategy.allStaff) {
            if (staff.getLoginID().equalsIgnoreCase(id) && staff.getPassword().equals(password))

                return staff;
        }
        return null;
    }


}
