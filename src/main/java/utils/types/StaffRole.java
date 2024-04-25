package main.java.utils.types;

/**
 * Enum representing the roles of staff members.
 * This is used during StaffApp, StaffAuthenticationController and StaffDAO, 
 * where the role will be verified against the stored data in Staff Excel.
 *
 * @author SDDA Team 1
 * @version 1.2
 * @since 24-Apr-2024
 */
public enum StaffRole {
    
    /** Represents Staff Employee at the fast-food company */
    STAFF, 
    
    /** Represents Manager Employee at the fast-food company */
    MANAGER, 
    
    /** Represents Admin Employee at the fast-food company */
    ADMIN
}