package com.kosmolobster.mytestapp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.kosmolobster.mytestapp.db.Repo;
import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;


public class MyTestApplication extends Application {
    private final static String DATABASE_URL = "/data/data/com.kosmolobster.mytestapp/databases/some_staff.db";
    public static Repo repo;

    @Override
    public void onCreate() {
        super.onCreate();

        repo = new Repo(this);

        Company company = new Company("Company");
        company.save(repo);

        String[] values = getResources().getStringArray(R.array.employees_names);

        for (int i = 0; i < values.length - 1; ++i) {
            Employee employee = new Employee(values[i]);
            employee.save(repo);

            CompanyEmployee companyEmployee = new CompanyEmployee(company, employee);;
        }
    }
}
