package com.kosmolobster.mytestapp;

import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosmokapusta on 10.03.15.
 */
public class Utils {
    public static List<String> getAllEmployeesNames(List<Employee> emps) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < emps.size(); ++i) {
            names.add(emps.get(i).getName());
        }
        return names;
    }

    public static List<String> getAllCompaniesNames(List<Company> comps) {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < comps.size(); ++i) {
            names.add(comps.get(i).getName());
        }
        return names;
    }
}
