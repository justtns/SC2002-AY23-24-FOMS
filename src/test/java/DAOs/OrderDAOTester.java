package test.java.DAOs;
import java.time.LocalDateTime;

import main.java.daos.OrderDAO;
import main.java.models.Order;
import main.java.utils.types.OrderStatus;

public class OrderDAOTester {
    public static void main(String[] args) {
        OrderDAO orderDao = new OrderDAO();

        // Test adding an order
        Order newOrder = new Order(101, OrderStatus.NEW, "JE", true, false, LocalDateTime.now());
        orderDao.addElement(newOrder);
        System.out.println("Add Order Test: " + (orderDao.getElements().contains(newOrder) ? "Passed" : "Failed"));

        // Test finding an order
        Order foundOrder = orderDao.findElement("101");
        System.out.println("Find Order Test: " + (foundOrder != null ? "Passed" : "Failed"));

        // Test updating an order
        Order updatedOrder = new Order(101, OrderStatus.COMPLETED, "JE", true, true, LocalDateTime.now());
        orderDao.updateElement(newOrder, updatedOrder);
        foundOrder = orderDao.findElement("101");
        System.out.println("Update Order Test: " + (foundOrder.isCompleted() ? "Passed" : "Failed"));

        // Test removing an order
        orderDao.removeElement("101");
        foundOrder = orderDao.findElement("101");
        System.out.println("Remove Order Test: " + (foundOrder == null ? "Passed" : "Failed"));
    }
}
