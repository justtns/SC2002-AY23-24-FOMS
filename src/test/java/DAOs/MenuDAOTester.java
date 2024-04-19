package test.java.DAOs;
import main.java.daos.MenuDAO;
import main.java.domain.models.MenuItem;

public class MenuDAOTester {
    public static void main(String[] args) {
        MenuDAO menuDao = new MenuDAO();

        // Test adding a menu item
        MenuItem newItem = new MenuItem("Pizza", "Food", "Main Branch", 15.99f);
        menuDao.addElement(newItem);
        System.out.println("Add MenuItem Test: " + (menuDao.getElements().contains(newItem) ? "Passed" : "Failed"));

        // Test finding a menu item
        MenuItem foundItem = menuDao.findElement("Pizza");
        System.out.println("Find MenuItem Test: " + (foundItem != null ? "Passed" : "Failed"));

        // Test updating a menu item
        MenuItem updatedItem = new MenuItem("Pizza", "Food", "Main Branch", 17.99f);
        menuDao.updateElement(newItem, updatedItem);
        foundItem = menuDao.findElement("Pizza");
        System.out.println("Update MenuItem Test: " + (foundItem.getPrice() == 17.99f ? "Passed" : "Failed"));

        // Test removing a menu item
        menuDao.removeElement("Pizza");
        foundItem = menuDao.findElement("Pizza");
        System.out.println("Remove MenuItem Test: " + (foundItem == null ? "Passed" : "Failed"));
    }
}
