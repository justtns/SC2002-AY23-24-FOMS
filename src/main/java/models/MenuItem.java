package main.java.models;

public class MenuItem {
    private String name;
    private double price;
    private String branch;
    private String category;
    private boolean isAvailable; 
    private String description;

    public MenuItem(String name, String category, String branch, String description, double price) {
        this.name = name;
        this.price = price;
        this.branch = branch;
        this.category = category;
        this.isAvailable = true;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getBranch() {
        return branch;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

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
