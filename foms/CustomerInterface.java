
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerInterface {
    Branch branch;
    Scanner scanner = new Scanner(System.in);
    List<Order> orders=new ArrayList<>();

    public boolean chooseBranch() {
        // Display branches for selection
        List<Branch> branches = DataManager.branchStrategy.allBranches;
        for (int i = 0; i < branches.size(); i++) {
            System.out.println((i) + ". " + branches.get(i).getBranchName());
        }
        System.out.print("Enter the branch number: ");
        int branchIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (branchIndex < 0 || branchIndex >= branches.size()) {
            System.out.println("Invalid branch number.");
            return false;
        }
        this.branch = branches.get(branchIndex);
        return true;
    }

    // Display all the menu items in the selected branch
    public void showMenuItems() {
        System.out.println("Menu Items in " + branch.getBranchName() + ":");
        List<MenuItem> menuItems = DataManager.itemStrategy.menuItems;
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }
        // Print table header
        System.out.printf("%-20s | %-15s | %-10s | %-20s | %-10s | %-50s%n",
                "Name", "Category", "Price ($)", "Branch", "Available", "Description");
        System.out.println("------------------------------------------------------------------------------------------");
        // Print menu items
        for (MenuItem item : menuItems) {
            if (item.getBranchName().equals(branch.getBranchName())) {
                System.out.printf("%-20s | %-15s | %-10.2f | %-20s | %-10s | %-50s%n",
                        item.getName(), item.getCategory(), item.getPrice(), item.getBranchName(),
                        item.isAvailable() ? "Yes" : "No", item.getDescription());
            }
        }
    }

    //this function will allow user to place an order
    //user first choose number of item he want to order then it will select that items from the list of items, he can also select the quantity
    //After placing the order user will be direct to payment and he pay the order,• Make payment for the order (no need to implement, just have an option for them to simulate payment).
    //After that The details of order will be dispalyed to user
    // Place an order
    public void placeAnOrder() {

        System.out.println("Placing an Order");
        List<MenuItem> selectedItems = new ArrayList<>();
        boolean continueOrdering = true;
        while (continueOrdering) {
            showMenuItems();
            System.out.print("Enter the name of the item to order (type 'done' to finish): ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {

                break;
            }
            MenuItem selectedItem = findMenuItemByName(itemName,branch.getBranchName());
            if (selectedItem == null || !selectedItem.getBranchName().equals(branch.getBranchName())) {
                System.out.println("Invalid item or not available in this branch. Please try again.");
                continue;
            }
            selectedItems.add(selectedItem);
        }
        if (selectedItems.isEmpty()) {
            System.out.println("No items selected. Order canceled.");
            return;
        }
        System.out.println("You have selected the following items:");
        for (MenuItem item : selectedItems) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.println("1.Take Away\t2.Dine-in");
        int method=scanner.nextInt();
        boolean m=method==2?true:false;
        System.out.println("Choose a Payment Method:");
        for (int i=0;i<DataManager.paymentStrategy.paymentMethods.size();i++)
        {
            System.out.println(i+": "+DataManager.paymentStrategy.paymentMethods.get(i).getMethod());
        }
        System.out.println("Enter your choice: ");
        scanner.next();
        // Simulate payment
        System.out.println("Payment successful. Thank you for your order!");
        // Create and display the order details
        Order order = new Order(selectedItems, m); // Assuming it's not a dine-in order
        System.out.println("Order Details:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Total Price: $" + order.calculateTotalPrice());
        if(m)
        System.out.println("Dine-in");
        else
            System.out.println("Take-Away");
        System.out.println("Items:");
        for (MenuItem item : order.getItems()) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        //add order to order List
        DataManager.orderStrategy.addItem(order);
        orders.add(order);
        DataManager.saveAllData();
    }

    // Method to find a menu item by its name
    private MenuItem findMenuItemByName(String name,String branch) {
        for (MenuItem item : DataManager.itemStrategy.menuItems) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranchName().equalsIgnoreCase(branch)) {
                return item;
            }
        }
        return null;
    }


    //This method will promote the user to enter order Id and then display details of order from orderlist
    // Method to find an order by its ID
    // View order status
    public void viewOrderStatus() {
        System.out.print("Enter the order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Order order = findOrderById(orderId);
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Total Price: $" + order.calculateTotalPrice());

        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }
    private Order findOrderById(int orderId) {
        for (Order order : DataManager.orderStrategy.orderList) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public void pickupOrder() {
        System.out.print("Enter the order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Order order = findOrderById(orderId);
        if (order != null) {
            System.out.println("Order Details:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Order Status: " + order.getOrderStatus());
            System.out.println("Total Price: $" + order.calculateTotalPrice());
            if(order.getOrderStatus()== Order.OrderStatus.Ready) {
                System.out.println("1.Pickup\t2.Not yet");
                int c = scanner.nextInt();
                if (c == 1) {
                    order.setOrderStatus(Order.OrderStatus.Completed);
                    order.setCompleted(true);
                    System.out.println("The order has been Completed..");
                    DataManager.saveAllData();
                }
            }
            else
            {
                System.out.println("You cannot pickup,Order is not Ready Yet..");
            }



        } else {
            System.out.println("Order not found with ID: " + orderId);
        }
    }
}
