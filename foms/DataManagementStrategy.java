

public interface DataManagementStrategy<T> {

    void addItem(T item);
    void removeItem(String itemName);
    T findItem(String itemName);
}
