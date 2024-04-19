package main.java.daos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.java.models.MenuItem;
import main.java.models.Order;
import main.java.utils.types.OrderStatus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.FileOutputStream;


public class OrderDAO implements DAOInterface<Order>{
    private  List<Order> orderList = new ArrayList<>();
    
    public OrderDAO(){
        readData();
    }

    @Override
    public void readData() {
        orderList = new ArrayList<>();
        String ordersFilePath = "src/main/resources/xlsx/orders_list.xlsx";
        String itemsFilePath = "src/main/resources/xlsx/order_items.xlsx";
        File ordersFile = new File(ordersFilePath);
        if (!ordersFile.exists()) {
            String[] headers = new String[]{"Order ID", "Order Status", "Is Dine In", "Is Completed"};
            try (Workbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(ordersFilePath)) {
                Sheet sheet = workbook.createSheet("Sheet1");
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                workbook.write(fos);
                } 
                catch (IOException e){
                System.out.println("Failed to create new Excel file: " + ordersFilePath);
                e.printStackTrace();
                }
        }

        File itemsFile = new File(itemsFilePath);
        if (!itemsFile.exists()) {
            String[] headers = new String[]{"Order ID", "Name", "Price", "Branch", "Category", "Comments"};
            try (Workbook workbook = new XSSFWorkbook();
            FileOutputStream fos = new FileOutputStream(itemsFilePath)) {
                Sheet sheet = workbook.createSheet("Sheet1");
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                workbook.write(fos);
                } 
                catch (IOException e){
                System.out.println("Failed to create new Excel file: " + itemsFilePath);
                e.printStackTrace();
                }
        }

        try (FileInputStream fis = new FileInputStream(ordersFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue;

                int orderId = (int) row.getCell(0).getNumericCellValue();
                OrderStatus orderStatus = OrderStatus.valueOf(row.getCell(1).getStringCellValue());
                boolean isDineIn = row.getCell(2).getBooleanCellValue();
                boolean isCompleted = row.getCell(3).getBooleanCellValue();

                Order order = new Order(orderId, orderStatus, isDineIn, isCompleted);
                addElement(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileInputStream fis = new FileInputStream(itemsFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.rowIterator();
            Order currentOrder = null;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // Skip header

                int orderId = (int) row.getCell(0).getNumericCellValue();
                if (currentOrder == null || currentOrder.getOrderId() != orderId) {
                    currentOrder = findOrderById(orderId);
                }

                if (currentOrder != null) {
                    String name = row.getCell(1).getStringCellValue();
                    double price = row.getCell(2).getNumericCellValue();
                    String branch = row.getCell(3).getStringCellValue();
                    String category = row.getCell(4).getStringCellValue();
                    String comments = row.getCell(6).getStringCellValue();
                    MenuItem item = new MenuItem(name, category, branch, price);
                    currentOrder.addItem(item);
                    for (Order order: orderList)
                    {
                        if(order.getOrderId()==orderId) {
                            order.addItem(item);
                            order.addComment(comments);
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Order findOrderById(int orderId) {
        for (Order order : orderList) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    };

    @Override
    public void saveData(){
        String ordersFilePath = "src/main/resources/xlsx/orders_list.xlsx";
        String itemsFilePath = "src/main/resources/xlsx/order_items.xlsx";
    
        // Writing orders to orders_list.xlsx
        try (FileOutputStream fos = new FileOutputStream(ordersFilePath);
                Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Orders");
    
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Order ID");
            headerRow.createCell(1).setCellValue("Order Status");
            headerRow.createCell(2).setCellValue("Is Dine In");
            headerRow.createCell(3).setCellValue("Is Completed");
    
            int rowIndex = 1;
            for (Order order : orderList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(order.getOrderId());
                row.createCell(1).setCellValue(order.getOrderStatus().toString());
                row.createCell(2).setCellValue(order.isDineIn());
                row.createCell(3).setCellValue(order.isCompleted());
            }
            workbook.write(fos);
        } catch (IOException e) {
            System.out.println("Error writing orders to file: " + e.getMessage());
            e.printStackTrace();
        }
    
        // Writing items to order_items.xlsx
        try (FileOutputStream fos = new FileOutputStream(itemsFilePath);
                Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Order Items");
    
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Order ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Price");
            headerRow.createCell(3).setCellValue("Branch");
            headerRow.createCell(4).setCellValue("Category");
            headerRow.createCell(5).setCellValue("Comments");
    
            int rowIndex = 1;
            for (Order order : orderList) {
                int i = 0;
                for (MenuItem item : order.getItems()) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(order.getOrderId());
                    row.createCell(1).setCellValue(item.getName());
                    row.createCell(2).setCellValue(item.getPrice());
                    row.createCell(3).setCellValue(item.getBranch());
                    row.createCell(4).setCellValue(item.getCategory());
                    row.createCell(5).setCellValue(order.getComment(i));
                    i++;
                }
            }
            workbook.write(fos);
        } catch (IOException e) {
            System.out.println("Error writing order items to file: " + e.getMessage());
            e.printStackTrace();
        }
        readData();
    };
    
    @Override
    public List<Order> getElements(){
        return orderList;
    };

    @Override
    public Order findElement(String itemName) {
        int findIndex=-1;
        for (int i=0;i<orderList.size();i++)
        {
            if(orderList.get(i).getOrderId()==Integer.parseInt(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return orderList.get(findIndex);
        return null;
    }

    @Override
    public void updateElement(Order oldElement, Order newElement){
        int updateIndex=-1;
        for (int i = 0; i< orderList.size(); i++)
        {
            if((orderList.get(i).getOrderId() == oldElement.getOrderId()))
            {
                updateIndex=i;
                break;
            }
        }
        if(updateIndex!=-1){
            orderList.set(updateIndex, newElement);
        }
        return;
    };

    @Override
    public void removeElement(String elementName) {
        int deleteIndex=-1;
        for (int i = 0; i< orderList.size(); i++)
        {
            if(Integer.toString((orderList.get(i).getOrderId())).equalsIgnoreCase(elementName))
            {
                deleteIndex=i;
                break;
            }
        }
        if(deleteIndex!=-1)
        orderList.remove(deleteIndex);
    }

    @Override
    public void addElement(Order element){
        orderList.add(element);
    };    
}
