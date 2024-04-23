/**
 * This interface defines the contract for Data Access Objects (DAOs) to interact with a data source.
 * It includes methods for reading and saving data, getting elements, finding, updating, removing, and adding elements.
 * 
 * @param <T> the type of elements this DAO operates on
 * @author SDDA Team 1
 * @version 1.1
 * @since 23-Apr-2024
 */
public interface DAOInterface<T> {
    /**
     * Reads data from the data source.
     */
    public void readData();

    /**
     * Saves data to the data source.
     */
    public void saveData();

    /**
     * Retrieves all elements from the data source.
     * 
     * @return a list of all elements
     */
    public List<T> getElements();

    /**
     * Finds an element by its name.
     * 
     * @param elementName the name of the element to find
     * @return the found element, or null if not found
     */
    public T findElement(String elementName);

    /**
     * Updates an existing element with new data.
     * 
     * @param oldElement the old element to be updated
     * @param newElement the new element with updated data
     */
    public void updateElement(T oldElement, T newElement);

    /**
     * Removes an element by its name.
     * 
     * @param elementName the name of the element to remove
     */
    public void removeElement(String elementName);

    /**
     * Adds a new element to the data source.
     * 
     * @param element the element to add
     */
    public void addElement(T element);
}