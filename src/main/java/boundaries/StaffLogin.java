package main.java.boundaries;

import main.java.utils.types.StaffRole;
import main.java.controllers.StaffAuthenticationController;
import main.java.daos.StaffDAO;

public class StaffLogin {   
    public StaffRole staffRole;
    private StaffAuthenticationController authController = new StaffAuthenticationController(new StaffDAO());
    private String password;
    private String username;

    public boolean enterUsername(String username) {
        this.username = username;
        return authController.authenticateUsername(username);
    }

    public boolean enterPassword(String password) {
        this.password = password;
        return authController.authenticatePassword(username, password);
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(this.password)){
            return false;
        }
        this.password = newPassword;
        return authController.updatePassword(username, password, newPassword);
    }
}