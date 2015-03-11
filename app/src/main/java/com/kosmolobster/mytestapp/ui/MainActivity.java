package com.kosmolobster.mytestapp.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.kosmolobster.mytestapp.R;
import com.kosmolobster.mytestapp.Utils;
import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.Employee;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private String CURRENT_LIST_TYPE = "";

    ListViewWithTopEdit list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListViewWithTopEdit) findViewById(R.id.list);

        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {

            @Override
            public void onItemAdded(String item) {
                Company company = new Company(item);
                company.save();

                setCurrentList("companies");
            }

            @Override
            public void onListItemSelected(int position, long id) {
                Intent myIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                myIntent.putExtra("key_inner_id", id);
                myIntent.putExtra("key_type", CURRENT_LIST_TYPE);
                startActivity(myIntent);
            }
        });

        setCurrentList("employees");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setCurrentList(String type) {
        CURRENT_LIST_TYPE = type;
        Utils utils = new Utils();

        switch (type){
            case "employees":
                List<Employee> emps = Employee.listAll(Employee.class);
                list.setListData(utils.getEmployeesCursor(emps));
                list.setListBackground(getResources().getColor(android.R.color.holo_orange_light));
                list.setAddLayoutVisibility(View.GONE);
                break;
            case "companies":
                List<Company> comps = Employee.listAll(Company.class);
                list.setListData(utils.getCompaniesCursor(comps));
                list.setListBackground(getResources().getColor(android.R.color.holo_green_light));
                list.setAddLayoutVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void doShowEmployees(View view) {
        setCurrentList("employees");
    }

    public void doShowCompanies(View view) {
        setCurrentList("companies");
    }
}
