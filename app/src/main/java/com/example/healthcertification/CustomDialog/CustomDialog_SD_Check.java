package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcertification.R;

public class CustomDialog_SD_Check extends Dialog implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.customdialog_selfdiagnosis_check;
    private Context context;
    private TextView SD_SaveBtn;
    private TextView SD_closeBtn;
    private CheckBox Fever_Btn, Cough_Btn, Throat_Btn, Snot_Btn, Phlegm_Btn, Breath_Btn, Suspicion_Btn;
    private String Fever_State, Cough_State, Throat_State, Snot_State, Phlegm_State, Breath_State, Suspicion_State;
    private String SelfDiagnosis_State;

    public CustomDialog_SD_Check(Context context){
        super(context);
        this.context = context;
    }

    public void SD_Check_Dialog_Listener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        SD_SaveBtn = (TextView) findViewById(R.id.sd_save_btn);
        SD_SaveBtn.setOnClickListener(this);
        SD_closeBtn = (TextView) findViewById(R.id.sd_cancle_btn);
        SD_closeBtn.setOnClickListener(this);
        Fever_Btn = (CheckBox)findViewById(R.id.fever_btn);
        Fever_Btn.setOnCheckedChangeListener(this);
        Cough_Btn = (CheckBox)findViewById(R.id.cough_btn);
        Cough_Btn.setOnCheckedChangeListener(this);
        Throat_Btn = (CheckBox)findViewById(R.id.throat_btn);
        Throat_Btn.setOnCheckedChangeListener(this);
        Snot_Btn = (CheckBox)findViewById(R.id.snot_btn);
        Snot_Btn.setOnCheckedChangeListener(this);
        Phlegm_Btn = (CheckBox)findViewById(R.id.phlegm_btn);
        Phlegm_Btn.setOnCheckedChangeListener(this);
        Breath_Btn = (CheckBox)findViewById(R.id.breath_btn);
        Breath_Btn.setOnCheckedChangeListener(this);
        Suspicion_Btn = (CheckBox)findViewById(R.id.suspicion_btn);
        Suspicion_Btn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sd_cancle_btn:
                cancel();
                break;
            case R.id.sd_save_btn:
                String name = "";
                dialog_listener.onPositiveClicked(name);
                dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        if(Fever_Btn.isChecked()) SelfDiagnosis_State = Fever_Btn.getText().toString() + ",";
        if(Cough_Btn.isChecked()) SelfDiagnosis_State = Cough_Btn.getText().toString() + ",";
        if(Throat_Btn.isChecked()) SelfDiagnosis_State = Throat_Btn.getText().toString() + ",";
        if(Snot_Btn.isChecked()) SelfDiagnosis_State = Snot_Btn.getText().toString() + ",";
        if(Phlegm_Btn.isChecked()) SelfDiagnosis_State = Phlegm_Btn.getText().toString() + ",";
        if(Breath_Btn.isChecked()) SelfDiagnosis_State = Breath_Btn.getText().toString() + ",";
        if(Suspicion_Btn.isChecked()) SelfDiagnosis_State = Suspicion_Btn.getText().toString() + ",";
        //if(Suspicion_Btn.isChecked()) SelfDiagnosis_State += Suspicion_Btn.getText().toString() + ",";
        Toast.makeText(context.getApplicationContext(),SelfDiagnosis_State,Toast.LENGTH_LONG).show();
    }
}
