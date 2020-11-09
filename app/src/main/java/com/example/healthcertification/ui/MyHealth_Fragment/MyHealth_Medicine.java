package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthcertification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Medicine extends Fragment {


    protected String[] List_medicine = {"타이에놀","부루펜","바이젠","약2","약3","약4"};

    public MyHealth_Medicine() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_medicine, container, false);
        ListView listView = (ListView) view.findViewById(R.id.medicine_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                List_medicine
        );
        listView.setAdapter(listViewAdapter);

        return view;
    }



}
