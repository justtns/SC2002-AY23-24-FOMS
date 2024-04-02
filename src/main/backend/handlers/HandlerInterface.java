package main.backend.handlers;

public interface HandlerInterface {
    public void addElement(String... parts);
    public void listElement();
    public void updateElement(String... parts);
    public void removeElement(String... parts);
}
