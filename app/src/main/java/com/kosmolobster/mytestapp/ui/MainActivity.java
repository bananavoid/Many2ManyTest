package com.kosmolobster.mytestapp.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kosmolobster.mytestapp.R;
import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private String currentList = "";
    private LinearLayout saveLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveLayout = (LinearLayout) findViewById(R.id.saveLayout);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.KEY_TYPE, currentList);
                intent.putExtra(DetailsActivity.KEY_INNER_ID, id);
                startActivity(intent);
            }
        });

        List<Company> coms = Company.listAll(Company.class);
        List<Employee> emps = Employee.listAll(Employee.class);
        List<CompanyEmployee> ce = CompanyEmployee.listAll(CompanyEmployee.class);

        List select_employees = Select.from(CompanyEmployee.class)
                .where(Condition.prop("companyname").eq("Company"))
                .list();

        List select_companies = Select.from(CompanyEmployee.class)
                .where(Condition.prop("employeename").eq("Noah"))
                .list();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        //dbHelper.getWritableDatabase();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //dbHelper.close();
        super.onPause();
    }

    private void setCurrentList(String type) {
//        currentList = type;
//        List<String> names = new ArrayList<>();
//
//        if (type.equals("employees")) {
//            List<Employee> employees = MyTestApplication.dbHelper.getAllEmployees();
//            for (int i = 0; i < employees.size(); ++i) {
//                names.add(employees.get(i).getName());
//            }
//
//            listView.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
//            saveLayout.setVisibility(View.GONE);
//        } else if (type.equals("companies")) {
//            List<Company> companies = MyTestApplication.dbHelper.getAllCompanies();
//            for (int i = 0; i < companies.size(); ++i) {
//                names.add(companies.get(i).getName());
//            }
//            listView.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
//            saveLayout.setVisibility(View.VISIBLE);
//        }
//
//        if(listView != null) {
//            ArrayAdapter adapter = new ArrayAdapter(this,
//                    android.R.layout.simple_list_item_1, names);
//
//            listView.setAdapter(adapter);
//        }
    }

    public void doShowEmployees(View view) {
        setCurrentList("employees");
    }

    public void doShowCompanies(View view) {
        setCurrentList("companies");
    }

    public void doSave(View view) {
        EditText newName = (EditText) findViewById(R.id.newName);
        //MyTestApplication.dbHelper.createCompany(new Company(listView.getCount(), newName.getText().toString()));
    }

}
