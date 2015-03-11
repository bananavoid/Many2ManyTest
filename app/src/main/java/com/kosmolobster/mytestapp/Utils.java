package com.kosmolobster.mytestapp;

import android.database.MatrixCursor;

import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;

import java.util.ArrayList;
import java.util.List;


public class Utils {
    public static MatrixCursor getEmployeesCursor(List<Employee> emps) {
        String[] c_columns = new String[] {
            "_id",
            "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (int i = 0; i < emps.size(); ++i) {
            matrixCursor.addRow(new String[]{
                    emps.get(i).getId().toString(),
                    emps.get(i).getName()
            });
        }
        return matrixCursor;
    }

    public static MatrixCursor getCompaniesCursor(List<Company> comps) {
        String[] c_columns = new String[] {
            "_id",
            "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (int i = 0; i < comps.size(); ++i) {
            matrixCursor.addRow(new String[]{
                    comps.get(i).getId().toString(),
                    comps.get(i).getName()
            });
        }
        return matrixCursor;
    }

    public static MatrixCursor getEmployeesForCompany(List<CompanyEmployee> comps) {
        String[] c_columns = new String[] {
                "_id",
                "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (int i = 0; i < comps.size(); ++i) {
            matrixCursor.addRow(new String[] {
                    comps.get(i).getId().toString(),
                    comps.get(i).getEmployee_name()
            });
        }
        return matrixCursor;
    }

    public static MatrixCursor getCompaniesForEmployeer(List<CompanyEmployee> comps) {
        String[] c_columns = new String[] {
                "_id",
                "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (int i = 0; i < comps.size(); ++i) {
            matrixCursor.addRow(new String[] {
                    comps.get(i).getId().toString(),
                    comps.get(i).getCompany_name()
            });
        }
        return matrixCursor;
    }

    public static List<String> getEmployeesNamesList(List<Employee> emps) {
        List<String> names = new ArrayList<>();
        for(int i = 0; i < emps.size(); ++i) {
            names.add(emps.get(i).getName());
        }
        return names;
    }
}
