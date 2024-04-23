package main.java.models;

/**
 * The Branch class is a business object that contains all necessary accessors and mutators for CRUD Operations performed by BranchDAO.
 * Branch class is in a 'has-a' relation with BranchDAO (Composition).
 * It contains information such as the name, capacity, and location of the branch.
 *
 * @author SDDA Team 1
 * @version 1.1
 * @since 23-Apr-2024
 */
public class Branch {
    
    /** The name of the branch. */
    private String name;
    
    /** The maximum capacity of the branch. */
    private int capacity;
    
    /** The location of the branch. */
    private String location;

    /**
     * Constructor that constructs a new Branch with the specified name, location, and capacity.
     *
     * @param name the name of the branch
     * @param location the location of the branch
     * @param capacity the maximum capacity of the branch
     */
    public Branch(String name, String location, int capacity){
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    /**
     * Gets the location of the branch.
     *
     * @return the location of the branch
     */
    public String getLocation(){
        return location;
    }

    /**
     * Sets the location of the branch.
     *
     * @param location the new location of the branch
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Gets the name of the branch.
     *
     * @return the name of the branch
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the branch.
     *
     * @param name the new name of the branch
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the maximum capacity of the branch.
     *
     * @return the maximum capacity of the branch
     */
    public int getCapacity(){
        return capacity;
    }

    /**
     * Sets the maximum capacity of the branch.
     *
     * @param capacity the new maximum capacity of the branch
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
}
