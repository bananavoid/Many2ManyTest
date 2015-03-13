package com.kosmolobster.mytestapp.database;

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

    public static String getCompanyName(long id) {
        return Company.findById(Company.class, id).getName();
    }

    public static String getEmployeeName(long id) {
        return Employee.findById(Employee.class, id).getName();
    }

    public static Company getCompany(long id) {
        return Company.findById(Company.class, id);
    }

    public static CompanyEmployee getCompanyEmployee(long id) {
        return CompanyEmployee.findById(CompanyEmployee.class, id);
    }

    public static List<Employee> getAllEmployees() {
        return Employee.listAll(Employee.class);
    }

    public static void saveCompanyEmployee(String c_name, String e_name) {
        CompanyEmployee companyEmployee = new CompanyEmployee(c_name, e_name);
        companyEmployee.save();
    }

    public static void deleteCompany(long id) {
        List<CompanyEmployee> com = getCompanyRelationList(id);

        for (CompanyEmployee ce : com) {
            ce.delete();
        }

        getCompany(id).delete();
    }

    public static void deleteCompanyEmployee(long id) {
        getCompanyEmployee(id).delete();
    }

    public static MatrixCursor getEmployeesCursor() {
        List<Employee> emps = Employee.listAll(Employee.class);
        String[] c_columns = new String[] {
            "_id",
            "name"
        };
        MatrixCursor matrixCursor = new MatrixCursor(c_columns, 0);
        for (Employee em : emps) {
            matrixCursor.addRow(new String[]{
                    em.getId().toString(),
                    em.getName()
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

        for ( Company com : comps) {
            matrixCursor.addRow(new String[]{
                    com.getId().toString(),
                    com.getName()
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
        for (CompanyEmployee ce : emps) {
            matrixCursor.addRow(new String[] {
                    ce.getId().toString(),
                    ce.getEmployee_name()
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
        for ( CompanyEmployee ce : comps) {
            matrixCursor.addRow(new String[] {
                    ce.getId().toString(),
                    ce.getCompany_name()
            });
        }

        return matrixCursor;
    }

    public static List<String> getEmployeesNamesList() {
        List<Employee> emps = getAllEmployees();
        List<String> names = new ArrayList<>();

        for ( Employee em : emps ) {
            names.add(em.getName());
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
        return getCompanyRelationList(id).size() == getAllEmployees().size();
    }

    public static List<CompanyEmployee> getCompanyRelationList(long companyId) {
        String name = getCompanyName(companyId);
        return Select.from(CompanyEmployee.class)
                .where(Condition.prop("companyname").eq(name))
                .list();
    }

    public static List<CompanyEmployee> getEmployeeRelationList(long employeeId) {
        String name = getEmployeeName(employeeId);
        return Select.from(CompanyEmployee.class)
                .where(Condition.prop("employeename").eq(name))
                .list();
    }
}
