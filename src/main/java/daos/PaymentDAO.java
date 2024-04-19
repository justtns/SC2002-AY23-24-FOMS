package main.java.daos;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.java.domain.models.PaymentMethod;
import main.java.domain.types.PaymentType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

public class PaymentDAO implements DAOInterface<PaymentMethod>{
    private List<PaymentMethod> paymentMethodList;
    
    public PaymentDAO(){
        readData();
    }

    @Override 
    public void readData(){
        paymentMethodList = new ArrayList<>(); // Assuming this is a list of staff members
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
                        if (row.getRowNum() == 0) { // Skip header row
                            continue;
                        }
                        if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null) {
                            continue; 
                        }
                        String name = row.getCell(0).getStringCellValue();
                        String typeString = row.getCell(1).getStringCellValue();    
                        PaymentType type;
                        switch (typeString) {
                            case "Online":
                                type = PaymentType.Online;
                                break;
                            case "Debit":
                                type = PaymentType.Debit;
                                break;
                            case "Credit":
                                type = PaymentType.Credit;
                                break;
                            default:
                                type = PaymentType.Online; // Default to Staff if role is undefined
                                break;
                        }
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
            System.out.println("payment method data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save payment method data: " + e.getMessage());
            e.printStackTrace();
        }
        readData();
    }

    @Override
    public List<PaymentMethod> getElements() {
        return paymentMethodList;
    }

    @Override
    public void addElement(PaymentMethod item) {
        paymentMethodList.add(item);
    }

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
