package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcertification.R;

import java.util.ArrayList;

public class CustonDialog_Test extends Dialog implements View.OnClickListener , CompoundButton.OnCheckedChangeListener {
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.customdialog_selfdiagnosis_check;
    private Context context;
    private TextView SD_SaveBtn;
    private TextView SD_closeBtn;
    private CheckBox Fever_Btn, Cough_Btn, Throat_Btn, Snot_Btn, Phlegm_Btn, Breath_Btn, Suspicion_Btn;
    private String Fever_State = "";
    private String Cough_State= "";
    private String Throat_State= "";
    private String Snot_State= "";
    private String Phlegm_State= "";
    private String Breath_State= "";
    private String Suspicion_State= "";
    ArrayList<String> items = new ArrayList<String>();
    public String SD_myState = "";

    public CustonDialog_Test(Context context){
        super(context);
        this.context = context;
    }

    public void test_DialogListener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        SD_closeBtn = (TextView) findViewById(R.id.sd_cancle_btn);
        SD_closeBtn.setOnClickListener(this);
        SD_SaveBtn = (TextView) findViewById(R.id.sd_save_btn);
        SD_SaveBtn.setOnClickListener(this);
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
                for(int i=0; i< items.size()+1; i++){
                    if(1 <= i && i < 2){
                        SD_myState = "not bad";
                        Toast.makeText(context.getApplicationContext(),"오늘은 컨디션 관리하기!",Toast.LENGTH_LONG).show();

                    }
                    else if(2 <= i && i < 3){
                        SD_myState = "tired";
                        Toast.makeText(context.getApplicationContext(),"오늘은 푹 쉬는게 좋겠어요.",Toast.LENGTH_LONG).show();

                    }
                    else if(3 <= i){
                        SD_myState = "sick";
                        Toast.makeText(context.getApplicationContext(),"질병관리본부 콜센터(1399)연락 또는 병원에 가보는건 어떨까요?",Toast.LENGTH_LONG).show();

                    }
                    else {
                        SD_myState = "good";
                        Toast.makeText(context.getApplicationContext(),"오늘 컨디션 최고!",Toast.LENGTH_LONG).show();

                    }
                }
                dialog_listener.onPositiveClicked(SD_myState);
                dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){

        switch (buttonView.getId()){
            case R.id.fever_btn:
                if(isChecked){
                    Fever_State = "열";
                    items.add(Fever_State);
                }
                else
                    items.remove("열");
                break;

            case R.id.cough_btn:
                if(isChecked){
                    Cough_State = "기침";
                    items.add(Cough_State);
                }
                else{
                    items.remove("기침");
                }

                break;

            case R.id.throat_btn:
                if(isChecked){
                    Throat_State = "목통증";
                    items.add(Throat_State);
                }
                else
                    items.remove(Throat_State);
                break;


            case R.id.snot_btn:
                if(isChecked){
                    Snot_State = "콧물";
                    items.add(Snot_State);
                }
                else
                    items.remove(Snot_State);
                break;

            case R.id.phlegm_btn:
                if(isChecked){
                    Phlegm_State = "가래";
                    items.add(Phlegm_State);
                }
                else
                    items.remove(Phlegm_State);
                break;

            case R.id.breath_btn:
                if(isChecked){
                    Breath_State = "호흡곤란";
                    items.add(Breath_State);
                }
                else
                    items.remove(Breath_State);
                break;

            case R.id.suspicion_btn:
                if(isChecked){
                    Suspicion_State = "확진자접촉";
                    items.add(Suspicion_State);
                }
                else
                    items.remove(Suspicion_State);
                break;
        }

        //Toast.makeText(context.getApplicationContext(),items.toString(),Toast.LENGTH_LONG).show();
    }
}
