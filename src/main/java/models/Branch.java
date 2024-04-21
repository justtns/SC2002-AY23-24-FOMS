package main.java.models;

import java.util.List;

public class Branch {
    private String name;
    private int capacity;
    private String location;

    public Branch(String name, String location, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }
}
