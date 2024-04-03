package main.java.handlers;
import main.java.entities.Order;
import main.java.entities.MenuItem;

import java.util.LinkedList;
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

        //the initialisation should read some form of xslsx file for available orders.
    }

    // singleton - ensures only one instance of OrderHandler is created 
    public static OrderHandler getInstance(MenuHandler menuHandler) {
        if (instance == null) {
            instance = new OrderHandler(menuHandler);
        }
        return instance;
    }

    @Override
    public boolean addElement(Order element) {
        orderQueue.add(element);
        return true;
    }

    // ADD ITEM

    //should add error handling for if its already been paid for, then cannot edit.
    public boolean addElement(int orderId, String name, String branch) {
        MenuItem item = menuHandler.findElementById(name, branch);
        if (item != null) {
            Order order = findElementById(orderId);
            if (order != null) {
                order.getItems().add(item);
                cartSize++;
                System.out.println("Item " + name + " added to cart.");
                return true;  
            } else {
                System.out.println("Order with ID " + orderId + " not found.");
            }
        } else {
            System.out.println("Menu item with name " + name + " not found.");
        }
        return false;
    }

    // REMOVE ITEM
    @Override
    public boolean removeElement(Order element) {
        orderQueue.remove(element);
        return true;
    }

    public boolean removeElement(int orderId, String name, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem item = menuHandler.findElementById(name, branch);
            if (item != null) {
                order.getItems().remove(item);
                cartSize--;

                if (cartSize == 0) {
                    System.out.println("Cart is empty.");
                }
                return true;
            } else {
                System.out.println("Menu item with Name " + name + " not found.");
            }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
        return false;
    }
    
    //EDIT ITEM
    @Override
    public boolean updateElement(Order oldElement, Order newElement) {
        orderQueue.remove(oldElement);
        orderQueue.add(newElement);
        return true;
    }

    public boolean updateElement(int orderId, String oldname, String newname, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem oldItem = menuHandler.findElementById(oldname, branch); 
                if (oldItem != null) {
                    MenuItem newItem = menuHandler.findElementById(newname, branch);
                    if (newItem != null) {
                        order.getItems().remove(oldItem);
                        order.getItems().add(newItem);
                        System.out.println("Item " + oldname + " updated to " + newname);
                        return true;
                    } else {
                        System.out.println("Menu item with ID " + newname + " not found.");
                    }
                } else {
                    System.out.println("Menu item with ID " + oldname + " not found.");
                }
        } else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
        return false;
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
        //list the available orders here.
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

    public void updatePayment(int orderId){
        findElementById(orderId).setPaymentStatusTrue();
        System.out.println("Payment completed.");
    }
}
