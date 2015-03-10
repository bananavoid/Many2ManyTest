package com.kosmolobster.mytestapp.db;

import com.j256.ormlite.dao.Dao;
import com.kosmolobster.mytestapp.models.CompanyEmployee;

import java.sql.SQLException;
import java.util.List;

public class CompanyEmployeeRepo {
    Dao<CompanyEmployee, Integer> companyEmployeesDao;

    public CompanyEmployeeRepo(DatabaseHelper db)
    {
        try {
            companyEmployeesDao = db.getCompanyEmployeeDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(CompanyEmployee item)
    {
        try {
            return companyEmployeesDao.create(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int update(CompanyEmployee item)
    {
        try {
            return companyEmployeesDao.update(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(CompanyEmployee item)
    {
        try {
            return companyEmployeesDao.delete(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public List<CompanyEmployee> getAll()
    {
        try {
            return companyEmployeesDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
}
