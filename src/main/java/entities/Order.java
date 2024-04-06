package main.java.entities;

import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderID;
    private List<OrderQuantities> itemsQuantities;
    public String orderStatus;
    private float totalAmount;
    private long orderTime;
    private char takeawayOption;
    private boolean paymentStatus;

    public Order(int orderID, List<OrderQuantities> itemsQuantities, float totalAmount, long orderTime, char takeawayOption, boolean paymentStatus) {
        this.orderID = orderID;
        this.itemsQuantities = itemsQuantities != null ? new ArrayList<>(itemsQuantities) : new ArrayList<>();
        this.totalAmount = totalAmount;
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = takeawayOption;
        this.paymentStatus = paymentStatus;
    }

    //Overload
    public Order(int orderID, List<OrderQuantities> itemsQuantities, long orderTime, char takeawayOption, boolean paymentStatus) {
        this.orderID = orderID;
        this.itemsQuantities = itemsQuantities != null ? new ArrayList<>(itemsQuantities) : new ArrayList<>();
        this.totalAmount = 0;
        for (OrderQuantities mq : itemsQuantities) {
            this.totalAmount += mq.getItem().getPrice() * mq.getQuantity();
        }
        this.orderStatus = "Order has been placed";
        this.orderTime = orderTime;
        this.takeawayOption = takeawayOption;
        this.paymentStatus = paymentStatus;


        // // add to order_list.xlsx
        //  try (FileInputStream inputStream = new FileInputStream(new File("/Users/rachelkoh/Desktop/OOP/CustomerOrderHandling/CustomerOrderHandling/SC2002-AY23-24-FOMS/src/main/resources/xlsx/order_list.xlsx"));
        //     Workbook workbook = new XSSFWorkbook(inputStream)) {
        //         Sheet sheet = workbook.getSheetAt(0);
        //         int lastRowNum = sheet.getLastRowNum();
        //         Row row = sheet.createRow(lastRowNum + 1);
        //         row.createCell(0).setCellValue(orderID);
        //         row.createCell(1).setCellValue(orderStatus);
        //         row.createCell(2).setCellValue(totalAmount);
        //         row.createCell(3).setCellValue(orderTime);
        //         row.createCell(4).setCellValue(takeawayOption);
        //         row.createCell(5).setCellValue(paymentStatus);
        //         StringBuilder items = new StringBuilder();
        //         for (OrderQuantities item : itemsQuantities) {
        //             items.append(item.getItem().getName()).append(":").append(item.getItem().getBranch()).append(":").append(item.getQuantity()).append(",");
        //         }
        //         row.createCell(6).setCellValue(items.toString());
        //         try (FileOutputStream outputStream = new FileOutputStream("/Users/rachelkoh/Desktop/OOP/CustomerOrderHandling/CustomerOrderHandling/SC2002-AY23-24-FOMS/src/main/resources/xlsx/order_list.xlsx")) {
        //             workbook.write(outputStream);
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order ID: " + orderID + "\nItems:\n");
        for (OrderQuantities itemQuantity : itemsQuantities) {
            MenuItem item = itemQuantity.getItem();
            sb.append("Name: ").append(item.getName())
              .append(", Quantity: ").append(itemQuantity.getQuantity())
              .append(" - $").append(String.format("%.2f", item.getPrice()))
              .append("\n");
        }
        sb.append("Total Amount: $").append(String.format("%.2f", getAmount()));
        return sb.toString();
    }
    
    public int getOrderID() {
        return orderID;
    }

    public List<OrderQuantities> getItems() {
        return itemsQuantities;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public char getTakeaway() {
        return takeawayOption;
    }

    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    public float getAmount() {
        return totalAmount;
    }

    public long getTime() {
        return orderTime;
    }

    public void addItem(MenuItem item, int quantity) {
        for (OrderQuantities mq : itemsQuantities) {
            if (mq.getItem().equals(item)) {
                mq.setQuantity(mq.getQuantity() + quantity);
                return;
            }
        }
        itemsQuantities.add(new OrderQuantities(item, quantity));
    }

    public void removeItem(MenuItem item, int quantity) {
        for (OrderQuantities mq : itemsQuantities) {
            if (mq.getItem().equals(item)) {
                if (mq.getQuantity() > quantity) {
                    mq.setQuantity(mq.getQuantity() - quantity);
                } else {
                    itemsQuantities.remove(mq);
                }
                return;
            }
        }
    }

    public void removeItem(MenuItem item) {
        itemsQuantities.removeIf(mq -> mq.getItem().equals(item));
    }

    public char setTakeawayOption(char takeawayOption) {
        return this.takeawayOption = takeawayOption;
    }

    public void setPaymentStatusTrue() {
        this.paymentStatus = true;
    }

}

