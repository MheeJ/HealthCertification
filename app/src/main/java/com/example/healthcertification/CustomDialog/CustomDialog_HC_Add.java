package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.healthcertification.R;

public class CustomDialog_HC_Add extends Dialog implements View.OnClickListener  {
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.costomdialog_healthcalculation_add;
    private Context context;
    private EditText weightEt;
    private EditText heightEt;
    private TextView AddBtn;
    private TextView closeBtn;

    public CustomDialog_HC_Add(Context context){
        super(context);
        this.context = context;
    }

    public void HC_DialogListener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        weightEt =(EditText)findViewById(R.id.weight_add);
        heightEt = (EditText)findViewById(R.id.height_add);

        AddBtn = (TextView) findViewById(R.id.hc_add);
        closeBtn = (TextView) findViewById(R.id.hc_close);

        AddBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hc_close:
                cancel();
                break;
            case R.id.hc_add:
                String height = heightEt.getText().toString();
                String weight = weightEt.getText().toString();
                dialog_listener.onSDClicked(height,weight);
                dismiss();
                break;
        }
    }
}
