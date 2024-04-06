package main.java.handlers;
import main.java.entities.Order;
import main.java.entities.OrderQuantities;
import main.java.entities.MenuItem;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.ArrayList;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;

public class OrderHandler implements HandlerInterface<Order> {
    private static OrderHandler instance; // Static instance
    private Queue<Order> orderQueue;
    private MenuHandler menuHandler;
    private int cartSize = 0;

    // private instance of OrderHandler
    private OrderHandler(MenuHandler menuHandler) {
        this.orderQueue = new LinkedList<>();
        this.menuHandler = menuHandler;

        // Load orders from excel file

        String filePath = "src/main/resources/xlsx/order_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Orders");
                Row headerRow = sheet.createRow(0);
                String[] columnHeaders = {"Order ID", "Order Status", "Total Amount", "Order Time", "Takeaway Option", "Payment Status", "Items"};
                for (int i = 0; i < columnHeaders.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columnHeaders[i]);
                }
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (FileInputStream inputStream = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null || row.getCell(4) == null || row.getCell(5) == null || row.getCell(6) == null) {
                    continue;
                }
                int orderID = (int) row.getCell(0).getNumericCellValue();
                String orderStatus = row.getCell(1).getStringCellValue();
                float totalAmount = (float) row.getCell(2).getNumericCellValue();
                long orderTime = (long) row.getCell(3).getNumericCellValue();
                char takeawayOption = row.getCell(4).getStringCellValue().charAt(0);
                boolean paymentStatus = row.getCell(5).getBooleanCellValue();
                String items = row.getCell(6).getStringCellValue();
                String[] itemArray = items.split(",");
                List<OrderQuantities> itemsQuantities = new ArrayList<>();
                for (String item : itemArray) {
                    String[] itemDetails = item.split(":");
                    MenuItem menuItem = menuHandler.findElementById(itemDetails[0], itemDetails[1]);
                    if (menuItem != null) {
                        itemsQuantities.add(new OrderQuantities(menuItem, Integer.parseInt(itemDetails[2])));
                    }
                }
                Order order = new Order(orderID, itemsQuantities, totalAmount, orderTime, takeawayOption, paymentStatus);
                orderQueue.add(order);
            }
        } catch (IOException e) {
            e.printStackTrace();

    }
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

    //should add error handling for if its already been paid for, then cannot edit.
    public boolean addElement(int orderId, String name, int quantity, String branch) {
        MenuItem item = menuHandler.findElementById(name, branch);
        if (item != null) {
            Order order = findElementById(orderId);
            if (order != null) {
                order.addItem(item, quantity);
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

    @Override
    public boolean removeElement(Order element) {
        orderQueue.remove(element);
        return true;
    }

    public boolean removeElementbyAttributes(int orderId, String name, int quantity, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem item = menuHandler.findElementById(name, branch);
            if (item != null) {
                order.removeItem(item, orderId);
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

    public boolean updateOrderItemQty(int orderId, String itemName, int quantity, String branch) {
        Order order = findElementById(orderId);
        if (order != null) {
            MenuItem item = menuHandler.findElementById(itemName, branch);
            if (item != null) {
                order.removeItem(item);
                order.addItem(item, quantity);
                System.out.println(order.getItems());
                System.out.println("Item " + itemName + " updated in cart.");
                return true;
            } 
            else {
                System.out.println("Menu item with Name " + itemName + " not found.");
            }
        } 
        else {
            System.out.println("Order with ID " + orderId + " not found.");
        }
        return false;
    }

    public void updateTakeawayOption(int orderId,char takeawayOption) {
        Order order = findElementById(orderId);
        if (order != null) {
            order.setTakeawayOption(takeawayOption);
        } else {
            System.out.println("No orders found.");
        }
    }

    @Override
    public void listElement() {
        
        for (Order order : orderQueue) {
            System.out.println(order);
        }
        
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

    @Override
    public void saveElement() {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src\\main\\resources\\xlsx\\order_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Order List");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] columnHeaders = {"Order ID", "Order Status", "Total Amount", "Order Time", "Takeaway Option", "Payment Status", "Items"};
            for (int i = 0; i < columnHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnHeaders[i]);
            }
            for (Order item : this.orderQueue) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getOrderID());
                row.createCell(1).setCellValue(item.getOrderStatus());
                row.createCell(2).setCellValue(item.getAmount());
                row.createCell(3).setCellValue(item.getTime());
                row.createCell(4).setCellValue(item.getTakeaway());
                row.createCell(5).setCellValue(item.getPaymentStatus());
                row.createCell(6).setCellValue(item.toString());
                }
            workbook.write(outputStream);
            System.out.println("Excel file was updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
    }
}
