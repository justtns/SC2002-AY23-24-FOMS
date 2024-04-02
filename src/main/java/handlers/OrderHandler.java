package main.java.handlers;
import main.java.entities.Order;
import main.java.entities.MenuItem;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderHandler implements HandlerInterface<Order> {
    private static OrderHandler instance; // Static instance
    private Queue<Order> orderQueue;
    private MenuHandler menuHandler;
    private int cartSize = 0;

    // private instance of OrderHandler
    private OrderHandler(MenuHandler menuHandler) {
        this.orderQueue = new LinkedList<>();
        this.menuHandler = menuHandler;
    }

    // singleton - ensures only one instance of OrderHandler is created 
    public static OrderHandler getInstance(MenuHandler menuHandler) {
        if (instance == null) {
            instance = new OrderHandler(menuHandler);
        }
        return instance;
    }

    @Override
    public void addElement(Order element) {
        orderQueue.add(element);
    }

    // ADD ITEM
    public void addElement(int orderId, String name, String branch) {
        MenuItem item = menuHandler.findElementById(name, branch);
        if (item != null) {
            Order order = findElementById(orderId);
            if (order != null) {
                order.getItems().add(item);
                cartSize++;
                System.out.println("Item " + name + " added to cart.");
                
// make this error handling
            } else {
                System.out.println("Order with ID " + orderId + " not found.");
            }
        } else {
            System.out.println("Menu item with name " + name + " not found.");
        }
    }

    // REMOVE ITEM
    @Override
    public void removeElement(Order element) {
        orderQueue.remove(element);
    }

    public void removeElement(int orderId, String name, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem item = menuHandler.findElementById(name, branch);
            if (item != null) {
                order.getItems().remove(item);
                cartSize--;

                if (cartSize == 0) {
                    System.out.println("Cart is empty.");
                }


            } else {
                System.out.println("Menu item with Name " + name + " not found.");
            }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }
    
    //EDIT ITEM
    @Override
    public void updateElement(Order oldElement, Order newElement) {
        orderQueue.remove(oldElement);
        orderQueue.add(newElement);
    }

    // this should be to edit an order, but currently it is editing the menu items within the order needs to b changed
    public void updateElement(int orderId, String oldname, String newname, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem oldItem = menuHandler.findElementById(oldname, branch); 
                if (oldItem != null) {
                    MenuItem newItem = menuHandler.findElementById(newname, branch);
                    if (newItem != null) {
                        order.getItems().remove(oldItem);
                        order.getItems().add(newItem);

                        System.out.println("Item " + oldname + " updated to " + newname);
                    } else {
                        System.out.println("Menu item with ID " + newname + " not found.");
                    }
                } else {
                    System.out.println("Menu item with ID " + oldname + " not found.");
                }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
    }

    public void updateElement(int orderId,char takeawayOption) {
        Order order = findElementById(orderId);
        if (order != null) {
            order.setTakeawayOption(takeawayOption);
        } else {
            System.out.println("No orders found.");
        }
    }

    //LIST ITEM
    @Override
    public void listElement() {

    }

    public float getTotalAmount() {
        return orderQueue.stream()
                .map(Order::getAmount)
                .reduce(0f, Float::sum);
    }

    public Order findElementById(int orderId) {
        return orderQueue.stream()
                .filter(order -> order.getOrderID() == orderId)
                .findFirst()
                .orElse(null);
    }
}
