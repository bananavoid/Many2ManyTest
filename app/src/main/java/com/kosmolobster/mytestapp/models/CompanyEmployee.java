package com.kosmolobster.mytestapp.models;

import com.orm.SugarRecord;

public class CompanyEmployee extends SugarRecord<CompanyEmployee> {
    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    String company_name;
    String employee_name;

    public CompanyEmployee(){
        //don't remove - orm requires
    }

    public CompanyEmployee(String c_id, String em_id){
        this.company_name = c_id;
        this.employee_name = em_id;
    }
}
