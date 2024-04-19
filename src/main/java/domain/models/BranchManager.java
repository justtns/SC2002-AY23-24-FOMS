package main.java.domain.models;
import main.java.domain.types.Role;

public class BranchManager extends Staff {
    // Additional attributes or methods specific to BranchManager can be added here


    public BranchManager(String name,  String password,  String gender, int age, String branch) {
        super(name,  password, Role.Manager, gender, age, branch);
    }
    public BranchManager(String name,String id,  String password,  String gender, int age, String branch) {
        super(name,id,  password, Role.Manager, gender, age, branch);
    }
}
