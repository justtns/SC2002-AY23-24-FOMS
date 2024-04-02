package main.backend.handlers;
import main.backend.entities.Order;
import main.backend.entities.MenuItem;

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
    public void addElement(int orderId, String itemId) {
        MenuItem item = menuHandler.findElementById(itemId);
        if (item != null) {
            Order order = findElementById(orderId);
            if (order != null) {
                order.getItems().add(item);
                cartSize++;

                System.out.println("Item " + itemId + " added to cart.");

            } else {
                System.out.println("Order with ID " + orderId + " not found.");
            }
        } else {
            System.out.println("Menu item with ID " + itemId + " not found.");
        }
    }

    // REMOVE ITEM
    @Override
    public void removeElement(Order element) {
        orderQueue.remove(element);
    }

    public void removeElement(int orderId, String itemId) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem item = menuHandler.findElementById(itemId);
            if (item != null) {
                order.getItems().remove(item);
                cartSize--;

                if (cartSize == 0) {
                    System.out.println("Cart is empty.");
                }


            } else {
                System.out.println("Menu item with ID " + itemId + " not found.");
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

    public void updateElement(int orderId, String oldElement, String newElement) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem oldItem = menuHandler.findElementById(oldElement); 
                if (oldItem != null) {
                    MenuItem newItem = menuHandler.findElementById(newElement);
                    if (newItem != null) {
                        order.getItems().remove(oldItem);
                        order.getItems().add(newItem);

                        System.out.println("Item " + oldElement + " updated to " + newElement);
                    } else {
                        System.out.println("Menu item with ID " + newElement + " not found.");
                    }
                } else {
                    System.out.println("Menu item with ID " + oldElement + " not found.");
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
    public List<Order> listElement() {
        return new LinkedList<>(orderQueue);
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
