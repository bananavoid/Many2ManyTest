package com.kosmolobster.mytestapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kosmolobster.mytestapp.R;
import com.kosmolobster.mytestapp.Utils;
import com.kosmolobster.mytestapp.models.Company;
import com.kosmolobster.mytestapp.models.CompanyEmployee;
import com.kosmolobster.mytestapp.models.Employee;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;


public class DetailsActivity extends ActionBarActivity {
    public static String KEY_TYPE = "key_type";
    public static String KEY_INNER_ID = "key_inner_id";

    ListViewWithTopEdit list;

    private long innerId;
    private String type;
    LinearLayout destroyLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        innerId = getIntent().getLongExtra(KEY_INNER_ID, -1l);
        type = getIntent().getStringExtra(KEY_TYPE);

        list = (ListViewWithTopEdit) findViewById(R.id.list);

//        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {
//
//
//            @Override
//            public void onItemAdded(String item) {
//
//            }
//
//            @Override
//            public void onListItemSelected(int position, long id) {
//
//            }
//        });

        showList(type, innerId);

        destroyLayout = (LinearLayout) findViewById(R.id.destroyLayout);
    }


    private void showList(String type, long id) {
        Utils utils = new Utils();
        switch (type){
            case "employees":
                String e_name = Employee.findById(Employee.class, id).getName();
                List select_companies = Select.from(CompanyEmployee.class)
                    .where(Condition.prop("employeename").eq(e_name))
                    .list();
                list.setRemovingListItems(false);
                list.setListData(select_companies);
                list.setListBackground(getResources().getColor(android.R.color.holo_orange_light));
                list.setAddLayoutVisibility(View.GONE);
                destroyLayout.setVisibility(View.GONE);
                break;
            case "companies":
                String c_name = Employee.findById(Employee.class, id).getName();
                List select_employees = Select.from(CompanyEmployee.class)
                        .where(Condition.prop("companyname").eq(c_name))
                        .list();
                list.setRemovingListItems(true);
                list.setListData(select_employees);
                list.setListBackground(getResources().getColor(android.R.color.holo_green_light));
                list.setAddLayoutVisibility(View.VISIBLE);
                destroyLayout.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setUpUI() {

    }
}
