package main.java.domain.models;

import main.java.domain.types.PaymentType;

public class PaymentMethod {
    private String name;
    private PaymentType type;

    public PaymentMethod(String name, PaymentType type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public PaymentType getType(){
        return type;
    }

    public void setType(PaymentType type){
        this.type = type;
    }
}