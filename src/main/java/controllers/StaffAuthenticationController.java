package main.java.controllers;

import main.java.models.Staff;
import main.java.daos.StaffDAO;

/**
 * The StaffAuthenticationController class provides methods for authenticating staff members.
 * It allows staff members to authenticate their username and password, as well as update their password.
 * This class interacts with the StaffDAO to retrieve and update staff information.
 * This class is part of a three-layer architecture, serving as the intermediary between the presentation layer (UI)
 * and the data access layer (DAO).
 * 
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public class StaffAuthenticationController {
    
    /** The Staff Data Access Object (DAO) for accessing staff information. */
    private StaffDAO staffDAO;

    /**
     * Constructs a new StaffAuthenticationController with the specified StaffDAO.
     *
     * @param staffDAO the StaffDAO used for staff data access
     */
    public StaffAuthenticationController(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    /**
     * Authenticates the provided username.
     *
     * @param username the username to authenticate
     * @return true if the username exists in the system, false otherwise
     */
    public boolean authenticateUsername(String username) {
        return staffDAO.findElement(username) != null;
    }

    /**
     * Authenticates the provided username and password combination.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return true if the username and password match a staff member in the system, false otherwise
     */
    public boolean authenticatePassword(String username, String password) {
        Staff staff = staffDAO.findElement(username);
        if (staff != null) {
            return staff.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Updates the password for the specified username if the old password matches.
     *
     * @param username the username for which to update the password
     * @param oldPassword the old password to verify
     * @param newPassword the new password to set
     * @return true if the password was successfully updated, false otherwise
     */
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        if (authenticatePassword(username, oldPassword)) {
            Staff staff = staffDAO.findElement(username);
            if (staff != null) {
                staff.setPassword(newPassword);
                staffDAO.updateElement(staffDAO.findElement(username), staff);
                return true;
            }
        }
        return false;
    }
}