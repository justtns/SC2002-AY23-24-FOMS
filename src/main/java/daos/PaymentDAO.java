package main.java.daos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.java.models.PaymentMethod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * PaymentDAO is implemented from DAOInterface.
 * PaymentDAO is a Data Access Object (DAO) for managing PaymentMethod objects with CRUD Operations.
 * It provides methods to interact with PaymentMethod data stored in Excel files.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 23-Apr-2024
 */
public class PaymentDAO implements DAOInterface<PaymentMethod>{
    /**
     * List to hold PaymentMethod objects.
     */
    private List<PaymentMethod> paymentMethodList;
    
    /**
     * Constructor for PaymentDAO.
     * Initializes paymentMethodList and reads data from Excel files.
     */
    public PaymentDAO(){
        readData();
    }

    /**
     * Reads data from Excel files and populates the paymentMethodList.
     * If the files do not exist, it creates new Excel files.
     * Handles IOException if encountered while reading or creating the files.
     */
    @Override 
    public void readData(){
        paymentMethodList = new ArrayList<>();
        String filePath = "src/main/resources/xlsx/payment_methods.xlsx";
        File file = new File(filePath);
        
        // Check if the file exists, if not, create it and add headers
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("PaymentMethods");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Name", "Type"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        // Read data from the Excel file
        try (FileInputStream inputStream = new FileInputStream(file);
                Workbook workbook = new XSSFWorkbook(inputStream)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    Iterator<Row> rowIterator = sheet.iterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        if (row.getRowNum() == 0| row.getCell(0).getStringCellValue().isEmpty()) { // Skip header row
                            continue;
                        }
                        if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null) {
                            continue; 
                        }
                        String name = row.getCell(0).getStringCellValue();
                        String type = row.getCell(1).getStringCellValue(); 
                        paymentMethodList.add(new PaymentMethod(name, type));
                    }
            }    
            catch (IOException e) {
                e.printStackTrace();
            }
            if (paymentMethodList.size() == 0) {
                System.out.println("No Payment Methods found.");
            }
        }        

    /**
     * Saves the data from paymentMethodList to Excel files.
     * If the files do not exist, it creates new ones.
     * Handles IOException if encountered while writing the files.
     */
    public void saveData() {
        String filePath = "src/main/resources/xlsx/payment_methods.xlsx";
        File file = new File(filePath);

        try (Workbook workbook = new XSSFWorkbook();
            FileOutputStream outputStream = new FileOutputStream(file)) {
            Sheet sheet = workbook.createSheet("PaymentMethods");

            // Create headers in the Excel file
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Name", "Type"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Populate the Excel file with staff data
            int rowIndex = 1;
            for (PaymentMethod method : paymentMethodList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(method.getName());
                row.createCell(1).setCellValue(method.getType().toString());
            }
            workbook.write(outputStream);
            System.out.println("Payment method data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save payment method data: " + e.getMessage());
            e.printStackTrace();
        }
        readData(); // Refresh data after saving
    }

    /**
     * Returns the list of PaymentMethods stored in paymentMethodList.
     * @return List of PaymentMethods
     */
    @Override
    public List<PaymentMethod> getElements() {
        return paymentMethodList;
    }

    /**
     * Adds a new PaymentMethod to the list.
     * @param item The PaymentMethod object to add
     */
    @Override
    public void addElement(PaymentMethod item) {
        paymentMethodList.add(item);
    }

    /**
     * Removes a PaymentMethod from the list by its name.
     * @param methodName The Name of the PaymentMethod to remove
     */
    @Override
    public void removeElement(String methodName) {
        int deleteIndex=-1;
        for (int i=0;i<paymentMethodList.size();i++)
        {
            if(paymentMethodList.get(i).getName().equalsIgnoreCase(methodName))
            {
                deleteIndex=i;
                break;
            }
        }
        if(deleteIndex!=-1)
        paymentMethodList.remove(deleteIndex);
    }

    /**
     * Finds and returns a PaymentMethod by its name.
     * @param itemName The Name of the PaymentMethod to find
     * @return The found PaymentMethod, or null if not found
     */
    @Override
    public PaymentMethod findElement(String itemName) {
        int findIndex=-1;
        for (int i=0;i<paymentMethodList.size();i++)
        {
            if(paymentMethodList.get(i).getName().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return paymentMethodList.get(findIndex);
        return null;
    }    

    /**
     * Updates an existing PaymentMethod with new data.
     * @param oldElement The old PaymentMethod object to update
     * @param newElement The new PaymentMethod object containing updated data
     */
    @Override
    public void updateElement(PaymentMethod oldElement, PaymentMethod newElement) {
        int findIndex=-1;
        for (int i=0;i<paymentMethodList.size();i++)
        {
            if(paymentMethodList.get(i).getName().equalsIgnoreCase(oldElement.getName()))
            {
                findIndex=i;
                break;
            }
        }
        if(findIndex!=-1) {
            paymentMethodList.set(findIndex, newElement);
        }    
    }
}