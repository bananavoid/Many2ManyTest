package com.kosmolobster.mytestapp.models;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by kosmokapusta on 08.03.15.
 */
public class CompanyEmployee {

    @DatabaseField(generatedId = true)
    public Employee employee;
    @DatabaseField
    public Company company;

    public CompanyEmployee() {
        // needed by ormlite
    }

    public CompanyEmployee(Company company, Employee employee) {
        this.company = company;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
