package main.java.handlers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import main.java.entities.MenuItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.FileOutputStream;


public class MenuHandler implements HandlerInterface<MenuItem>{
    private List<MenuItem> menu;
    
    public MenuHandler() {
        this.menu = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(new File("src\\main\\resources\\xlsx\\menu_list.xlsx"));
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getRowNum() == 0){
                        continue;
                    }
                    if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null) {
                        continue; 
                    }
                    String name = row.getCell(0).getStringCellValue();
                    Float price = (float) row.getCell(1).getNumericCellValue();
                    String itemBranch = row.getCell(2).getStringCellValue();
                    String category = row.getCell(3).getStringCellValue();
                    
                    MenuItem item = new MenuItem(name, category, itemBranch, price);
                    this.menu.add(item);
                }
            }

        catch (IOException e){
            e.printStackTrace();
        }

        if (this.menu.size() == 0){
            System.out.println("No menus available.");
        }
    }
    
    public void displayMenu(String branch) {
        System.out.println("Menu Items: ");
        for (MenuItem item : this.menu) {
            if (item.getBranch().equalsIgnoreCase(branch)) {
                System.out.println(item.getName() + ", Category: " + item.getCategory() + ", Price: $" + item.getPrice() + ", Branch: " + item.getBranch());
            }
        }
    }

    @Override
    public void listElement() {
        System.out.println("Full Menu Items: ");
        for (MenuItem item : this.menu) {
            System.out.println(item.getName() + ", Category: " + item.getCategory() + ", Price: $" + item.getPrice());
        }
    }

    @Override
    public boolean addElement(MenuItem newItem) {
        for (MenuItem item : this.menu) {
            if ((item.getName().equalsIgnoreCase(newItem.getName())) && (item.getBranch().equalsIgnoreCase(newItem.getBranch())))   {
                System.out.println("Item with name " + newItem.getName() + " already exists in " + newItem.getBranch() + " .");
                return false;
            }
        }
        menu.add(newItem);
        return true;
    }

    @Override
    public boolean updateElement(MenuItem oldItem, MenuItem newItem) {
        Boolean found = false;
        for (int i = 0; i<menu.size(); i++){
            if ((menu.get(i).getName().equalsIgnoreCase(oldItem.getName())) && menu.get(i).getBranch().equalsIgnoreCase(oldItem.getBranch())){
                menu.set(i, newItem);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Item with name " + oldItem.getName() + " in branch " + oldItem.getBranch() + " not found.");
        }
        return found;
    }

    @Override
    public boolean removeElement(MenuItem menuItem) {
        Boolean found = false;
        for (int i = 0; i<menu.size(); i++){
            if ((menu.get(i).getName().equalsIgnoreCase(menuItem.getName())) && menu.get(i).getBranch().equalsIgnoreCase(menuItem.getBranch())){
                menu.remove(i);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Item with name " + menuItem.getName() + " in branch " + menuItem.getBranch() + " not found.");
        }
        return found;
    }

    public MenuItem findElementById(String name, String branch) {
        for (MenuItem item : menu) {
            if (item.getName().equalsIgnoreCase(name) && item.getBranch().equalsIgnoreCase(branch)) {
                return item;
            }
        }
        return null;
    }

    public void writeMenuToFile() {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src\\main\\resources\\xlsx\\payment_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Menu Items");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Name", "Price", "Branch", "Category"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (MenuItem item : this.menu) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(item.getName());
                row.createCell(1).setCellValue(item.getPrice());
                row.createCell(2).setCellValue(item.getBranch());
                row.createCell(3).setCellValue(item.getCategory());
            }

            workbook.write(outputStream);
            System.out.println("Excel file was updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
    }
}
