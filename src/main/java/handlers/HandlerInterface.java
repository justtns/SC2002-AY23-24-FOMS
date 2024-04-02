package main.java.handlers;

public interface HandlerInterface <T> {
    public void addElement(T element);
    public void listElement();
    public void updateElement(T oldElement, T newElement);
    public void removeElement(T element);
}
