package main.backend.entities;

public class MenuItem {
    private String itemID;
    private String name;
    private String description;
    private float price;

    public MenuItem(String itemID, String name, String description, float price) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    //static base menu - chicken menu items that are always available ? just for testing. 
    public static final MenuItem FRIED_CHICKEN = new MenuItem("C1", "Fried Chicken", "description of fried chicken", 5.99f);
    public static final MenuItem STEAMED_CHICKEN = new MenuItem("C2", "Steamed Chicken", "description of steamed chicken", 6.99f);
    public static final MenuItem BOILED_CHICKEN = new MenuItem("C3", "Boiled Chicken", "description of boiled chicken", 7.99f);
    public static final MenuItem GRILLED_CHICKEN = new MenuItem("C4", "Grilled Chicken", "description of grilled chicken", 8.99f);
    
    @Override
    public String toString() {
        return "[ItemID: " + itemID + "] " + name + ": " + description + " - $" + String.format("%.2f", price);
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getItemID() {
        return itemID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

}

