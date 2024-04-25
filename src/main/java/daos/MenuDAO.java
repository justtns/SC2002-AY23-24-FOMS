package main.java.daos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import main.java.models.MenuItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * MenuDAO is a Data Access Object (DAO) for managing MenuItem objects with CRUD Operations.
 * It provides methods to interact with MenuItem data stored in Excel files.
 * This class implements the DAOInterface.
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class MenuDAO implements DAOInterface<MenuItem> {

    /** List to hold MenuItem objects. */
    private static List<MenuItem> menuItemList;
    
    /**
     * Constructor for MenuDAO.
     * Initializes menuItemList and reads data from Excel files.
     */
    public MenuDAO() {
        readData();
    }

    /**
     * Reads data from Excel files and populates the menuItemList.
     * If the files do not exist, it creates new Excel files.
     * Handles IOException if encountered while reading or creating the files.
     */
    @Override
    public void readData() {
        menuItemList = new ArrayList<>();
        String filePath = "src/main/resources/xlsx/order_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Orders");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Name", "Price", "Branch", "Category", "Description", "Is Available"};
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
                    if (row.getRowNum() == 0| row.getCell(0).getStringCellValue().isEmpty()){
                        continue;
                    }
                    if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null || row.getCell(3) == null) {
                        continue; 
                    }
                    String name = row.getCell(0).getStringCellValue();
                    Float price = (float) row.getCell(1).getNumericCellValue();
                    String itemBranch = row.getCell(2).getStringCellValue();
                    String category = row.getCell(3).getStringCellValue();
                    String description = row.getCell(4).getStringCellValue();
                    Boolean available = row.getCell(4).getBooleanCellValue();
                    
                    MenuItem item = new MenuItem(name, category, itemBranch, description, price, available);
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

    /**
     * Saves the data from menuItemList to Excel files.
     * If the files do not exist, it creates new ones.
     * Handles IOException if encountered while writing the files.
     */
    @Override
    public void saveData() {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src/main/resources/xlsx/menu_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Menu Items");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Name", "Price", "Branch", "Category", "Description", "Is Available"};
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
                row.createCell(4).setCellValue(item.getDescription());
                row.createCell(4).setCellValue(item.isAvailable());
            }

            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
    };
    
    /**
     * Returns the list of MenuItems stored in menuItemList.
     * @return List of MenuItems
     */
    @Override
    public List<MenuItem> getElements() {
        return menuItemList;
    }

    /**
     * Finds and returns a MenuItem by its name.
     * @param elementName The Name of the MenuItem to find
     * @return The found MenuItem, or null if not found
     */
    @Override
    public MenuItem findElement(String elementName) {
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

    /**
     * Finds and returns a MenuItem by its name and branch. Overloaded with previous findElement(String elementName).
     * @param elementName The Name of the MenuItem to find
     * @param branchName The Branch of the MenuItem to find
     * @return The found MenuItem, or null if not found
     */
    public static MenuItem findElement(String elementName, String branchName) {
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

    /**
     * Updates an existing MenuItem with new data.
     * @param oldElement The old MenuItem object to update
     * @param newElement The new MenuItem object containing updated data
     */
    @Override
    public void updateElement(MenuItem oldElement, MenuItem newElement) {
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

    /**
     * Removes a MenuItem from the list by its name.
     * @param elementName The Name of the MenuItem to remove
     */
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

    /**
     * Removes a MenuItem from the list by its name and branch. Overloaded with previous removeElement(String elementName).
     * @param elementName The Name of the MenuItem to remove
     * @param branchName The Branch of the MenuItem to remove
     */
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

    /**
     * Adds a new MenuItem to the list.
     * @param element The MenuItem object to add
     */
    @Override
    public void addElement(MenuItem element) {
        //checking for duplicate
        if (findElement(element.getName(), element.getBranch()) != null) {
            System.err.println("Error: A menu item with this name and branch already exists.");
            return; 
        }
        
        menuItemList.add(element);
    }
}