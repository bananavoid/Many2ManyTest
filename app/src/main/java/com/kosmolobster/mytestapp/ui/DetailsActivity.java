package com.kosmolobster.mytestapp.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kosmolobster.mytestapp.DbUtils;
import com.kosmolobster.mytestapp.R;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        innerId = getIntent().getLongExtra(KEY_INNER_ID, -1l);
        type = getIntent().getStringExtra(KEY_TYPE);

        list = (ListViewWithTopEdit) findViewById(R.id.list);
        destroyBtn = (Button) findViewById(R.id.destroyBtn);
        textDesc = (TextView) findViewById(R.id.item_desc);

        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {


            @Override
            public void onItemAdded(String item) {
                if (item.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.item_cannot_be_empty,
                            Toast.LENGTH_LONG).show();
                } else if (!DbUtils.isItExistingEmployee(item)) {
                    Toast.makeText(getApplicationContext(), R.string.mo_such_employee,
                            Toast.LENGTH_LONG).show();
                } else if (DbUtils.isItCompanyEmployee(item, innerId)) {
                    Toast.makeText(getApplicationContext(), R.string.employee_already_here,
                            Toast.LENGTH_LONG).show();
                } else {
                    CompanyEmployee companyEmployee = new CompanyEmployee(Company.findById(Company.class, innerId).getName(),
                            item);
                    companyEmployee.save();
                    showList("companies", innerId);
                }
            }

            @Override
            public void onListItemSelected(long id) {

            }

            @Override
            public void onListItemLongPressed(int position, long id) {
                if (type.equals("companies")) {
                    showAlertDialog(id);
                }
            }
        });

        showList(type, innerId);
    }

    private void showList(String type, long id) {
        TextView nameView = (TextView) findViewById(R.id.item_name);

        switch (type){
            case "employees":
                nameView.setText(Employee.findById(Employee.class, id).getName());
                nameView.setTextColor(getResources().getColor(R.color.employee_color));

                textDesc.setText(R.string.item_desc_employee);

                list.setListData(DbUtils.getEmployeeRelationCursor(id));
                list.setListBackground(getResources().getColor(R.color.employee_color));
                list.setAddLayoutVisibility(View.GONE);

                destroyBtn.setVisibility(View.GONE);
                break;
            case "companies":
                String c_name = Company.findById(Company.class, id).getName();

                if(DbUtils.isItCompanyFull(id)) {
                    list.setTextEditEnabled(false);
                    list.setTextEditHint(R.string.full_text);
                } else {
                    list.setTextEditEnabled(true);
                    list.setTextEditHint(R.string.add_edit_hint);
                }

                nameView.setText(c_name);
                nameView.setTextColor(getResources().getColor(R.color.company_color));

                textDesc.setText(R.string.item_desc_company);

                list.setListData(DbUtils.getCompanyRelationCursor(id));
                list.setListBackground(getResources().getColor(R.color.company_color));
                list.setAddLayoutVisibility(View.VISIBLE);
                list.setAutocompleteData(DbUtils.getEmployeesNamesList(Employee.listAll(Employee.class)));

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

    public void doDestroyCompany(View view) {
        Company company = Company.findById(Company.class, innerId);

        List<CompanyEmployee> com = Select.from(CompanyEmployee.class)
                .where(Condition.prop("companyname").eq(company.getName()))
                .list();

        for (int i = 0; i < com.size(); ++i) {
            CompanyEmployee ce = com.get(i);
            ce.delete();
        }

        company.delete();

        finish();
    }

    private void doDeleteEmployee(long id) {
        CompanyEmployee.findById(CompanyEmployee.class, id).delete();
        list.setListData(DbUtils.getCompanyRelationCursor(innerId));
    }

    public void showAlertDialog(final long id) {
        CompanyEmployee ce = CompanyEmployee.findById(CompanyEmployee.class, id);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Owww");
        builder.setMessage("Do you want to delete " +
                ce.getEmployee_name() + "?");

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                doDeleteEmployee(id);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
