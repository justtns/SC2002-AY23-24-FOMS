import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class StaffInterface {
    protected Staff currentStaff;
    private Scanner scanner=new Scanner(System.in);

    public StaffInterface(Staff currentStaff) {
        this.currentStaff = currentStaff;
    }

    /*
    This function will display new Orders. The order is considered new if it has status=Ready.
     */
    public void displayNewOrders() {
        List<Order> orders = DataManager.orderStrategy.orderList;
        System.out.println("New Orders:");
        boolean foundNewOrders = false;
        for (Order order : orders) {
            if (order.getOrderStatus() == Order.OrderStatus.New) {
                foundNewOrders = true;
                System.out.printf("Order ID: %d%n", order.getOrderId());
                System.out.println("Items:");
                for (MenuItem item : order.getItems()) {
                    System.out.printf("- %s: $%.2f%n", item.getName(), item.getPrice());
                }
                System.out.printf("Total Price: $%.2f%n", order.calculateTotalPrice());
                System.out.printf("Dine-in: %s%n", order.isDineIn() ? "Yes" : "No");
                System.out.println("------------------");
            }
        }
        if (!foundNewOrders) {
            System.out.println("No new orders.");
        }
    }

    //View and order by ID
    public void viewParticularOrder() {
        List<Order> orders = DataManager.orderStrategy.orderList;
        System.out.println("Enter Order ID:");
        int id=-1;

        try {
            id = Integer.parseInt(scanner.next());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid OrderID..");
            return;
        }
        boolean orderFound = false;
        for (Order order : orders) {
            if (order.getOrderId() ==id) {
                orderFound = true;
                System.out.printf("Order ID: %d%n", order.getOrderId());
                System.out.println("Items:");
                for (MenuItem item : order.getItems()) {
                    System.out.printf("- %s: $%.2f%n", item.getName(), item.getPrice());
                }
                System.out.printf("Total Price: $%.2f%n", order.calculateTotalPrice());
                System.out.printf("Dine-in: %s%n", order.isDineIn() ? "Yes" : "No");
                System.out.println("------------------");
                return;
            }
        }
        if (!orderFound) {
            System.out.println("Order not Found..");
        }
    }


    /*
    Process order: select order to process, update the status of the
    processed order from a new order to be “Ready to pickup”.
     */
    public void processOrder() {
        List<Order> orders = DataManager.orderStrategy.orderList;
        System.out.println("Enter Order ID:");
        int id=-1;

        try {
            id = Integer.parseInt(scanner.next());
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid OrderID..");
            return;
        }
        boolean orderFound = false;
        for (Order order : orders) {
            if (order.getOrderId() ==id) {
                if(order.getOrderStatus()== Order.OrderStatus.Ready)
                {
                    System.out.println("Order is Already Ready to Pickup..");
                    return;
                }
                order.setOrderStatus(Order.OrderStatus.Ready);
                orderFound = true;
                System.out.printf("Order ID: %d%n", order.getOrderId());

                System.out.printf("Total Price: $%.2f%n", order.calculateTotalPrice());
                System.out.println("The order is now Ready to Pickup");
                System.out.println("------------------");
                DataManager.saveAllData();
                return;
            }
        }
        if (!orderFound) {
            System.out.println("Order not Found..");
        }
    }

    public  void changePassword()
    {
        System.out.println("Enter new Password:");
        String password=scanner.next();
        currentStaff.setPassword(password);
        DataManager.saveAllData();
        System.out.println("Password change successfully");

    }
}
