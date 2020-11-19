package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustomDialog_SD_Save;
import com.example.healthcertification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_SelfDiagnosis extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private ImageView SelfDiagnosis_Btn;
    private CheckBox Fever_Btn, Cough_Btn, Throat_Btn, Snot_Btn, Phlegm_Btn, Breath_Btn, Suspicion_Btn;
    private String Fever_State, Cough_State, Throat_State, Snot_State, Phlegm_State, Breath_State, Suspicion_State;
    private String SelfDiagnosis_State;
    public MyHealth_SelfDiagnosis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myhealth_selfdiagnosis, container, false);
        SelfDiagnosis_Btn = (ImageView)view.findViewById(R.id.selfdiagnosis_btn);
        SelfDiagnosis_Btn.setOnClickListener(this);
        Fever_Btn = (CheckBox) view.findViewById(R.id.fever_btn);
        Fever_Btn.setOnCheckedChangeListener(this);
        Cough_Btn = (CheckBox)view.findViewById(R.id.cough_btn);
        Cough_Btn.setOnCheckedChangeListener(this);
        Throat_Btn = (CheckBox)view.findViewById(R.id.throat_btn);
        Throat_Btn.setOnCheckedChangeListener(this);
        Snot_Btn = (CheckBox)view.findViewById(R.id.snot_btn);
        Snot_Btn.setOnCheckedChangeListener(this);
        Phlegm_Btn = (CheckBox)view.findViewById(R.id.phlegm_btn);
        Phlegm_Btn.setOnCheckedChangeListener(this);
        Breath_Btn = (CheckBox)view.findViewById(R.id.breath_btn);
        Breath_Btn.setOnCheckedChangeListener(this);
        Suspicion_Btn = (CheckBox)view.findViewById(R.id.suspicion_btn);
        Suspicion_Btn.setOnCheckedChangeListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selfdiagnosis_btn:
                CustomDialog_SD_Save customDialogSD_save = new CustomDialog_SD_Save(getContext());
                customDialogSD_save.Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        saveSelfDiagnosis(name);
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                customDialogSD_save.show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(Fever_Btn.isChecked()) SelfDiagnosis_State += Fever_Btn.getText().toString() + ",";
        if(Cough_Btn.isChecked()) SelfDiagnosis_State += Cough_Btn.getText().toString() + ",";
        if(Throat_Btn.isChecked()) SelfDiagnosis_State += Throat_Btn.getText().toString() + ",";
        if(Snot_Btn.isChecked()) SelfDiagnosis_State += Snot_Btn.getText().toString() + ",";
        if(Phlegm_Btn.isChecked()) SelfDiagnosis_State += Phlegm_Btn.getText().toString() + ",";
        if(Breath_Btn.isChecked()) SelfDiagnosis_State += Breath_Btn.getText().toString() + ",";
        if(Suspicion_Btn.isChecked()) SelfDiagnosis_State += Suspicion_Btn.getText().toString() + ",";
        Toast.makeText(getActivity(),SelfDiagnosis_State,Toast.LENGTH_LONG).show();
    }

    public void SwitchCheck(){

        Fever_Btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Fever_State = "발열있음";
                    Toast.makeText(getActivity(),Fever_State,Toast.LENGTH_LONG).show();
                }
                else {
                    Fever_State = "발열없음";
                    Toast.makeText(getActivity(),Fever_State,Toast.LENGTH_LONG).show();
                }
            }
        });


    }




    public void saveSelfDiagnosis(String diagnosis_string){
        Fever_State = diagnosis_string;
    }
}
