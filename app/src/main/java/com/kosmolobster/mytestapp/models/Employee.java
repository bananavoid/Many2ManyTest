package com.kosmolobster.mytestapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.kosmolobster.mytestapp.db.Repo;

public class Employee{

    @DatabaseField(generatedId = true)
    public String name;

    public Employee() {
        // needed by ormlite
    }

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname) {
        this.name = firstname;
    }

    public int save(Repo repo){
        return repo.employeeRepo.create(this);
    }
}
