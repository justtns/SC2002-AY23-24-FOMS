package main.java.handlers;

public interface HandlerInterface <T> {
    public boolean addElement(T element);
    public void listElement();
    public boolean updateElement(T oldElement, T newElement);
    public boolean removeElement(T element);
    public void saveElement();
}
