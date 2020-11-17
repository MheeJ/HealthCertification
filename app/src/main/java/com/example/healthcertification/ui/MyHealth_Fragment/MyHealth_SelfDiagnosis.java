package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.healthcertification.CustomDialog.CustomDialog_Add;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustonDialog_SelfDiagnosis;
import com.example.healthcertification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_SelfDiagnosis extends Fragment implements View.OnClickListener{

    ImageView SelfDiagnosis_Btn;
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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selfdiagnosis_btn:
                CustonDialog_SelfDiagnosis dialog = new CustonDialog_SelfDiagnosis(getContext());
                dialog.setDialogListener(new CustomDialog_Listener() {  // MyDialogListener 를 구현
                    @Override
                    public void onPositiveClicked(String diagnosis_string) {
                        saveSelfDiagnosis(diagnosis_string);
                    }

                    @Override
                    public void onNegativeClicked() {
                        Log.d("MyDialogListener","onNegativeClicked");
                    }
                });
        }
    }

    public void saveSelfDiagnosis(String diagnosis_string){
    }
}
