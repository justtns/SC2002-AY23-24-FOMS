package main.java.deprecated;

import main.java.models.MenuItem;

public class OrderItem {

    private MenuItem item;
    private int quantity;
    private String customisations;

    public OrderItem(MenuItem item, int quantity, String customisations) {
        this.item = item;
        this.quantity = quantity;
        this.customisations = customisations;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomisations() {
        return customisations;
    }

    public void setCustomisations(String customisations) {
        this.customisations = customisations;
    }

    @Override
    public String toString() {
        return "Item: " + item.getName() + ", Quantity: " + quantity + ", Price: $" + String.format("%.2f", item.getPrice() + ", Customisations:" + customisations);
    }

}
