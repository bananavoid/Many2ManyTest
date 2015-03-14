package com.kosmolobster.mytestapp.ui;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kosmolobster.mytestapp.database.DbUtils;
import com.kosmolobster.mytestapp.R;
import com.kosmolobster.mytestapp.models.CompanyEmployee;

import java.util.List;

public class DetailsActivity extends ActionBarActivity {
    public static String KEY_TYPE = "key_type";
    public static String KEY_INNER_ID = "key_inner_id";

    ListViewWithTopEdit list;

    private long innerId;
    private String listType;
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
        listType = getIntent().getStringExtra(KEY_TYPE);

        list = (ListViewWithTopEdit) findViewById(R.id.list);
        destroyBtn = (Button) findViewById(R.id.destroyBtn);
        textDesc = (TextView) findViewById(R.id.item_desc);

        list.setListEditViewListener(new ListViewWithTopEdit.OnListEditViewListener() {


            @Override
            public void onItemAdded(String name) {
                if (name.isEmpty()) {
                    UiUtils.showToast(getApplicationContext(), R.string.item_cannot_be_empty);
                } else if (!DbUtils.isItExistingEmployee(name)) {
                    UiUtils.showToast(getApplicationContext(), R.string.mo_such_employee);
                } else if (DbUtils.isItCompanyEmployee(name, innerId)) {
                    UiUtils.showToast(getApplicationContext(), R.string.employee_already_here);
                } else {
                    DbUtils.saveCompanyEmployee(DbUtils.getCompanyName(innerId), name);
                    showList("companies");
                }
            }

            @Override
            public void onListItemSelected(long id) {

            }

            @Override
            public void onListItemLongPressed(long id) {
                if (listType.equals("companies")) {
                    doDeleteEmployee(id);
                }
            }
        });

        showList(listType);
    }

    private void showList(String type) {
        TextView nameView = (TextView) findViewById(R.id.item_name);

        switch (type){
            case "employees":
                nameView.setText(DbUtils.getEmployeeName(innerId));
                nameView.setTextColor(getResources().getColor(R.color.employee_color));

                textDesc.setText(R.string.item_desc_employee);

                list.setListData(DbUtils.getEmployeeRelationCursor(innerId));
                list.setListBackground(getResources().getColor(R.color.employee_color));
                list.setAddLayoutVisibility(View.GONE);

                destroyBtn.setVisibility(View.GONE);
                break;
            case "companies":
                String c_name = DbUtils.getCompanyName(innerId);

                if(DbUtils.isItCompanyFull(innerId)) {
                    list.setTextEditEnabled(false);
                    list.setTextEditHint(R.string.full_text);
                } else {
                    list.setTextEditEnabled(true);
                    list.setTextEditHint(R.string.add_edit_hint);
                }

                nameView.setText(c_name);
                nameView.setTextColor(getResources().getColor(R.color.company_color));

                textDesc.setText(R.string.item_desc_company);

                list.setListData(DbUtils.getCompanyRelationCursor(innerId));
                list.setListBackground(getResources().getColor(R.color.company_color));
                list.setAddLayoutVisibility(View.VISIBLE);
                list.setAutocompleteData(DbUtils.getEmployeesNamesList());

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
        List<CompanyEmployee> list = DbUtils.getCompanyRelationList(innerId);

        if (list.size() != 0) {
            UiUtils.showAlertDialog(this,
                    R.string.are_you_sure,
                    new MyCallback() {
                        @Override
                        public void doCallback() {
                            deleteCompany();
                        }
                    },
                    null);
        }
        else {
            deleteCompany();
        }
    }

    public void deleteCompany() {
        DbUtils.deleteCompany(innerId);
        finish();
    }


    private void doDeleteEmployee(final long id) {
        UiUtils.showAlertDialog(this,
                R.string.are_you_sure,
                new MyCallback() {
            @Override
            public void doCallback() {
                DbUtils.deleteCompanyEmployee(id);
                list.setListData(DbUtils.getCompanyRelationCursor(innerId));
            }
        },
        null);
    }
}
