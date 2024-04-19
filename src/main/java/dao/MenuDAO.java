package main.java.dao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.java.domain.models.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.FileOutputStream;

public class MenuDAO implements DAOInterface<MenuItem>{
    private List<MenuItem> menuItemList = new ArrayList<>();
    
    public MenuDAO(){
        readData();
    }

    @Override
    public void readData(){
        menuItemList = new ArrayList<>();
        String filePath = "src/main/resources/xlsx/order_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Orders");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Name", "Price", "Branch", "Category"};;
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

        try (FileInputStream inputStream = new FileInputStream(new File("src/main/resources/xlsx/menu_list.xlsx"));
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
                    addElement(item);
                }
            }


        catch (IOException e){
            e.printStackTrace();
        }

        if (menuItemList.size() == 0){
            System.out.println("No menus available.");
        }
    };

    @Override
    public void saveData(){
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src/main/resources/xlsx/payment_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Menu Items");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Name", "Price", "Branch", "Category"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (MenuItem item : menuItemList) {
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

        readData();
    };
    
    @Override
    public List<MenuItem> getElements(){
        return menuItemList;
    };

    @Override
    public MenuItem findElement(String elementName){
        int findIndex=-1;
        for (int i = 0; i< menuItemList.size(); i++)
        {
            if(menuItemList.get(i).getName().equalsIgnoreCase(elementName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return menuItemList.get(findIndex);
        return null;
    };

    public MenuItem findElement(String elementName, String branchName){
        int findIndex=-1;
        for (int i = 0; i< menuItemList.size(); i++)
        {
            if((menuItemList.get(i).getName().equalsIgnoreCase(elementName)) & (menuItemList.get(i).getBranch().equalsIgnoreCase(branchName)))
            {
                findIndex=i;
                break;
            }
        }
        if(findIndex!=-1)
            return menuItemList.get(findIndex);
        return null;
    };

    @Override
    public void updateElement(MenuItem oldElement, MenuItem newElement){
        int updateIndex=-1;
        for (int i = 0; i< menuItemList.size(); i++)
        {
            if((menuItemList.get(i).getName().equalsIgnoreCase(oldElement.getName())) & (menuItemList.get(i).getBranch().equalsIgnoreCase(oldElement.getBranch())))
            {
                updateIndex=i;
                break;
            }
        }
        if(updateIndex!=-1){
            menuItemList.set(updateIndex, newElement);
        }
        return;
    };

    @Override
    public void removeElement(String elementName) {
        int deleteIndex=-1;
        for (int i = 0; i< menuItemList.size(); i++)
        {
            if(menuItemList.get(i).getName().equalsIgnoreCase(elementName))
            {
                deleteIndex=i;
                break;
            }
        }
        if(deleteIndex!=-1)
            menuItemList.remove(deleteIndex);
    }

    public void removeElement(String elementName, String branchName) {
        int deleteIndex=-1;
        for (int i = 0; i< menuItemList.size(); i++)
        {
            if((menuItemList.get(i).getName().equalsIgnoreCase(elementName)) & (menuItemList.get(i).getBranch().equalsIgnoreCase(branchName)))
            {
                deleteIndex=i;
                break;
            }
        }
        if(deleteIndex!=-1)
            menuItemList.remove(deleteIndex);
    }

    @Override
    public void addElement(MenuItem element){
        menuItemList.add(element);
    };    
}
