package main.backend.handlers;

import java.util.List;

public interface HandlerInterface<T> {
    void addElement(T element);
    void removeElement(T element);
    void updateElement(T oldElement, T newElement);
    List<T> listElement();
}

