package main.java.controllers;

import main.java.models.Order;
import main.java.daos.OrderDAO;
import main.java.models.Staff;
import main.java.daos.StaffDAO;

public class StaffOrderController {
    private OrderDAO orderDAO;
    private StaffDAO staffDAO;

    public StaffOrderController(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    }

    public void displayNewOrder(){ // justin look here! do we display new orders by branch

    }

    public Order viewParticularOrder(int orderID){
        return orderDAO.findOrderById(orderID);
    }

    public void processOrder(int orderID){
        Order newOrder = orderDAO.findOrderById(orderID);
        newOrder.setOrderStatus(OrderStatus.Ready);
        orderDAO.updateElement(orderDAO.findOrderById(orderID), newOrder);
    }
}