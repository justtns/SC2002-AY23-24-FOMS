package test.java.handlers;

import main.java.entities.MenuItem;
import main.java.entities.Order;
import main.java.entities.OrderQuantities;
import main.java.handlers.OrderHandler;
import main.java.handlers.MenuHandler;
import java.util.List;
import java.util.ArrayList;

public class OrderHandlerTester {

    private static void assertEqual(String testName, Object expected, Object actual) {
        if (!expected.equals(actual)) {
            System.out.println(testName + " Failed: Expected " + expected + ", but got " + actual);
        } else {
            System.out.println(testName + " Passed");
        }
    }

    private static void testAddElement() {
        MenuHandler menuHandler = new MenuHandler(); // Assuming you have a MenuHandler class
        OrderHandler handler = OrderHandler.getInstance(menuHandler);

        // Assuming you have constructors or methods to create these objects.
        MenuItem item = new MenuItem("Pizza", "Main", "Branch", 9.99f); 
        List<OrderQuantities> quantities = new ArrayList<>();
        quantities.add(new OrderQuantities(item, 2));

        Order order = new Order(1, quantities, 1000, 'N', false);
        handler.addElement(order);

        assertEqual("testAddElement", 19.98f, handler.getTotalAmount()); // Adjust according to your logic
    }

    private static void testUpdateOrderItemQty() {
        MenuHandler menuHandler = new MenuHandler(); // Setup necessary environment
        OrderHandler handler = OrderHandler.getInstance(menuHandler);

        // Add an order to update
        MenuItem item = new MenuItem("Fries", "side", "ntu", 3.2f);
        List<OrderQuantities> quantities = new ArrayList<>();
        quantities.add(new OrderQuantities(item, 1));
        Order order = new Order(2, quantities, 1000, 'N', false);
        handler.addElement(order);

        // Update the item quantity
        handler.updateOrderItemQty(2, "Fries", 2, "ntu");
        int updatedQuantity = -1;
        Order updatedOrder = handler.findElementById(1);
        List<OrderQuantities> updatedQuantities = updatedOrder.getItems();
        Boolean found = false;
        for (OrderQuantities Orders: updatedQuantities){
            if ((Orders.getItem().getName() == "Fries") & (Orders.getItem().getBranch() == "ntu")){
                updatedQuantity = Orders.getQuantity();
                found = true;
            }
        }
        assertEqual("Item found", true, found);
        assertEqual("testUpdateOrderItemQty", 2, updatedQuantity);
    }

    private static void testRemoveElement() {
        MenuHandler menuHandler = new MenuHandler(); // Setup necessary environment
        OrderHandler handler = OrderHandler.getInstance(menuHandler);

        // Add an order to remove
        MenuItem item = new MenuItem("Burger", "Main", "Branch", 7.99f);
        List<OrderQuantities> quantities = new ArrayList<>();
        quantities.add(new OrderQuantities(item, 1));

        Order order = new Order(3, quantities, 1000, 'N', false);
        handler.addElement(order);

        // Remove the order
        handler.removeElement(order);

        float result = handler.getTotalAmount();

        assertEqual("testRemoveElement", 19.98f, result);
    }

    public static void main(String[] args) {
        testAddElement();
        testRemoveElement();
        testUpdateOrderItemQty();
    }
}

