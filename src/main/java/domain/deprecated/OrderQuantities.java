package main.java.domain.deprecated;

import main.java.domain.models.MenuItem;

public class OrderQuantities{
    private MenuItem item;
    private int quantity;

    public OrderQuantities(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
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

 }
