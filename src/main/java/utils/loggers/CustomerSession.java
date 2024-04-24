package main.java.utils.loggers;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A logger that logs the session for a customer.
 * This session keeps track of the order ID and branch information for the customer.
 * It provides methods to retrieve and update this information.
 * The order ID is retrieved from an Excel file containing a list of orders.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class CustomerSession {

    /** The order ID for the customer session. */
    private int orderID;

    /** The branch information for the customer session. */
    private String branch;

    /**
     * Constructs a new CustomerSession object.
     * Initializes the order ID by finding the number of orders from the Excel file.
     */
    public CustomerSession(){
        int orderNumber = findOrderNumbers();
        this.orderID = (orderNumber);
        this.branch = null;
    }

    /**
     * Finds the number of orders from the Excel file containing the order list.
     * 
     * @return the number of orders
     */
    private int findOrderNumbers() {
        String ordersFilePath = "src/main/resources/xlsx/order_list.xlsx";
        int numberOfRows = 0; 

        try (FileInputStream fis = new FileInputStream(ordersFilePath);
            Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); 
            numberOfRows = sheet.getPhysicalNumberOfRows();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numberOfRows;
    }

    /**
     * Increments the order ID by one.
     */
    public void incrementOrderID(){
        orderID++;
    } 

    /**
     * Retrieves the branch information for the customer session.
     * 
     * @return the branch information
     */
    public String getBranch(){
        return branch;
    }

    /**
     * Sets the branch information for the customer session.
     * 
     * @param branch the branch information to set
     */
    public void setBranch(String branch){
        this.branch = branch;
    }

    /**
     * Retrieves the order ID for the customer session.
     * 
     * @return the order ID
     */
    public int getOrderId(){
        return orderID;
    }
}