package main.java.models;

import main.java.utils.types.StaffRole;

/**
 * The Staff class represents an employee, manager or admin working in the restaurant company.
 * It is a business object that contains all necessary accessors and mutators for CRUD Operations performed by StaffDAO.
 * Staff class is in a 'has-a' relation with StaffDAO (Composition).
 * It contains information such as the name, login ID, password, role, gender, age, and branch of the staff member.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 23-Apr-2024
 */
public class Staff {

    /** The name of the staff member. */
    private String name;
    
    /** The login ID of the staff member. */
    private String loginID;
    
    /** The password of the staff member. */
    private String password;
    
    /** The role of the staff member. */
    private StaffRole role;
    
    /** The gender of the staff member. */
    private String gender;
    
    /** The age of the staff member. */
    private int age;
    
    /** The branch where the staff member works. */
    private String branch;
    
    /** The counter to generate unique login IDs for staff members. */
    private static int count = 0;

    /**
     * Constructs a Staff object with the given parameters.
     * The login ID is generated automatically based on the name and a counter.
     * 
     * @param name The name of the staff member
     * @param loginId The login ID of the staff member
     * @param password The password of the staff member
     * @param role The role of the staff member
     * @param gender The gender of the staff member
     * @param age The age of the staff member
     * @param branch The branch where the staff member works
     */
    public Staff(String name, String loginId, String password, StaffRole role, String gender, int age, String branch) {
        this.name = name;
        this.password = password;
        this.loginID = loginId;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branch = branch;
    }

    /**
     * Constructs a Staff object with the given parameters.
     * The login ID is automatically generated based on the name and a counter.
     * 
     * @param name The name of the staff member
     * @param password The password of the staff member
     * @param role The role of the staff member
     * @param gender The gender of the staff member
     * @param age The age of the staff member
     * @param branch The branch where the staff member works
     */
    public Staff(String name, String password, StaffRole role, String gender, int age, String branch) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branch = branch;
        this.loginID = name + "" + count++;
    }

    /**
     * Retrieves the name of the staff member.
     * 
     * @return The name of the staff member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the staff member.
     * 
     * @param name The new name of the staff member
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the login ID of the staff member.
     * 
     * @return The login ID of the staff member
     */
    public String getLoginID() {
        return loginID;
    }

    /**
     * Sets the login ID of the staff member.
     * 
     * @param loginID The new login ID of the staff member
     */
    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    /**
     * Retrieves the password of the staff member.
     * 
     * @return The password of the staff member
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the staff member.
     * 
     * @param password The new password of the staff member
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the role of the staff member.
     * 
     * @return The role of the staff member
     */
    public StaffRole getRole() {
        return role;
    }

    /**
     * Sets the role of the staff member.
     * 
     * @param role The new role of the staff member
     */
    public void setRole(StaffRole role) {
        this.role = role;
    }

    /**
     * Retrieves the gender of the staff member.
     * 
     * @return The gender of the staff member
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the staff member.
     * 
     * @param gender The new gender of the staff member
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Retrieves the age of the staff member.
     * 
     * @return The age of the staff member
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the staff member.
     * 
     * @param age The new age of the staff member
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retrieves the branch where the staff member works.
     * 
     * @return The branch where the staff member works
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Sets the branch where the staff member works.
     * 
     * @param branch The new branch where the staff member works
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Provides a string representation of the staff member.
     * 
     * @return A string representation of the staff member
     */
    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", loginID='" + loginID + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", branch='" + branch + '\'' +
                '}';
    }
}