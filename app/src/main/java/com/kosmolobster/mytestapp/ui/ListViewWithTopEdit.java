package com.kosmolobster.mytestapp.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kosmolobster.mytestapp.R;

import java.util.EventListener;
import java.util.List;

public class ListViewWithTopEdit extends LinearLayout {
    private ListView includedList;
    private Button addBtn;
    private AutoCompleteTextView addEdit;
    private OnListEditViewListener eventListener;
    private LinearLayout addLayout;
    private SimpleCursorAdapter dataAdapter;

    public ListViewWithTopEdit(Context context) {
        super(context);
        setUpUI(context);
    }

    public ListViewWithTopEdit(Context context, AttributeSet attrs) {
        super(context, attrs);

        setUpUI(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ListViewWithTopEdit(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setUpUI(context);
    }

    public ListViewWithTopEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setUpUI(context);
    }

    private void setUpUI(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setMinimumWidth(LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_view_with_top_edit, this);

        includedList = (ListView) this.findViewById(R.id.included);
        addBtn = (Button) this.findViewById(R.id.addBtn);
        addEdit = (AutoCompleteTextView) this.findViewById(R.id.newName);
        addLayout = (LinearLayout) this.findViewById(R.id.addLayout);

        includedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventListener.onListItemSelected(id);
            }
        });

        includedList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                eventListener.onListItemLongPressed(position, id);
                return true;
            }
        });

        addBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                doAdd();
            }
        });
    }

    private void doAdd() {
        if (addEdit != null){
            String newName = addEdit.getText().toString();
            eventListener.onItemAdded(newName);
            addEdit.setText("");
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getApplicationContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(addEdit.getWindowToken(), 0);
    }

    public void setListData(Cursor cursor) {
        String[] columns = new String[] {
                "name"
        };

        int[] to = new int[] {
            R.id.name,
        };

        if (includedList !=null) {

            dataAdapter = new SimpleCursorAdapter(
                    getContext(), R.layout.list_item,
                    cursor,
                    columns,
                    to,
                    0);


            includedList.setAdapter(dataAdapter);
        }
    }

    public void setAddLayoutVisibility(int visibility) {
        if (addLayout != null) {
            addLayout.setVisibility(visibility);
        }
    }

    public void setListBackground(int color) {
        if (includedList != null) {
            includedList.setBackgroundColor(color);
        }
    }

    public void setListEditViewListener(OnListEditViewListener listener){
        eventListener = listener;
    }

    public void setAutocompleteData(List<String> data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, data);

        addEdit.setThreshold(1);
        addEdit.setAdapter(adapter);
    }

    public void setTextEditEnabled(boolean enabled) {
        addEdit.setEnabled(enabled);
        addBtn.setEnabled(enabled);
    }

    public void setTextEditHint(int string_id) {
        addEdit.setHint(string_id);
    }

    public interface OnListEditViewListener extends EventListener {
        public void onItemAdded(String item);
        public void onListItemSelected(long id);
        public void onListItemLongPressed(int position, long id);
    }

}


