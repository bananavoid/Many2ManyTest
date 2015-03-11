package com.kosmolobster.mytestapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button destroyBtn;
    private TextView textDesc;
    private List companyEmployees;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        innerId = getIntent().getLongExtra(KEY_INNER_ID, -1l);
        type = getIntent().getStringExtra(KEY_TYPE);

        list = (ListViewWithTopEdit) findViewById(R.id.list);
        destroyBtn = (Button) findViewById(R.id.destroyBtn);
        textDesc = (TextView) findViewById(R.id.item_desc);

        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {


            @Override
            public void onItemAdded(String item) {
                String name = list.getTextFromEdit();

                if (companyEmployees != null) {
                    if (companyEmployees.contains(name)) {
                        Toast.makeText(getApplicationContext(), R.string.employee_already_here,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    CompanyEmployee companyEmployee = new CompanyEmployee(Company.findById(Company.class, innerId).getName(),
                            name);
                    companyEmployee.save();
                    showList("companies", innerId);
                }
            }

            @Override
            public void onListItemSelected(int position, long id) {

            }
        });

        showList(type, innerId);
    }

    private void showList(String type, long id) {
        Utils utils = new Utils();
        TextView nameView = (TextView) findViewById(R.id.item_name);

        switch (type){
            case "employees":
                String e_name = Employee.findById(Employee.class, id).getName();

                companyEmployees = Select.from(CompanyEmployee.class)
                        .where(Condition.prop("employeename").eq(e_name))
                        .list();

                nameView.setText(e_name);
                nameView.setTextColor(getResources().getColor(android.R.color.holo_orange_light));

                textDesc.setText(R.string.item_desc_employee);

                list.setListData(utils.getCompaniesForEmployeer(companyEmployees));
                list.setListBackground(getResources().getColor(android.R.color.holo_orange_light));
                list.setAddLayoutVisibility(View.GONE);

                destroyBtn.setVisibility(View.GONE);
                break;
            case "companies":
                String c_name = Company.findById(Company.class, id).getName();
                List select_employees = Select.from(CompanyEmployee.class)
                        .where(Condition.prop("companyname").eq(c_name))
                        .list();

                if(select_employees.size() == (Employee.listAll(Employee.class)).size()) {
                    list.setTextEditEnabled(false);
                    list.setTextEditHint(R.string.full_text);
                } else {
                    list.setTextEditEnabled(true);
                    list.setTextEditHint(R.string.add_edit_hint);
                }

                nameView.setText(c_name);
                nameView.setTextColor(getResources().getColor(android.R.color.holo_green_light));

                textDesc.setText(R.string.item_desc_company);

                list.setListData(utils.getEmployeesForCompany(select_employees));
                list.setListBackground(getResources().getColor(android.R.color.holo_green_light));
                list.setAddLayoutVisibility(View.VISIBLE);
                list.setAutocompleteData(utils.getEmployeesNamesList(Employee.listAll(Employee.class)));
                if (c_name.equals("Company")) {
                    destroyBtn.setVisibility(View.GONE);
                } else {
                    destroyBtn.setVisibility(View.VISIBLE);
                }

                break;
            default:
                break;
        }
    }

    public void setUpAddLayout() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void doDestroy(View view) {
        Company company = Company.findById(Company.class, innerId);
        company.delete();
        finish();
    }
}
