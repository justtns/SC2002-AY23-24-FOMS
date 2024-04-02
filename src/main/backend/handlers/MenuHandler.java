package main.backend.handlers;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import main.backend.entities.MenuItem;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MenuHandler implements HandlerInterface{
    private List<MenuItem> menu;
    
    public MenuHandler() {
        this.menu = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File("../db/menu_list.xlsx"));
             Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getRowNum() == 0) continue;
                    String name = row.getCell(0).getStringCellValue();
                    Float price = (float) row.getCell(1).getNumericCellValue();
                    String item_branch = row.getCell(2).getStringCellValue();
                    String category = row.getCell(3).getStringCellValue();
                    
                    MenuItem item = new MenuItem(name, category, item_branch, price);
                    this.menu.add(item);
                }
            }

        catch (Exception e){
            e.printStackTrace();
        }

        if (this.menu.size() == 0){
            throw new IllegalArgumentException("Menu is empty.");
        }
    }

    public void displayMenu(String branch) {
        System.out.println("Menu Items: ");
        for (MenuItem item : this.menu) {
            if (item.getBranch().equals(branch)) {
                System.out.println(item.getName() + ", Category: " + item.getCategory() + ", Price: $" + item.getPrice() + ", Branch: " + item.getBranch());
            }
        }
    }
    public void listElement() {
        System.out.println("Full Menu Items: ");
        for (MenuItem item : this.menu) {
            System.out.println(item.getName() + ", Category: " + item.getCategory() + ", Price: $" + item.getPrice());
        }
    }

    public void addElement(MenuItem newItem) {
        for (MenuItem item : this.menu) {
            if ((item.getName().equals(newItem.getName())) && (item.getBranch().equals(newItem.getBranch())))   {
                throw new IllegalArgumentException("Item with name " + newItem.getName() + " already exists in " + newItem.getBranch() + " .");
            }
        }
        menu.add(newItem);
    }

    public void updateElement(String itemName, String branch) {
        boolean found = false;
        for (int i = 0; i<menu.size(); i++){
            if ((menu.get(i).getName().equals(itemName)) && menu.get(i).getBranch().equals(branch)){
                
                found = true;
                break;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("Item with name " + itemName + " in branch " + branch + " not found.");
        }
    }

    public void removeElement() {

    }
}
