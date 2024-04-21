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

public class BranchDAO implements DAOInterface<Branch>{
    private List<Branch> BranchList;

    public BranchDAO(){
        readData();
    }
    public void readData(){
        BranchList = new ArrayList<>();
        String filePath = "src/main/resources/xlsx/branch_list.xlsx";
        File file = new File(filePath);
        
        if (!file.exists()) {
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Branches");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Name", "Capacity"};;
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
                    int capacity = (int) row.getCell(1).getNumericCellValue();
                    
                    Branch branch = new Branch(name, capacity);
                    addElement(branch);
                }
            }


        catch (IOException e){
            e.printStackTrace();
        }

        if (BranchList.size() == 0){
            System.out.println("No Branches available.");
        }
    };

    public void saveData(){
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream("src/main/resources/xlsx/payment_list.xlsx")) {
            Sheet sheet = workbook.createSheet("Menu Items");
            int rowIndex = 0;
            Row headerRow = sheet.createRow(rowIndex++);
            String[] headers = {"Name", "Capacity"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            for (Branch branch : BranchList) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(branch.getName());
                row.createCell(1).setCellValue(branch.getCapacity());
            }
            workbook.write(outputStream);
            System.out.println("Excel file was updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing Excel file");
            e.printStackTrace();
        }
        readData();
    };
    
    public List<Branch> getElements(){
        return this.BranchList;
    }
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