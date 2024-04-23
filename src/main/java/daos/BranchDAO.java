package main.java.daos;

import main.java.models.Branch;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * BranchDAO is implemented from DAOInterface 
 * BranchDAO is a Data Access Object (DAO) for managing Branch objects.
 * It provides methods to interact with Branch data stored in an Excel file.
 * 
 * @author SDDA Team 1
 * @version 1.5
 * @since 23-Apr-2024
 */
public class BranchDAO implements DAOInterface<Branch>{
    /**
     * List to hold Branch objects.
     */
    private List<Branch> BranchList;

    /**
     * Constructor for BranchDAO.
     * Initializes BranchList and reads data from the Excel file.
     * Handles IOException if encountered while reading the file.
     */
    public BranchDAO(){
        readData();
    }
    
    /**
     * Reads data from the Excel file and populates the BranchList.
     * If the file does not exist, it creates a new Excel file.
     * Handles IOException if encountered while reading or creating the file.
     */
    @Override
    public void readData(){
        BranchList = new ArrayList<>();
        String filePath = "src/main/resources/xlsx/branch_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Branches");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Name", "Location", "Capacity"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    workbook.write(outputStream);
                }
            } catch (IOException e) {
                // Exception encountered while creating the file
                e.printStackTrace();
            }
        }

        try (FileInputStream inputStream = new FileInputStream(new File("src/main/resources/xlsx/branch_list.xlsx"));
        Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getRowNum() == 0){
                        continue;
                    }
                    if (row.getCell(0) == null || row.getCell(1) == null) {
                        continue; 
                    }
                    String name = row.getCell(0).getStringCellValue();
                    String location = row.getCell(1).getStringCellValue();
                    int capacity = (int) row.getCell(2).getNumericCellValue();
                    
                    Branch branch = new Branch(name, location, capacity);
                    addElement(branch);
                }
            } catch (IOException e){
                // Exception encountered while reading the file
                e.printStackTrace();
            }

        if (BranchList.size() == 0){
            System.out.println("No Branches available.");
        }
    };

    /**
     * Saves the data from BranchList to an Excel file.
     * If the file does not exist, it creates a new one.
     * Handles IOException if encountered while writing the file.
     */
    @Override
    public void saveData(){
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src/main/resources/xlsx/branch_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Branches");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Name", "Location", "Capacity"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (Branch branch : BranchList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(branch.getName());
                row.createCell(1).setCellValue(branch.getLocation());
                row.createCell(2).setCellValue(branch.getCapacity());
            }
            workbook.write(outputStream);
            System.out.println("Excel file was updated successfully.");
        } catch (IOException e) {
            // Exception encountered while writing the file
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
        readData();
    };
    
    /**
     * Returns the list of Branches stored in BranchList.
     * @return List of Branches
     */
    @Override
    public List<Branch> getElements(){
        return this.BranchList;
    }
    
    /**
     * Finds and returns a Branch by its name.
     * @param branchName The name of the Branch to find
     * @return The found Branch, or null if not found
     */
    @Override
    public Branch findElement(String branchName){
        int findIndex=-1;
        for (int i = 0; i< BranchList.size(); i++)
        {
            if(BranchList.get(i).getName().equalsIgnoreCase(branchName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return BranchList.get(findIndex);
        return null;
    }

    /**
     * Updates an existing Branch with new data.
     * @param oldElement The old Branch object to update
     * @param newElement The new Branch object containing updated data
     */
    @Override
    public void updateElement(Branch oldElement, Branch newElement){
        int updateIndex=-1;
        for (int i = 0; i< BranchList.size(); i++)
        {
            if((BranchList.get(i).getName().equalsIgnoreCase(oldElement.getName())))
            {
                updateIndex=i;
                break;
            }
        }
        if(updateIndex!=-1){
            BranchList.set(updateIndex, newElement);
        }
        return;
    };

    /**
     * Removes a Branch from the list by its name.
     * @param elementName The name of the Branch to remove
     */
    @Override
    public void removeElement(String elementName){
        int updateIndex=-1;
        for (int i = 0; i< BranchList.size(); i++)
        {
            if((BranchList.get(i).getName().equalsIgnoreCase(elementName)))
            {
                updateIndex=i;
                break;
            }
        }
        if(updateIndex!=-1){
            BranchList.remove(updateIndex);
        }
        return;
    }

    /**
     * Adds a new Branch to the list, if it doesn't already exist.
     * @param element The Branch object to add
     */
    @Override
    public void addElement(Branch element){
        //checking for duplicate
        if (findElement(element.getName()) != null) {
            System.err.println("Error: A Branch with this name already exists.");
            return; 
        }
        
        BranchList.add(element);
        System.out.println("Branch added successfully.");
    }
}