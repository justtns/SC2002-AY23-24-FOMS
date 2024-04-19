package main.java.domain.models;
import main.java.domain.types.Role;

public class Admin extends Staff {

    // Constructor
    public Admin(String name, String password, String gender, int age) {
        super(name,  password, Role.Admin, gender, age, "All Branches");
    }

    public Admin(String name,String id, String password, String gender, int age) {
        super(name,id,  password, Role.Admin, gender, age, "All Branches");
    }


}
