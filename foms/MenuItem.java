
public class MenuItem {
    private String name;
    private String category;
    private double price;
    private String branchName;
    private boolean available;
    private String description;

    public String getDescription() {
        return description;
    }

    public MenuItem(String name, String category, double price, String branchName, boolean available, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.branchName = branchName;
        this.available = available;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public boolean isAvailable() {
        return available;
    }



    public void setAvailable(boolean available) {
        this.available = available;
    }


}
