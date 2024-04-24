package main.java.boundaries;

/**
 * The Form interface represents a generic form in the Fastfood Ordering and Management System (FOMS).
 * This interface will be implemented by all forms in this project, and the generateForm abstract method will
 * overridden in all forms that implement this interface.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public interface Form {
    /**
     * Generates the form based on the specific functionality defined in implementing classes.
     */
    public void generateForm();
}