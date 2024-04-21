package main.java.boundaries;

import main.java.utils.types.StaffRole;

public class StaffLogin {   
    public StaffRole staffRole;
    private String password;
    private String username;

    public boolean enterUsername(String username) {
        // Logic to enter the username
        // This could check if the username is valid, for the purpose of this example, we assume it is
        this.username = username;
        return true; // returns true if the username is successfully entered
    }

    public boolean enterPassword(String password) {
        // Logic to enter the password
        // This could check if the password is valid, for the purpose of this example, we assume it is
        this.password = password;
        return true; // returns true if the password is successfully entered
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        // Logic to change the password
        // This could check if the old password matches, for the purpose of this example, we assume it matches
        if (this.password.equals(oldPassword)) {
            this.password = newPassword;
            return true; // returns true if the password is successfully changed
        }
        return false; // returns false if the old password doesn't match
    }

    // Additional getters and setters as needed...
}