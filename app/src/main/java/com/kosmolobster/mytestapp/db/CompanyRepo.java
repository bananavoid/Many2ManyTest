package com.kosmolobster.mytestapp.db;

import com.j256.ormlite.dao.Dao;
import com.kosmolobster.mytestapp.models.Company;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by kosmokapusta on 09.03.15.
 */
public class CompanyRepo {
    Dao<Company, Integer> companyDao;

    public CompanyRepo(DatabaseHelper db)
    {
        try {
            companyDao = db.getCompanyDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(Company item)
    {
        try {
            return companyDao.create(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Company item)
    {
        try {
            return companyDao.update(item);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Company user)
    {
        try {
            return companyDao.delete(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }

    public List<Company> getAll()
    {
        try {
            return companyDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
}
