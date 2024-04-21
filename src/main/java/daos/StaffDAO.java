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

import main.java.models.Admin;
import main.java.models.BranchManager;
import main.java.models.Staff;
import main.java.utils.types.Role;

public class StaffDAO implements DAOInterface<Staff>{
    private List<Staff> staffList=new ArrayList<>();

    public StaffDAO(){
        readData();
    }

    @Override 
    public void readData(){
        staffList = new ArrayList<>(); // Assuming this is a list of staff members
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
                String id = row.getCell(1).getStringCellValue();
                String password;
                if(row.getCell(2) == null || row.getCell(2).getStringCellValue().isEmpty()) {
                    password = "DEFAULT";
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
    
                Role role;
                switch (roleString) {
                    case "S":
                        role = Role.Staff;
                        break;
                    case "M":
                        role = Role.Manager;
                        break;
                    case "A":
                        role = Role.Admin;
                        break;
                    default:
                        role = Role.Staff; // Default to Staff if role is undefined
                        break;
                }
    
                // Creating different staff types based on their role
                switch (role) {
                    case Staff:
                        staffList.add(new Staff(name, id, password, role, gender, age, branch));
                        break;
                    case Manager:
                        staffList.add(new BranchManager(name, id, password, gender, age, branch));
                        break;
                    case Admin:
                        staffList.add(new Admin(name, id, password, gender, age));
                        break;
                    default:
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        
            if (staffList.size() == 0) {
                System.out.println("No staff records found.");
            }
        }        

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
    
    private String getRoleString(Role role) {
        switch (role) {
            case Staff:
                return "S";
            case Manager:
                return "M";
            case Admin:
                return "A";
            default:
                return "Unknown";  // Handle unexpected role
        }
    }    

    @Override
    public List<Staff> getElements() {
        return staffList;
    }

    @Override
    public void addElement(Staff item) {
        staffList.add(item);
    }

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

    @Override
    public Staff findElement(String itemName) {
        int findIndex=-1;
        for (int i=0;i<staffList.size();i++)
        {
            if(staffList.get(i).getName().equalsIgnoreCase(itemName))
            {
                findIndex=i;
                break;

            }
        }
        if(findIndex!=-1)
            return staffList.get(findIndex);
        return null;
    }    

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
