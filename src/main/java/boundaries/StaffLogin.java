package main.java.boundaries;

import main.java.utils.types.StaffRole;
import main.java.controllers.StaffAuthenticationController;
import main.java.daos.StaffDAO;

/**
 * The StaffLogin class represents the authentication process for staff users.
 * 
 * This class provides methods for entering username, password, changing password,
 * and verifying the user's role.
 * 
 * @author SDDA Team 1
 * @version 1.1
 * @since 24-Apr-2024
 */
public class StaffLogin {

    /**
     * The authentication controller for staff users.
     */
    private StaffAuthenticationController authController = new StaffAuthenticationController(new StaffDAO());
    
    /**
     * The password of the staff user.
     */
    private String password;

    /**
     * The username of the staff user.
     */
    private String username;

    /**
     * This method sets the username attribute and authenticates the username.
     * 
     * @param username The username to be authenticated.
     * @return true if the username is authenticated successfully, otherwise false.
     */
    public boolean enterUsername(String username) {
        this.username = username;
        return authController.authenticateUsername(username);
    }

    /**
     * This method sets the password attribute and authenticates the password
     * associated with the given username.
     * 
     * @param password The password to be authenticated.
     * @return true if the password is authenticated successfully, otherwise false.
     */
    public boolean enterPassword(String password) {
        this.password = password;
        return authController.authenticatePassword(username, this.password);
    }

    /**
     * This method changes the password for the current user if the old password
     * matches the current password.
     * 
     * @param username The username of the staff user.
     * @param oldPassword The old password to be replaced.
     * @param newPassword The new password to be set.
     * @return true if the password is changed successfully, otherwise false.
     */
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return authController.updatePassword(username, oldPassword, newPassword);
    }

    /**
     * This method verifies whether the user has the specified role.
     * 
     * @param role The role to be verified.
     * @return true if the user has the specified role, otherwise false.
     */
    public boolean verifyRole(StaffRole role){
        return authController.checkRole(username, role);
    }
}