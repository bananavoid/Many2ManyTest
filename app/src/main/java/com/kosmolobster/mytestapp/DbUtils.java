package com.kosmolobster.mytestapp;

import android.database.MatrixCursor;

import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;


public final class DbUtils {

    private DbUtils() {
    }

    public static MatrixCursor getEmployeesCursor() {
        List<Employee> emps = Employee.listAll(Employee.class);
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

    public static MatrixCursor getCompaniesCursor() {
        List<Company> comps = Company.listAll(Company.class);

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

    public static MatrixCursor getCompanyRelationCursor(long forWhom) {
        List<CompanyEmployee> emps = getCompanyRelationList(forWhom);
        String[] c_columns = new String[] {
                "_id",
                "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (int i = 0; i < emps.size(); ++i) {
            matrixCursor.addRow(new String[] {
                    emps.get(i).getId().toString(),
                    emps.get(i).getEmployee_name()
            });
        }
        return matrixCursor;
    }

    public static MatrixCursor getEmployeeRelationCursor(long forWhom) {
        List<CompanyEmployee> comps = getEmployeeRelationList(forWhom);
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

    public static boolean isItExistingEmployee(String name) {
        List <Employee> emp = Select.from(Employee.class).where(Condition.prop("name").eq(name)).list();
        return emp.size() != 0 ;
    }

    public static boolean isItCompanyEmployee(String name, long companyId) {
        Company com = Company.findById(Company.class, companyId);
        List companyEmployees = Select.from(CompanyEmployee.class)
                .where(Condition.prop("employeename").eq(name),
                       Condition.prop("companyname").eq(com.getName()))
                .list();

        return companyEmployees.size() != 0;
    }

    public static boolean isItExistingCompany(String name) {
        List<Company> com = Select.from(Company.class)
            .where(Condition.prop("name").eq(name))
            .list();

        return com.size() != 0;
    }

    public static boolean isItCompanyFull(long id) {
        return getCompanyRelationList(id).size() == (Employee.listAll(Employee.class)).size();
    }

    public static List<CompanyEmployee> getCompanyRelationList(long companyId) {
        String name = Company.findById(Company.class, companyId).getName();
        return Select.from(CompanyEmployee.class)
                .where(Condition.prop("companyname").eq(name))
                .list();
    }

    public static List<CompanyEmployee> getEmployeeRelationList(long employeeId) {
        String name = Employee.findById(Employee.class, employeeId).getName();
        return Select.from(CompanyEmployee.class)
                .where(Condition.prop("employeename").eq(name))
                .list();
    }
}
