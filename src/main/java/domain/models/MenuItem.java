package main.java.domain.models;

public class MenuItem {
    private String name;
    private String category;
    private String branch;
    private double price;

    public MenuItem(String name, String category, String branch, double price) {
        this.name = name;
        this.category = category;
        this.branch = branch;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getBranch() {
        return branch;
    }

    public double getPrice() {
        return price;
    }
}
