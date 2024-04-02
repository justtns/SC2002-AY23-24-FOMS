package main.backend.entities;

public class MenuItem {
    private String name;
    private String category;
    private String branch;
    private Float price;

    public MenuItem(String name, String category, String branch, Float price) {
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

    public Float getPrice() {
        return price;
    }
}
