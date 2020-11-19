package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healthcertification.R;

public class CustomDialog_HC_Remove extends Dialog implements View.OnClickListener{
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.customdialog_healthcalculation_remove;
    private Context context;
    private TextView RemoveBtn;
    private TextView closeBtn;

    public CustomDialog_HC_Remove(Context context){
        super(context);
        this.context = context;
    }

    public void HC_Remove_Dialog_Listener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        RemoveBtn = (TextView) findViewById(R.id.hc_costom_remove);
        closeBtn = (TextView) findViewById(R.id.hc_costom_cancle);

        RemoveBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hc_costom_cancle:
                cancel();
                break;
            case R.id.hc_costom_remove:
                String name = "";
                dialog_listener.onPositiveClicked(name);
                dismiss();
                break;
        }
    }
}
