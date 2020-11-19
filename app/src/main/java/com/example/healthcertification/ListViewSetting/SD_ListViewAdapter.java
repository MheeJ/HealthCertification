package com.example.healthcertification.ListViewSetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcertification.R;

import java.util.ArrayList;

public class SD_ListViewAdapter extends BaseAdapter {
    private ArrayList<SD_ListViewItem> listViewItemSDList = new ArrayList<SD_ListViewItem>();

    public SD_ListViewAdapter(){}

    @Override
    public int getCount() {
        return listViewItemSDList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* final int pos = position;*/
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_selfdiagnosis, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView dateView = (TextView) convertView.findViewById(R.id.SD_date) ;
        TextView stateView = (TextView)convertView.findViewById(R.id.SD_state);


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SD_ListViewItem SDListViewItem = listViewItemSDList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        dateView.setText(SDListViewItem.getDate());
        stateView.setText(SDListViewItem.getState());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemSDList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String date, String state) {
        SD_ListViewItem item = new SD_ListViewItem();

        //item.setIcon(icon);
        item.setDate(date);
        item.setState(state);

        listViewItemSDList.add(item);
    }

}
