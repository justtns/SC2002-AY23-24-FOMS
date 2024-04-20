package main.java.controllers;

import main.java.models.Order;
import main.java.models.MenuItem;
import main.java.daos.OrderDAO;

public class CustomerOrderController {
    private OrderDAO orderDAO;

    public CustomerOrderController(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    public Order createCustomerOrder(int orderID){
        Order customerOrder = new Order(orderID);
        return customerOrder;
    }
    public Order chooseDineIn(Order customerOrder, boolean choice){
        customerOrder.setDineIn(choice);
        return customerOrder;
    }

    public Order addItem(Order customerOrder, MenuItem cartItem, int Quantity, String Comments){
        for (int i = 0; i<Quantity; i++){
            customerOrder.addItem(cartItem);
            customerOrder.addComment(Comments);
        }
        return customerOrder;
    }

    public Order deleteItem(Order customerOrder, MenuItem cartItem, int Quantity){
        for(int i=0; i<Quantity; i++){
            customerOrder.removeItem(cartItem);
        }
        return customerOrder;
    }

    public Order findOrder(int orderID){
        return orderDAO.findOrderById(orderID);
    }

    public void saveOrder(Order customerOrder){
        orderDAO.addElement(customerOrder);
        orderDAO.saveData();
    }

    public void updateOrder(Order customerOrder){
        orderDAO.updateElement(orderDAO.findOrderById(customerOrder.getOrderId()), customerOrder);
    }

    public double getTotal(Order customerOrder){
        return customerOrder.calculateTotalPrice();
    }
}
