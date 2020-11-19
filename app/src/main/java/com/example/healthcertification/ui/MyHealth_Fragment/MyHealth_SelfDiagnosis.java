package com.example.healthcertification.ui.MyHealth_Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcertification.CustomDialog.CustomDialog_Remove;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustonDialog_Test;
import com.example.healthcertification.ListViewSetting.SD_ListViewAdapter;
import com.example.healthcertification.ListViewSetting.Test_ListVeiwAdapter;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_SelfDiagnosis extends Fragment implements View.OnClickListener{


    private FloatingActionButton SD_ADD_Btn;
    private ListView SD_ListView;
    private SD_ListViewAdapter SD_ListView_Adapter;
    private ListView TestListView;
    private TextView GoodWeight_View, Bmi_View, BmiState_View;
    private Test_ListVeiwAdapter test_listVeiwAdapter;
    private Drawable drawable;


    public MyHealth_SelfDiagnosis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myhealth_selfdiagnosis, container, false);

        test_listVeiwAdapter = new Test_ListVeiwAdapter();
        TestListView = (ListView)view.findViewById(R.id.SD_listview1);
        TestListView.setAdapter(test_listVeiwAdapter);
        SD_ADD_Btn = (FloatingActionButton) view.findViewById(R.id.sd_add_btn);
        SD_ADD_Btn.setOnClickListener(this);

        TestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                CustomDialog_Remove dialog_hc_remove = new CustomDialog_Remove(getContext());
                dialog_hc_remove.HC_Remove_Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        test_listVeiwAdapter.removeItem(pos);
                        test_listVeiwAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                dialog_hc_remove.show();
            }
        });




        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_add_btn:
                CustonDialog_Test dialog= new CustonDialog_Test(getContext());
                dialog.test_DialogListener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        makeItem(name);
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {
                        //makeItem(height, weight);
                    }
                });
                dialog.show();
                break;
        }
    }
    public void makeItem(String name){
        // 첫 번째 아이템 추가.
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy.MM.dd");
        String getTime = simpleDate.format(mDate);
        if(name == "good"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_good);
        }
        else if(name == "not bad"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_notbad);
        }
        else if(name == "tired"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_tired);
        }
        else{
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_sick);
        }

        //drawable 안되면
        //test_listVeiwAdapter.addItem(getTime, name);
        test_listVeiwAdapter.addItem(getTime, drawable);
        test_listVeiwAdapter.notifyDataSetChanged();

    }
}
