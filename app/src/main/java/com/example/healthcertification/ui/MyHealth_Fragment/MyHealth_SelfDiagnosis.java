package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustomDialog_SD_Check;
import com.example.healthcertification.CustomDialog.CustomDialog_SD_Save;
import com.example.healthcertification.ListViewSetting.SD_ListViewAdapter;
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
    private SD_ListViewAdapter sd_listViewAdapter;

    public MyHealth_SelfDiagnosis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myhealth_selfdiagnosis, container, false);

        sd_listViewAdapter = new SD_ListViewAdapter();
        SD_ListView = (ListView)view.findViewById(R.id.sd_listview);
        SD_ListView.setAdapter(sd_listViewAdapter);
        SD_ADD_Btn = (FloatingActionButton) view.findViewById(R.id.sd_add_btn);
        SD_ADD_Btn.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_add_btn:
                CustomDialog_SD_Check customDialog_sd_check = new CustomDialog_SD_Check(getContext());
                customDialog_sd_check.SD_Check_Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {

                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                customDialog_sd_check.show();
                break;
        }
    }


}
