package com.kosmolobster.mytestapp.models;

import com.orm.SugarRecord;

public class Company extends SugarRecord<Company> {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Company(){
        //don't remove - orm requires
    }

    public Company(String name){
        this.name = name;
    }
}
