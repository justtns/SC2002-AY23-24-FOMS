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
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * OrderDAO is implemented from DAOInterface 
 * OrderDAO is a Data Access Object (DAO) for managing Order objects with CRUD Operations.
 * It provides methods to interact with Order data stored in Excel files.
 * 
 * @author SDDA Team 1
 * @version 1.3
 * @since 23-Apr-2024
 */
public class OrderDAO implements DAOInterface<Order>{
    /**
    * List to hold Order objects.
    */
    private  List<Order> orderList = new ArrayList<>();
    
    /**
     * Constructor for OrderDAO.
     * Initializes orderList and reads data from Excel files.
     */
    public OrderDAO(){
        readData();
    }

    /**
     * Reads data from Excel files and populates the orderList.
     * If the files do not exist, it creates new Excel files.
     * Handles IOException if encountered while reading or creating the files.
     */
    @Override
    public void readData() {
        String ordersFilePath = "src/main/resources/xlsx/order_list.xlsx";
        String itemsFilePath = "src/main/resources/xlsx/order_items.xlsx";
        File ordersFile = new File(ordersFilePath);
        if (!ordersFile.exists()) {
            String[] headers = new String[]{"Order ID", "Branch", "Order Status", "Is Dine In", "Is Completed"};
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
            String[] headers = new String[]{"Order ID", "Branch", "Name", "Price", "Branch", "Category", "Comments"};
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
                String branch = row.getCell(1).getStringCellValue();
                OrderStatus orderStatus = OrderStatus.valueOf(row.getCell(2).getStringCellValue());
                boolean isDineIn = row.getCell(3).getBooleanCellValue();
                boolean isCompleted = row.getCell(4).getBooleanCellValue();

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
                currentOrder = findElement(Integer.toString(orderId));

                if (currentOrder != null) {
                    String name = row.getCell(1).getStringCellValue();
                    double price = row.getCell(2).getNumericCellValue();
                    String branch = row.getCell(3).getStringCellValue();
                    String category = row.getCell(4).getStringCellValue();
                    String comments;
                    if (row.getCell(6) != null) {
                        comments = row.getCell(6).getStringCellValue();
                    } else {
                        comments = " ";
                    }
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

    /**
     * Saves the data from orderList to Excel files.
     * If the files do not exist, it creates new ones.
     * Handles IOException if encountered while writing the files.
     */
    @Override
    public void saveData(){
        // Code for saving data to Excel files
    };
    
    /**
     * Returns the list of Orders stored in orderList.
     * @return List of Orders
     */
    @Override
    public List<Order> getElements(){
        return orderList;
    };

    /**
     * Finds and returns an Order by its ID.
     * @param itemName The Name of the Item to find, which is equivalent to the ID
     * @return The found Order containing the Item inside, or null if not found
     */
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

    /**
     * Updates an existing Order with new data.
     * @param oldElement The old Order object to update
     * @param newElement The new Order object containing updated data
     */
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

    /**
     * Removes an Order from the list by its name.
     * @param elementName The Name of the Order to remove
     */
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

    /**
     * Adds a new Order to the list.
     * @param element The Order object to add
     */
    @Override
    public void addElement(Order element){
        orderList.add(element);
    };    
}