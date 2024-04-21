    package main.java.controllers;

    import main.java.models.Staff;

    public class StaffAuthenticationController {

        public boolean authenticateUsername(String username, Staff staff) {
            return staff.getLoginID().equals(username);
        }

        public boolean authenticatePassword(String password, Staff staff) {
            return staff.getPassword().equals(password);
        }

        public boolean updatePassword(Staff staff, String oldPassword, String newPassword) {
            if (authenticatePassword(oldPassword, staff)) {
                staff.setPassword(newPassword);
                return true;
            }
            return false;
        }
    }