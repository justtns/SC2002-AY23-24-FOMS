package main.java.utils.loggers;

import main.java.utils.types.StaffRole;

public class StaffSession {
    private String staffUserID;
    private StaffRole staffRole;

    public StaffSession(String staffUserID, StaffRole staffRole) {
        this.staffUserID = staffUserID;
        this.staffRole = staffRole;
    }

    public String getStaffUserID() {
        return staffUserID;
    }

    public StaffRole getStaffRole() {
        return staffRole;
    }
}
