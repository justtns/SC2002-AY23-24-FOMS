package main.java.utils.loggers;

import main.java.utils.types.StaffRole;

/**
 * A logger that logs the session for a staff user.
 * This session keeps track of the staff user ID and their role.
 * It provides methods to retrieve this information.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffSession {

    /** The ID of the staff user. */
    private String staffUserID;

    /** The role of the staff user. */
    private StaffRole staffRole;

    /**
     * Constructs a new StaffSession object with the given staff user ID and role.
     * 
     * @param staffUserID the ID of the staff user
     * @param staffRole the role of the staff user
     */
    public StaffSession(String staffUserID, StaffRole staffRole) {
        this.staffUserID = staffUserID;
        this.staffRole = staffRole;
    }

    /**
     * Retrieves the ID of the staff user.
     * 
     * @return the staff user ID
     */
    public String getStaffUserID() {
        return staffUserID;
    }

    /**
     * Retrieves the role of the staff user.
     * 
     * @return the staff user's role
     */
    public StaffRole getStaffRole() {
        return staffRole;
    }
}
