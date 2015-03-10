package com.kosmolobster.mytestapp.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kosmolobster.mytestapp.R;

import java.util.EventListener;
import java.util.List;

public class ListViewWithTopEdit extends LinearLayout {
    boolean INCLUDE_REMOVING = true;

    ListView includedList;
    Button addBtn;
    EditText addEdit;
    List<String> listData;
    ArrayAdapter regAdapter;
    SimpleArrayAdapter arrayAdapter;
    //OnListEditViewListener eventListener;
    LinearLayout addLayout;

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

//    private void doAdd() {
//        if (addEdit != null & !addEdit.getText().equals("")){
//            String newName = addEdit.getText().toString();
//            listData.add(newName);
//            arrayAdapter.notifyDataSetChanged();
//            eventListener.onItemAdded(newName);
//        }
//    }

    public void setListData(List<String> data) {
        listData = data;

        if (includedList !=null) {
//            arrayAdapter = new SimpleArrayAdapter(getContext(),
//                    R.layout.list_item, data);
            regAdapter = new ArrayAdapter(getContext(),
                    android.R.layout.simple_list_item_1);

            includedList.setAdapter(regAdapter);
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

    public void  setRemovingListItems(boolean included) {
        INCLUDE_REMOVING = included;
    }

    private void setUpUI(Context context) {
        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_view_with_top_edit, this);

        includedList = (ListView) this.findViewById(R.id.included);
        addBtn = (Button) this.findViewById(R.id.addBtn);
        addEdit = (EditText) this.findViewById(R.id.newName);
        addLayout = (LinearLayout) this.findViewById(R.id.addLayout);

//        includedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                eventListener.onListItemSelected(position, id);
//            }
//        });
//
//        addBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doAdd();
//            }
//        });
    }

//    public void setListEditViewListener(OnListEditViewListener listener){
//        eventListener = listener;
//    }
//
//    public void removeListEditViewListener(OnListEditViewListener listener){
//        eventListener = null;
//    }
//
//    public interface OnListEditViewListener extends EventListener {
//        public void onItemAdded(String item);
//        public void onListItemSelected(int position, long id);
//    }

    public class SimpleArrayAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<String> values;
        int res;

        public SimpleArrayAdapter(Context context, int res, List<String> values) {
            super(context, R.layout.list_item, values);
            this.context = context;
            this.values = values;
            this.res = res;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(res, parent, true);
            TextView textView = (TextView) rowView.findViewById(R.id.name);
            Button removeBtn = (Button) rowView.findViewById(R.id.remove);
            textView.setBackgroundColor(Color.BLUE);
            if (!INCLUDE_REMOVING) {
                removeBtn.setVisibility(GONE);
            }

//            textView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("TEXTVIEW", "CLICKED");
//                }
//            });

            textView.setText(values.get(position));

            return rowView;
        }
    }
}


