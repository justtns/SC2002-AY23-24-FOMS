package main.java.applications;

import main.java.daos.OrderDAO;
import main.java.models.MenuItem;
import main.java.models.Order;
import main.java.daos.MenuDAO;

import main.java.controllers.CustomerOrderController;
import main.java.controllers.CustomerMenuController;

import java.util.List;

import main.java.boundaries.CustomerOrderingForm;

public class CustomerOrderingApplication implements Application{

    public void execute(String branch, int orderID){
        OrderDAO orderDAO = new OrderDAO();
        MenuDAO menuDAO = new MenuDAO();

        CustomerOrderController orderController = new CustomerOrderController(orderDAO);
        CustomerMenuController menuController = new CustomerMenuController(menuDAO);

        Boolean ordering = true;
        List<MenuItem> selectedItems = menuController.getitems();
        
        Order custOrder = orderController.createCustomerOrder(orderID);
        // System.out.println("Placing an Order");
        CustomerOrderingForm.initaiteOrder();
        //ordering method
        while(ordering){
            String menuItem = CustomerOrderingForm.getOrderInput(branch, selectedItems);
            if (menuItem.equalsIgnoreCase("done")) {
                break;
            }
            MenuItem selectedItem = findMenuItemByName(menuItem, branch, selectedItems);
            if (selectedItem == null || !selectedItem.getBranch().equals(branch)) {
                CustomerOrderingForm.printInvalidItem();
                continue;
            }
            String comment = CustomerOrderingForm.getComment();
            int quantity = CustomerOrderingForm.getQty();
            custOrder = orderController.addItem(custOrder, selectedItem, quantity, comment);
        }
        // confirm, set takeout status 
        CustomerOrderingForm.getOrderConfirmation(custOrder);
        custOrder = orderController.chooseDineIn(custOrder, CustomerOrderingForm.getOrderDineIn());
        orderController.saveOrder(custOrder);
    }

    // Method to find a menu item by its name
    private MenuItem findMenuItemByName(String name,String branch, List<MenuItem> menuItems) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranch().equalsIgnoreCase(branch)) {
                return item;
            }
        }
        return null;
    }
}



// System.out.println("Choose a Payment Method:");
//         for (int i=0;i<DataManager.paymentStrategy.paymentMethods.size();i++)
//         {
//             System.out.println(i+": "+DataManager.paymentStrategy.paymentMethods.get(i).getMethod());
            
//         }
//         System.out.println("Enter your choice: ");
//         scanner.next();
//         // Simulate payment
//         System.out.println("Payment successful. Thank you for your order!");
//         // Create and display the order details
//         Order order = new Order(selectedItems, m); // Assuming it's not a dine-in order
//         System.out.println("Order Details:");
//         System.out.println("Order ID: " + order.getOrderId());
//         System.out.println("Total Price: $" + order.calculateTotalPrice());
//         if(m)
//         System.out.println("Dine-in");
//         else
//             System.out.println("Take-Away");
//         System.out.println("Items:");
//         for (MenuItem item : order.getItems()) {
//             System.out.println(item.getName() + " - $" + item.getPrice());
//         }