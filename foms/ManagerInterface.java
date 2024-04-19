import java.util.List;
import java.util.Scanner;

public class ManagerInterface extends StaffInterface {

    public ManagerInterface(Staff currentManager) {
        super(currentManager);
    }

    //  Display the staff list in the branch supervised by the manager.
    // All the data of staff is in DataManager.staffStrategy.allStaff and all data of orders is in DataManager.orderList
    public void displayStaff() {
        List<Staff> staffList = DataManager.staffStrategy.allStaff;
        BranchManager manager = (BranchManager) currentStaff;
        String branchName = manager.getBranch();

        System.out.println("Staff List in Branch " + branchName + ":");
        System.out.printf("%-20s | %-10s | %-5s | %-6s%n", "Name", "Role", "Age", "Gender");
        System.out.println("------------------------------------------------------");
        boolean foundStaff = false;
        for (Staff staff : staffList) {
            if (staff.getBranch().equals(branchName)) {
                foundStaff = true;
                System.out.printf("%-20s | %-10s | %-5d | %-6s%n",
                        staff.getName(), staff.getRole(), staff.getAge(), staff.getGender());
            }
        }
        if (!foundStaff) {
            System.out.println("No staff found in this branch.");
        }
    }

    /*

- Add a new menu item with a unique name, price, description, and category
- Update the price, description, and availability of an existing menu item
- Remove an existing menu item
The Menu items are stored in DataManager.menuItems
 */
    public void menuOptions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Menu Options:");
        System.out.println("1. Add a new menu item");
        System.out.println("2. Update an existing menu item");
        System.out.println("3. Remove an existing menu item");
        System.out.print("Enter your choice (1-3): ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addMenuItem();
                break;
            case 2:
                updateMenuItem();
                break;
            case 3:
                removeMenuItem();
                break;
            default:
                System.out.println("Invalid choice.");
        }
        DataManager.saveAllData();
    }

    // Method to add a new menu item
    private void addMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the new menu item:");
        String name = scanner.nextLine();
        // Check if the menu item already exists
        for (MenuItem item : DataManager.itemStrategy.menuItems) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranchName().equalsIgnoreCase(currentStaff.getBranch())) {
                System.out.println("Menu item with the same name already exists.");
                return;
            }
        }
        System.out.println("Enter the category of the new menu item:");
        String category = scanner.nextLine();
        System.out.println("Enter the price of the new menu item:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter the description of the new menu item:");
        String description = scanner.nextLine();

        String branchName = currentStaff.getBranch();
        // Add the new menu item to DataManager.menuItems
        MenuItem newItem = new MenuItem(name, category, price, branchName, true, description);
        DataManager.itemStrategy.addItem(newItem);
        System.out.println("New menu item added successfully.");
    }


    // Method to update an existing menu item
    private void updateMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the menu item to update:");
        String name = scanner.nextLine();
        MenuItem menuItem = findMenuItemByName(name);
        if (menuItem != null) {
            System.out.println("Enter the new price of the menu item:");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.println("Enter the new description of the menu item:");
            String description = scanner.nextLine();
            // Update the menu item
            menuItem.setPrice(price);
            menuItem.setDescription(description);
            System.out.println("Menu item updated successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    // Method to remove an existing menu item
    private void removeMenuItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the menu item to remove:");
        String name = scanner.nextLine();
        MenuItem menuItem = findMenuItemByName(name);
        if (menuItem != null) {
            // Remove the menu item
            DataManager.itemStrategy.removeItem(menuItem.getName());
            System.out.println("Menu item removed successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    // Method to find a menu item by its name
    private MenuItem findMenuItemByName(String name) {
        for (MenuItem item : DataManager.itemStrategy.menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
}

}
