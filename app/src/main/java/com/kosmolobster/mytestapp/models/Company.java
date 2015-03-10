package com.kosmolobster.mytestapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.kosmolobster.mytestapp.db.Repo;

public class Company {
    @DatabaseField(generatedId = true)
    public String name;

    public Company() {
        // needed by ormlite
    }

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname) {
        this.name = firstname;
    }

    public int save(Repo repo){
        return repo.companyRepo.create(this);
    }

    public int delete(Repo repo)
    {
        return repo.companyRepo.delete(this);
    }
}
