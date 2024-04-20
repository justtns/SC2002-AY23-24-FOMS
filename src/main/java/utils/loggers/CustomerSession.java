package main.java.utils.loggers;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomerSession {
    private int orderID;
    private String branch;

    CustomerSession(String branch){
        int orderNumber = findOrderNumbers();
        this.orderID = (orderNumber + 1);
        
        this.branch = branch;
    }

    private int findOrderNumbers() {
        String ordersFilePath = "src/main/resources/xlsx/orders_list.xlsx";
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

    public String getBranch(){
        return branch;
    }

    public int getOrderId(){
        return orderID;
    }
}
