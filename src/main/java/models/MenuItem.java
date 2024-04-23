package main.java.models;

/**
 * The MenuItem class represents a menu item in a restaurant.
 * It is a business object that contains all necessary accessors and mutators for CRUD Operations performed by MenuDAO.
 * MenuItem class is in a 'has-a' relation with MenuDAO (Composition).
 * It contains information such as the name, price, branch, category, availability, and description of each item in the menu
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class MenuItem {
    
    /** The name of the menu item. */
    private String name;
    
    /** The price of the menu item. */
    private double price;
    
    /** The branch associated with the menu item. */
    private String branch;
    
    /** The category of the menu item. */
    private String category;
    
    /** The availability status of the menu item. */
    private boolean isAvailable; 
    
    /** The description of the menu item. */
    private String description;

    /**
     * Constructs a new MenuItem with the specified name, category, branch, description, and price.
     * By default, the item is available.
     * 
     * @param name The name of the menu item
     * @param category The category of the menu item
     * @param branch The branch associated with the menu item
     * @param description The description of the menu item
     * @param price The price of the menu item
     */
    public MenuItem(String name, String category, String branch, String description, double price) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.isAvailable = true;
        this.description = description;
    }

    /**
     * Retrieves the description of the menu item.
     * 
     * @return The description of the menu item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the description of the menu item.
     * 
     * @param description The new description of the menu item
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Retrieves the name of the menu item.
     * 
     * @return The name of the menu item
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the price of the menu item.
     * 
     * @return The price of the menu item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves the branch associated with the menu item.
     * 
     * @return The branch associated with the menu item
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Retrieves the category of the menu item.
     * 
     * @return The category of the menu item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Checks if the menu item is available.
     * 
     * @return True if the menu item is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the name of the menu item.
     * 
     * @param name The new name of the menu item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the menu item.
     * 
     * @param price The new price of the menu item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the branch associated with the menu item.
     * 
     * @param branch The new branch associated with the menu item
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Sets the category of the menu item.
     * 
     * @param category The new category of the menu item
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the availability status of the menu item.
     * 
     * @param available The new availability status of the menu item
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Returns a string representation of the menu item.
     * 
     * @return A string representation of the menu item
     */
    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", branch='" + branch + '\'' +
                ", category='" + category + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}