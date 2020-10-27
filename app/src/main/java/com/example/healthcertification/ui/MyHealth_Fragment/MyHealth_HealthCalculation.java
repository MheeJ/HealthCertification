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
public class MyHealth_HealthCalculation extends Fragment {

    protected String[] List_body ={"10월 20일           48kg          21.76           정상","10월 20일            48kg           21.76           정상",
            "10월 20일           48kg          21.76          정상",
            "10월 20일           48kg          21.76          정상","10월 20일           48kg          21.76          정상",
            "10월 20일           48kg          21.76          정상","10월 20일           48kg          21.76          정상"};

    public MyHealth_HealthCalculation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_healthcalculation, container, false);
        ListView listView = (ListView) view.findViewById(R.id.fragment3_listview);
        ArrayAdapter<String> listViewAdapter= new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                List_body
        );
        listView.setAdapter(listViewAdapter);
        return  view;
    }
}
