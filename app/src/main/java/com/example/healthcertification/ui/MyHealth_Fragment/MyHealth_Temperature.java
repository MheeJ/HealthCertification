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
public class MyHealth_Temperature extends Fragment {

    protected String[] List_menu ={"10월 20일      36.5°C ","10월 19일      36.2°C","10월 18일      36.7°C",
            "10월 17일      36.6°C","10월 16일      37.1°C","10월 15일      36.7°C","10월 14일       36.5°C"};

    public MyHealth_Temperature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_temperature, container, false);
        ListView listView = (ListView) view.findViewById(R.id.fragment2_listview);
        ArrayAdapter<String> listViewAdapter= new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                List_menu
        );
        listView.setAdapter(listViewAdapter);

        return view;
    }
}
