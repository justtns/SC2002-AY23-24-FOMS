    package main.java.controllers;

    import main.java.models.Staff;
    import main.java.daos.StaffDAO;

    public class StaffAuthenticationController {
        private StaffDAO staffDAO;

        public StaffAuthenticationController(StaffDAO staffDAO) {
            this.staffDAO = staffDAO;
        }

        public boolean authenticateUsername(String username) {
            Staff staff = staffDAO.findElement(username);
            return staff != null;
        }

        public boolean authenticatePassword(String username, String password) {
            Staff staff = staffDAO.findElement(username);
            if (staff != null) {
                return staff.getPassword().equals(password);
            }
            return false;
        }

        public boolean updatePassword(String username, String oldPassword, String newPassword) {
            if (authenticatePassword(username, oldPassword)) {
                Staff staff = staffDAO.findElement(username);
                if (staff != null) {
                    staff.setPassword(newPassword);
                    staffDAO.updateElement(staff, staff);
                    return true;
                }
            }
            return false;
        }
    }