package main.java.utils.loggers;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CustomerSession {
    private int orderID;
    private String branch;

    public CustomerSession(){
        int orderNumber = findOrderNumbers();
        this.orderID = (orderNumber);
        this.branch = null;
    }

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
        System.out.println(numberOfRows);
        return numberOfRows;
    }

    public String getBranch(){
        return branch;
    }

    public void setBranch(String branch){
        this.branch = branch;
    }

    public int getOrderId(){
        return orderID;
    }
}
