package com.kosmolobster.mytestapp.models;

import com.orm.SugarRecord;

public class Employee extends SugarRecord<Employee> {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public Employee(){
        //don't remove - orm requires
    }

    public Employee(String name){
        this.name = name;
    }
}
