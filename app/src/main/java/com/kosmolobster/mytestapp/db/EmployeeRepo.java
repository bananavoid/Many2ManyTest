package com.kosmolobster.mytestapp.db;

import com.j256.ormlite.dao.Dao;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kosmokapusta on 09.03.15.
 */
public class EmployeeRepo {
    Dao<Employee, Integer> employeeDao;

    public EmployeeRepo(DatabaseHelper db)
    {
        try {
            employeeDao = db.getEmployeeDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(Employee item)
    {
        try {
            return employeeDao.create(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public List<Employee> getAll()
    {
        try {
            return employeeDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
}
