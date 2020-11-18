package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthcertification.ListViewSetting.Temp_ListVeiwAdapter;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Temperature extends Fragment  {

    private ListView Temp_ListView;
    private Temp_ListVeiwAdapter temp_listVeiwAdapter;
    private FloatingActionButton Temp_ADD_Btn;


    public MyHealth_Temperature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_temperature, container, false);

        temp_listVeiwAdapter = new Temp_ListVeiwAdapter();
        Temp_ListView = (ListView)view.findViewById(R.id.ht_listview);
        Temp_ListView.setAdapter(temp_listVeiwAdapter);
        Temp_ADD_Btn = (FloatingActionButton)view.findViewById(R.id.temp_add_btn);

        temp_add();

        return view;
    }

    public void temp_add(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy.MM.dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm");
        String getDate = simpleDate.format(mDate);
        String getTime = simpleTime.format(mDate);

        temp_listVeiwAdapter.Temp_addItem("20.11.16","11:45","36.6"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem("20.11.17","11:30","36.5"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem("20.11.18","11:40","36.6"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem(getDate,getTime,"36.8"+"℃","정상");
    }


}
