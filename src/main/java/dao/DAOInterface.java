package main.java.dao;
import java.util.List;

public interface DAOInterface <T> {
    public void readData();
    public void saveData();
    public List<T> getElements();
    public T findElement(String elementName);
    public void updateElement(T oldElement, T newElement);
    public void removeElement(String elementName);
    public void addElement(T element);
}
