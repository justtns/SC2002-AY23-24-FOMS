package main.backend.handlers;

import main.backend.entities.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuHandler implements HandlerInterface<MenuItem> {
    private List<MenuItem> menuItems;

    public MenuHandler() {
        this.menuItems = new ArrayList<>();
        addBaseMenuItems();
    }

    private void addBaseMenuItems() {
        menuItems.add(MenuItem.BOILED_CHICKEN);
        menuItems.add(MenuItem.STEAMED_CHICKEN);
        menuItems.add(MenuItem.FRIED_CHICKEN);
        menuItems.add(MenuItem.GRILLED_CHICKEN);
    }

    @Override
    public void addElement(MenuItem item) {
        menuItems.add(item);
    }

    public void addElement(String itemID, String name, String description, float price) {
        MenuItem item = new MenuItem(itemID, name, description, price);
        addElement(item); 
    }

    @Override
    public void removeElement(MenuItem item) {
        menuItems.removeIf(existingItem -> existingItem.getItemID().equals(item.getItemID()));
    }

    @Override
    public void updateElement(MenuItem oldItem, MenuItem newItem) {
        Optional<MenuItem> foundItem = menuItems.stream()
                .filter(existingItem -> existingItem.getItemID().equals(oldItem.getItemID()))
                .findFirst();
        
        foundItem.ifPresent(item -> {
            item.setName(newItem.getName());
            item.setDescription(newItem.getDescription());
            item.setPrice(newItem.getPrice());
        });
    }

    @Override
    public List<MenuItem> listElement() {
        return new ArrayList<>(menuItems);
    }

    public MenuItem findElementById(String itemID) {
        for (MenuItem item : menuItems) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null;
    }


}

