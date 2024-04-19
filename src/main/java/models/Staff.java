package main.java.models;

import main.java.utils.types.Role;

public class Staff {

    private String name;
    private String loginID;
    private String password;
    private Role role;
    private String gender;
    private int age;
    private String branch;
    private static int count=0;

    public Staff(String name,String loginId,  String password, Role role, String gender, int age, String branch) {
        this.name = name;
        this.password = password;
        this.loginID=loginId;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branch = branch;

    }

    public Staff(String name,  String password, Role role, String gender, int age, String branch) {
        this.name = name;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.branch = branch;
        this.loginID=name+""+count++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                ", loginID='" + loginID + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", branch='" + branch + '\'' ;
    }
}
