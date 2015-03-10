package com.kosmolobster.mytestapp.db;
import android.content.Context;

public class Repo {
	
	DatabaseHelper db;
	
	public CompanyEmployeeRepo companyEmployeeRepo;
    public CompanyRepo companyRepo;
    public EmployeeRepo employeeRepo;
	
	public Repo(Context context) {
		db = new DatabaseHelper(context);
        companyEmployeeRepo = new CompanyEmployeeRepo(db);
        companyRepo = new CompanyRepo(db);
        employeeRepo = new EmployeeRepo(db);
	}
}
