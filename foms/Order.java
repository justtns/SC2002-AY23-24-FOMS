
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private List<MenuItem> items;
    private boolean isDineIn;

    private boolean isCompleted;
    public static int count=111;
    private OrderStatus orderStatus;

    public enum OrderStatus
    {
        New , Ready,Completed
    }

    public Order( boolean isDineIn) {

        this.orderId =count++;
        items=new ArrayList<>();
        this.isDineIn = isDineIn;
        this.isCompleted = false;

    }

    public Order(int orderId, OrderStatus orderStatus, boolean isDineIn, boolean isCompleted) {
        this.orderId = orderId;
        this.isDineIn = isDineIn;
        this.isCompleted = isCompleted;
        this.orderStatus = orderStatus;
        this.items=new ArrayList<>();
    }

    public Order(List<MenuItem> items, boolean isDineIn) {
        this.orderId =count++;
        this.items = items;
        this.isDineIn = isDineIn;
        this.isCompleted = false;
        this.orderStatus=OrderStatus.New;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }



    public boolean isDineIn() {
        return isDineIn;
    }

    public void setDineIn(boolean dineIn) {
        isDineIn = dineIn;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    // Method to add a MenuItem to the order
    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    // Method to remove a MenuItem from the order
    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    // Method to find a MenuItem in the order by its name
    public MenuItem findItemByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null; // MenuItem not found
    }

    // Method to find a MenuItem in the order by its category
    public List<MenuItem> findItemsByCategory(String category) {
        List<MenuItem> foundItems = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getCategory().equals(category)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    // Method to calculate the total price of the order based on the menu items
    public double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (MenuItem item : items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }

}
