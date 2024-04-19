package main.java.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.java.domain.deprecated.CreditDebitPayment;
import main.java.domain.deprecated.OnlinePayment;
import main.java.domain.deprecated.PaymentInterface;

public class PaymentHandler implements HandlerInterface<PaymentInterface>{
    private List<PaymentInterface> paymentMethods;

    public PaymentHandler() {
        this.paymentMethods = new ArrayList<>();
    
        String filePath = "src/main/resources/xlsx/payment_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Orders");
                Row headerRow = sheet.createRow(0);
                String[] columnHeaders = {"Type", "Name/Email", "Number/Password", "CVC", "Expiry Date", "Domain"};
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
                    // if (row.getRowNum() == 0){
                    //     continue;
                    // }
                    if (row.getCell(0) == null) {
                        continue; 
                    }
                    String type = row.getCell(0).getStringCellValue();
                    if (type.equals("CreditDebit")){
                        String name = row.getCell(1).getStringCellValue();
                        String number = row.getCell(2).getStringCellValue();
                        String cvc = row.getCell(3).getStringCellValue();
                        String expiryDate = row.getCell(4).getStringCellValue();
                        String cardType = row.getCell(7).getStringCellValue();
                        PaymentInterface item = new CreditDebitPayment(name, number, cvc, expiryDate, cardType);
                        this.paymentMethods.add(item);
    
                    }
                    else if (type.equals("OnlinePayment")){
                        String email = row.getCell(5).getStringCellValue();
                        String password = row.getCell(6).getStringCellValue();
                        String domain = row.getCell(7).getStringCellValue();
                        PaymentInterface item = new OnlinePayment(email, password, domain);
                        this.paymentMethods.add(item);
                    }      
                }
            }

        catch (IOException e){
            e.printStackTrace();
        }

        if (this.paymentMethods.size() == 0){
            System.out.println("No Payment Methods Registered.");
        }
    }

    @Override
    public boolean addElement(PaymentInterface element) {
            if (element.validate()){
                this.paymentMethods.add(element);
                return true;
            }
            return false;
    }

    @Override
    public void listElement() {
        System.out.println("All Payment Methods: ");
        for (PaymentInterface item : this.paymentMethods) {
            if (item instanceof OnlinePayment){
                OnlinePayment temp = (OnlinePayment) item;
                System.out.println("Online Payment | Domain: " + temp.getDomain() + ", Email: " + temp.getEmail());
            }
            else if (item instanceof CreditDebitPayment){
                CreditDebitPayment temp = (CreditDebitPayment) item;
                System.out.println("Credit/Debit Payment | Name: " + temp.getName() + ", Type: " + temp.getType());
            }
        }
    }

    @Override
    public boolean updateElement(PaymentInterface oldElement, PaymentInterface newElement) {
        Boolean found = false;
        if (oldElement instanceof OnlinePayment){
            OnlinePayment temp = (OnlinePayment) oldElement;
            int i = findOnlineElementIndex(temp.getEmail(), temp.getDomain());
            paymentMethods.set(i, newElement);
            found = true;
        }
        else if (oldElement instanceof CreditDebitPayment){
            CreditDebitPayment temp = (CreditDebitPayment) oldElement;
            int i = findCreditDebitIndex(temp.getName(), temp.getType());
            paymentMethods.set(i, newElement);
            found = true;
        }
        if (!found) {
            System.out.println("Payment Method was not found and could not be replaced.");
        }
        return found;
    }
    
    @Override
    public boolean removeElement(PaymentInterface element) {
        Boolean found = false;
        if (element instanceof OnlinePayment){
            OnlinePayment temp = (OnlinePayment) element;
            int i = findOnlineElementIndex(temp.getEmail(), temp.getDomain());
            paymentMethods.remove(i);
            found = true;
        }
        else if (element instanceof CreditDebitPayment){
            CreditDebitPayment temp = (CreditDebitPayment) element;
            int i = findCreditDebitIndex(temp.getName(), temp.getType());
            paymentMethods.remove(i);
            found = true;
        }
        if (!found) {
            System.out.println("Payment Method was not found and could not be replaced.");
        }
        return found;
    }

    private int findOnlineElementIndex(String email, String domain){
        for (int i = 0; i<paymentMethods.size(); i++){
            if ((paymentMethods.get(i) instanceof OnlinePayment)){
                OnlinePayment newTemp = (OnlinePayment) paymentMethods.get(i);
                if ((newTemp.getEmail() == email) & (newTemp.getDomain() == domain)){
                    return i;
                }
            }
        }
        return 0;
    }

    private int findCreditDebitIndex(String name, String domain){
        for (int i = 0; i<paymentMethods.size(); i++){
            if ((paymentMethods.get(i) instanceof CreditDebitPayment)){
                CreditDebitPayment newTemp = (CreditDebitPayment) paymentMethods.get(i);
                if ((newTemp.getName() == name) & (newTemp.getType() == domain)){
                    return i;
                }
            }
        }
        return 0;
    }
    
    @Override
    public void saveElement() {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src\\main\\resources\\xlsx\\payment_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Payment Methods");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Type", "Name/Email", "Number/Password", "CVC", "Expiry Date", "Domain"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (PaymentInterface item : this.paymentMethods) {
                Row row = sheet.createRow(rowIndex++);
                if (item instanceof CreditDebitPayment) {
                    CreditDebitPayment payment = (CreditDebitPayment) item;
                    row.createCell(0).setCellValue("CreditDebit");
                    row.createCell(1).setCellValue(payment.getName());
                    row.createCell(2).setCellValue(payment.getCardNumber());
                    row.createCell(3).setCellValue(payment.getCVC());
                    row.createCell(4).setCellValue(payment.getExpiryDate());
                    row.createCell(7).setCellValue(payment.getType());
                } else if (item instanceof OnlinePayment) {
                    OnlinePayment payment = (OnlinePayment) item;
                    row.createCell(0).setCellValue("OnlinePayment");
                    row.createCell(5).setCellValue(payment.getEmail());
                    row.createCell(6).setCellValue(payment.getPassword());
                    row.createCell(7).setCellValue(payment.getDomain());
                }
            }

            workbook.write(outputStream);
            System.out.println("Excel file was updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
    }
}
