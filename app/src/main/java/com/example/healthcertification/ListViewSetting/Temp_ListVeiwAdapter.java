package com.example.healthcertification.ListViewSetting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthcertification.R;

import java.util.ArrayList;

public class Temp_ListVeiwAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Temp_ListViewItem> listViewItemTempList = new ArrayList<Temp_ListViewItem>() ;
    public Temp_ListVeiwAdapter(){}

    @Override
    public int getCount() {
        return listViewItemTempList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_temperature, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView dateView = (TextView) convertView.findViewById(R.id.temp_date);
        TextView timeView = (TextView) convertView.findViewById(R.id.temp_time);
        TextView tempView = (TextView)convertView.findViewById(R.id.temp_temperature);
        TextView stateView = (TextView) convertView.findViewById(R.id.temp_state) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Temp_ListViewItem TempListViewItem = listViewItemTempList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        dateView.setText(TempListViewItem.getDate());
        timeView.setText(TempListViewItem.getTime());
        tempView.setText(TempListViewItem.getTemp());
        stateView.setText(TempListViewItem.getState());
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemTempList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void Temp_addItem(String date, String time, String temperature, String state) {
        Temp_ListViewItem item = new Temp_ListViewItem();

        //item.setIcon(icon);
        item.setDate(date);
        item.setTime(time);
        item.setTemp(temperature);
        item.setState(state);


        listViewItemTempList.add(item);
    }


}
