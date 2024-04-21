package main.java.boundaries;

import main.java.utils.types.LoginRole;
import main.java.utils.types.StaffRole;

public class StaffApp implements AppDisplay {
    private Role loginRole;
    private StaffRole staffRole;

    @Override
    public void enterRole(LoginRole role) {
        this.loginRole = role;
    }

    public void executeLogin() {
        StaffLogin staffLogin = new StaffLogin();


    }
}
