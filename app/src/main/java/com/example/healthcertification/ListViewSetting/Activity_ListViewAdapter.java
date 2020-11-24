package com.example.healthcertification.ListViewSetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcertification.R;
import com.example.healthcertification.ui.MyActivity_Fragment.CompareItem;
import com.example.healthcertification.ui.MyActivity_Fragment.EncryptedItem;

import java.util.ArrayList;

public class Activity_ListViewAdapter extends BaseAdapter {
    private ArrayList<CompareItem> compareItems = new ArrayList<>();
    int pos;
    public Activity_ListViewAdapter(){

    }
    @Override
    public int getCount() {
        return compareItems.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        pos = position;
        final Context context =parent.getContext();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_activity, parent, false);
        }

        TextView dateView = (TextView) convertView.findViewById(R.id.Activity_date);
        TextView stateView = (TextView) convertView.findViewById(R.id.Activity_state);

        CompareItem compareItem = compareItems.get(position);

        dateView.setText(compareItem.getDate());
        stateView.setText(compareItem.getCompare());
        return convertView;
    }

    public void addItem(CompareItem Item){
        compareItems.add(Item);
    }

    @Override
    public Object getItem(int position) {
        return compareItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear(){
        compareItems.clear();
    }
}
