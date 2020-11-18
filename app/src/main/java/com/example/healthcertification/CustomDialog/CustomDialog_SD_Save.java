package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healthcertification.R;

public class CustomDialog_SD_Save extends Dialog implements View.OnClickListener {
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.costomdialog_selfdiagnosis_save;
    private Context context;
    private TextView SaveBtn;
    private TextView closeBtn;

    public CustomDialog_SD_Save(Context context){
        super(context);
        this.context = context;
    }

    public void Dialog_Listener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        SaveBtn = (TextView) findViewById(R.id.selfdiagnosis_save);
        closeBtn = (TextView) findViewById(R.id.selfdiagnosis_cancle);

        SaveBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.selfdiagnosis_cancle:
                cancel();
                break;
            case R.id.selfdiagnosis_save:
                String name = "mini";
                dialog_listener.onPositiveClicked(name);
                dismiss();
                break;
        }
    }
}
