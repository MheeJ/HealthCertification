package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthcertification.R;

public class CustomDialog_Add extends Dialog implements View.OnClickListener{
    private CustomDialog_Add_Listener dialogListener;

    private static final int LAYOUT = R.layout.customdialog_medicine_add;

    private Context context;

    private EditText nameEt;
    private EditText emailEt;

    private TextView cancelTv;
    private TextView searchTv;

    private String name;


    public CustomDialog_Add(Context context){
        super(context);
        this.context = context;
    }

    public void setDialogListener(CustomDialog_Add_Listener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        nameEt = (EditText) findViewById(R.id.findPwDialogNameEt);


        cancelTv = (TextView) findViewById(R.id.findPwDialogCancelTv);
        searchTv = (TextView) findViewById(R.id.findPwDialogFindTv);

        cancelTv.setOnClickListener(this);
        searchTv.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.findPwDialogCancelTv:
                cancel();
                break;
            case R.id.findPwDialogFindTv:
                String name = nameEt.getText().toString();
                dialogListener.onPositiveClicked(name);
                dismiss();
                break;
        }
    }


}
