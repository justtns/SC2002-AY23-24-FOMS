package main.java.models;
import main.java.utils.types.StaffRole;

public class Admin extends Staff {

    // Constructor
    public Admin(String name, String password, String gender, int age) {
        super(name,  password, StaffRole.Admin, gender, age, "NA");
    }

    public Admin(String name,String id, String password, String gender, int age) {
        super(name,id,  password, StaffRole.Admin, gender, age, "NA");
    }

}
