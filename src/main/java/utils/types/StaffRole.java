package main.java.utils.types;

/**
 * Enum representing the roles of staff members.
 * This is used during StaffAuthenticationController and StaffDAO, where the role will be verified against the stored data in Staff Excel.
 *
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public enum StaffRole {
    STAFF, MANAGER, ADMIN
}