package com.kosmolobster.mytestapp.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kosmolobster.mytestapp.R;

import java.util.EventListener;
import java.util.List;

public class ListViewWithTopEdit extends LinearLayout {
    private ListView includedList;
    private Button addBtn;
    private EditText addEdit;
    private OnListEditViewListener eventListener;
    private LinearLayout addLayout;
    private SimpleCursorAdapter dataAdapter;
    private MatrixCursor matrixCursor;

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

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setUpUI(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setMinimumWidth(LayoutParams.MATCH_PARENT);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_view_with_top_edit, this);

        includedList = (ListView) this.findViewById(R.id.included);
        addBtn = (Button) this.findViewById(R.id.addBtn);
        addEdit = (EditText) this.findViewById(R.id.newName);
        addLayout = (LinearLayout) this.findViewById(R.id.addLayout);

        includedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Cursor cursor = (Cursor) includedList.getItemAtPosition(position);
                eventListener.onListItemSelected(position, id);
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
//            MatrixCursor extras = new MatrixCursor(new String[] { "_id", "name" });
//            extras.addRow(new String[] { "1", newName });
//            MatrixCursor[] cursors = { extras, matrixCursor };
//            Cursor extendedCursor = new MergeCursor(cursors);
            eventListener.onItemAdded(newName);
        }
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

    public void removeListEditViewListener(OnListEditViewListener listener){
        eventListener = null;
    }

    public interface OnListEditViewListener extends EventListener {
        public void onItemAdded(String item);
        public void onListItemSelected(int position, long id);
    }

}


