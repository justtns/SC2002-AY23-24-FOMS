import java.util.ArrayList;
import java.util.List;

public class Branch {
    private String branchName;
    private String location;
    private int totalStaff;

    private List<Staff> staffList;
    private List<MenuItem> itemList;

    public Branch(String branchName, String location, int totalStaff) {
        this.branchName = branchName;
        this.location = location;
        this.totalStaff = totalStaff;
        staffList = new ArrayList<>();
        itemList=new ArrayList<>();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(int totalStaff) {
        this.totalStaff = totalStaff;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    // Method to add a staff member to the branch
    public void addStaff(Staff staff) {
        staffList.add(staff);
        totalStaff++;
    }

    // Method to remove a staff member from the branch
    public void removeStaff(String loginID) {
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getLoginID().equals(loginID)) {
                staffList.remove(i);
                totalStaff--;
                break;
            }
        }
    }

    // Method to find a staff member by their login ID
    public Staff findStaff(String loginID) {
        for (Staff staff : staffList) {
            if (staff.getLoginID().equals(loginID)) {
                return staff;
            }
        }
        return null; // Staff not found
    }
    // Method to add a menu item to the branch
    public void addMenuItem(MenuItem menuItem) {
        itemList.add(menuItem);
    }

    // Method to remove a menu item from the branch
    public void removeMenuItem(String itemName) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(itemName)) {
                itemList.remove(i);
                break;
            }
        }
    }

    // Method to find a menu item by its name
    public MenuItem findMenuItem(String itemName) {
        for (MenuItem menuItem : itemList) {
            if (menuItem.getName().equals(itemName)) {
                return menuItem;
            }
        }
        return null; // Menu item not found
    }

}
