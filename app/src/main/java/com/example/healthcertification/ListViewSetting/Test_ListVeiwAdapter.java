package com.example.healthcertification.ListViewSetting;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.healthcertification.R;

import java.util.ArrayList;

public class Test_ListVeiwAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Test_ListVeiwitem> test_listVeiwitems = new ArrayList<Test_ListVeiwitem>() ;

    int pos;
    Drawable mDrawable;
    // ListViewAdapter의 생성자
    public Test_ListVeiwAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return test_listVeiwitems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /* final int pos = position;*/
        pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_test, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        //ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView dateView = (TextView) convertView.findViewById(R.id.test_item_date) ;
        ImageView stateView = (ImageView) convertView.findViewById(R.id.test_item_state);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Test_ListVeiwitem test_listVeiwitem = test_listVeiwitems.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIcon());
        dateView.setText(test_listVeiwitem.getDate());
        stateView.setImageDrawable(test_listVeiwitem.getIcon());
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
        return test_listVeiwitems.get(position) ;

    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String date, Drawable icon) {
        Test_ListVeiwitem item = new Test_ListVeiwitem();

        //item.setIcon(icon);
        item.setDate(date);
        item.setIcon(icon);
        test_listVeiwitems.add(item);
    }
    public void removeItem(int position){
        if(position != ListView.INVALID_POSITION){
            test_listVeiwitems.remove(position);
        }
    }


}
