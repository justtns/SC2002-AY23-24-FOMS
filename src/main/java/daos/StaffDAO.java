package main.java.daos;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

import main.java.models.Staff;
import main.java.utils.types.StaffRole;

/**
 * StaffDAO is implemented from DAOInterface.
 * StaffDAO is a Data Access Object (DAO) for managing Staff objects with CRUD Operations.
 * It provides methods to interact with Staff data stored in Excel files.
 * 
 * @author SDDA Team 1
 * @version 1.3
 * @since 23-Apr-2024
 */
public class StaffDAO implements DAOInterface<Staff>{
    /**
     * List to hold Staff objects.
     */
    private List<Staff> staffList=new ArrayList<>();

    /**
     * Constructor for StaffDAO.
     * Initializes staffList and reads data from Excel files.
     */
    public StaffDAO(){
        readData();
    }

/**
 * Reads data from Excel files and populates the staffList.
 * If the files do not exist, it creates new Excel files with headers.
 * Handles IOException if encountered while reading or creating the files.
 */
@Override
public void readData() {
    staffList = new ArrayList<>();
    String filePath = "src/main/resources/xlsx/staff_list.xlsx";
    File file = new File(filePath);
    
    // Check if the file exists, if not, create it and add headers
    if (!file.exists()) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Staff");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Name", "ID", "Password", "Role", "Gender", "Age", "Branch"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            System.out.println("Failed to create new Excel file for staff data: " + e.getMessage());
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
                    String name = row.getCell(0).getStringCellValue();
                    String id = row.getCell(1).getStringCellValue();
                    String password;
                    if(row.getCell(2) == null || row.getCell(2).getStringCellValue().isEmpty()) {
                        password = "password";
                    } else {
                        password = row.getCell(2).getStringCellValue();
                    }
                    String roleString = row.getCell(3).getStringCellValue();
                    String gender = row.getCell(4).getStringCellValue();
                    int age = (int) row.getCell(5).getNumericCellValue();
                    String branch;
                    if(row.getCell(6) == null || row.getCell(6).getStringCellValue().isEmpty()) {
                        branch = "NA";
                    } else {
                        branch = row.getCell(6).getStringCellValue();
                    }

                    StaffRole role;
                    switch (roleString) {
                        case "S":
                            role = StaffRole.Staff;
                            break;
                        case "M":
                            role = StaffRole.Manager;
                            break;
                        case "A":
                            role = StaffRole.Admin;
                            break;
                        default:
                            role = StaffRole.Staff; // Default to Staff if role is undefined
                            break;
                    }

                    // Creating entry into staff list
                    staffList.add(new Staff(name, id, password, role, gender, age, branch));

                }
    } catch (IOException e) {
        System.out.println("Failed to read staff data from Excel file: " + e.getMessage());
        e.printStackTrace();
    }
    if (staffList.size() == 0) {
        System.out.println("No staff records found.");
    }
}

/**
 * Saves the data from staffList to Excel files.
 * If the files do not exist, it creates new ones with headers.
 * Handles IOException if encountered while writing the files.
 */
public void saveData() {
    String filePath = "src/main/resources/xlsx/staff_list.xlsx";
    File file = new File(filePath);

    try (Workbook workbook = new XSSFWorkbook();
        FileOutputStream outputStream = new FileOutputStream(file)) {
        Sheet sheet = workbook.createSheet("Staff");

        // Create headers in the Excel file
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Name", "ID", "Password", "Role", "Gender", "Age", "Branch"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Populate the Excel file with staff data
        int rowIndex = 1;
        for (Staff member : staffList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(member.getName());
            row.createCell(1).setCellValue(member.getLoginID());
            row.createCell(2).setCellValue(member.getPassword());
            row.createCell(3).setCellValue((member.getRole().toString()));
            row.createCell(4).setCellValue(member.getGender());
            row.createCell(5).setCellValue(member.getAge());
            row.createCell(6).setCellValue(member.getBranch());
        }
        workbook.write(outputStream);
        System.out.println("Staff data saved successfully.");
    } catch (IOException e) {
        System.out.println("Failed to save staff data: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Retrieves the list of Staff objects.
     * @return List of Staff objects
     */
    @Override
    public List<Staff> getElements() {
        return staffList;
    }

    /**
     * Adds a new Staff object to the staffList.
     * @param item The Staff object to add
     */
    @Override
    public void addElement(Staff item) {
        staffList.add(item);
    }

    /**
     * Removes a Staff object from the staffList by its name.
     * @param itemName The name of the Staff object to remove
     */
    @Override
    public void removeElement(String itemName) {
        int deleteIndex=-1;
        for (int i=0;i<staffList.size();i++)
        {
            if(staffList.get(i).getName().equalsIgnoreCase(itemName))
            {
                deleteIndex=i;
                break;

            }
        }
        if(deleteIndex!=-1)
            staffList.remove(deleteIndex);

    }

    /**
     * Finds and returns a Staff object by its login ID.
     * @param itemName The login ID of the Staff object to find
     * @return The found Staff object, or null if not found
     */
    @Override
    public Staff findElement(String itemName) {
        int findIndex=-1;
        for (int i=0;i<staffList.size();i++)
        {
            if(staffList.get(i).getLoginID().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return staffList.get(findIndex);
        return null;
    }    

    /**
     * Updates an existing Staff object with new data.
     * @param oldElement The old Staff object to update
     * @param newElement The new Staff object containing updated data
     */
    @Override
    public void updateElement(Staff oldElement, Staff newElement) {
        int findIndex=-1;
        for (int i=0;i<staffList.size();i++)
        {
            if(staffList.get(i).getName().equalsIgnoreCase(oldElement.getName()))
            {
                findIndex=i;
                break;
            }
        }
        if(findIndex!=-1)
            staffList.set(findIndex, newElement);
    }    
}