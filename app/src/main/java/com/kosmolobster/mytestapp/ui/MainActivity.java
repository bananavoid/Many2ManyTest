package com.kosmolobster.mytestapp.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.kosmolobster.mytestapp.database.DbUtils;
import com.kosmolobster.mytestapp.R;
import com.kosmolobster.mytestapp.models.Company;

public class MainActivity extends ActionBarActivity {
    private String CURRENT_LIST_TYPE = "employees";

    ListViewWithTopEdit list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        list = (ListViewWithTopEdit) findViewById(R.id.list);

        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {

            @Override
            public void onItemAdded(String name) {
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.item_cannot_be_empty,
                            Toast.LENGTH_LONG).show();
                } else if (DbUtils.isItExistingCompany(name)) {
                    Toast.makeText(getApplicationContext(), R.string.company_exists,
                            Toast.LENGTH_LONG).show();
                } else {
                    Company company = new Company(name);
                    company.save();

                    setCurrentList(CURRENT_LIST_TYPE);
                }
            }

            @Override
            public void onListItemSelected(long id) {
                Intent myIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                myIntent.putExtra("key_inner_id", id);
                myIntent.putExtra("key_type", CURRENT_LIST_TYPE);
                startActivity(myIntent);
            }

            @Override
            public void onListItemLongPressed(int position, long id) {

            }
        });

        setCurrentList(CURRENT_LIST_TYPE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (CURRENT_LIST_TYPE != null)
            setCurrentList(CURRENT_LIST_TYPE);
    }

    private void setCurrentList(String type) {
        CURRENT_LIST_TYPE = type;

        switch (type){
            case "employees":
                list.setListData(DbUtils.getEmployeesCursor());
                list.setListBackground(getResources().getColor(R.color.employee_color));
                list.setAddLayoutVisibility(View.GONE);
                break;
            case "companies":
                list.setListData(DbUtils.getCompaniesCursor());
                list.setListBackground(getResources().getColor(R.color.company_color));
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
