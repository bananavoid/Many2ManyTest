package com.kosmolobster.mytestapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String DATABASE_NAME = "some_staff.db";
	private static final int DATABASE_VERSION = 1;

    private Dao<Company, Integer> companyDao;
    private Dao<Employee, Integer> employeeDao;
    private Dao<CompanyEmployee, Integer> companyEmployeesDao;
	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		DatabaseInitializer initializer = new DatabaseInitializer(context);
		try {
			initializer.createDatabase();
			initializer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			
			TableUtils.createTable(connectionSource, Employee.class);
            TableUtils.createTable(connectionSource, Company.class);
            TableUtils.createTable(connectionSource, CompanyEmployee.class);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.dropTable(connectionSource, Employee.class, true);
            TableUtils.dropTable(connectionSource, Company.class, true);
            TableUtils.dropTable(connectionSource, CompanyEmployee.class, true);
			
			onCreate(db);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	
	public Dao<Employee, Integer> getEmployeeDao() throws SQLException {
		if (employeeDao == null) {
            employeeDao = getDao(Employee.class);
		}
		return employeeDao;
	}

    public Dao<Company, Integer> getCompanyDao() throws SQLException {
        if (companyDao == null) {
            companyDao = getDao(Company.class);
        }
        return companyDao;
    }

    public Dao<CompanyEmployee, Integer> getCompanyEmployeeDao() throws SQLException {
        if (companyEmployeesDao == null) {
            companyEmployeesDao = getDao(CompanyEmployee.class);
        }
        return companyEmployeesDao;
    }
	
	
	
	@Override
	public void close() {
		super.close();
		employeeDao = null;
        companyDao = null;
        companyEmployeesDao = null;
	}
}
