package com.kosmolobster.mytestapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.Employee;

import java.util.List;


public class DetailsActivity extends ActionBarActivity {
    public static String KEY_TYPE = "key_type";
    public static String KEY_INNER_ID = "key_inner_id";

    private int id;
    private long innerId;
    private String type;
    //private DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        innerId = getIntent().getLongExtra(KEY_INNER_ID, -1l);
        type = getIntent().getStringExtra(KEY_TYPE);

        TextView name = (TextView) findViewById(R.id.item_name);
        LinearLayout addLayout = (LinearLayout) findViewById(R.id.addLayout);
        LinearLayout destroyLayout = (LinearLayout) findViewById(R.id.destroyLayout);

//        switch (type) {
//            case "employees":
//                Employee emp = MyTestApplication.dbHelper.getEmployee(id);
//                name.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
//                name.setText(emp.getName());
//                addLayout.setVisibility(View.GONE);
//                destroyLayout.setVisibility(View.GONE);
//                //List<Company> comp = MyTestApplication.dbHelper.getAlCompaniesByEmployee();
//                break;
//            case "companies":
//                Company com = MyTestApplication.dbHelper.getCompany(id);
//                name.setTextColor(getResources().getColor(android.R.color.holo_green_light));
//                name.setText(com.getName());
//                addLayout.setVisibility(View.VISIBLE);
//                destroyLayout.setVisibility(View.VISIBLE);
//                //List<Employee> emps = MyTestApplication.dbHelper.getAlEmployeesByCompany(id);
//                break;
//            default:
//                break;
        }



        //showList(type, innerId);
    //}

//    private void showList(String type, long id) {
//        switch (type) {
//            case "employees":
//                Employee emp = MyTestApplication.dbHelper.getEmployee(id);
//                //List<Company> comp = MyTestApplication.dbHelper.getAlCompaniesByEmployee();
//                break;
//            case "companies":
//                Company com = MyTestApplication.dbHelper.getCompany(id);
//                List<Employee> emps = MyTestApplication.dbHelper.getAlEmployeesByCompany(id);
//                break;
//            default:
//                break;
//        }
//    }

    private void setUpUI() {

    }
}
