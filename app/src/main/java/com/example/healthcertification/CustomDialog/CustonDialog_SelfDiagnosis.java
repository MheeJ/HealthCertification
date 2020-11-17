package com.example.healthcertification.CustomDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthcertification.R;
public class CustonDialog_SelfDiagnosis extends Dialog implements View.OnClickListener {
    private CustomDialog_Listener dialogListener;

    private static final int LAYOUT = R.layout.costomdialog_selfdiagnosis_save;

    private Context context;

    private EditText nameEt;

    private TextView cancelTv;
    private TextView searchTv;

    private String name;


    public CustonDialog_SelfDiagnosis(Context context){
        super(context);
        this.context = context;
    }

    public void setDialogListener(CustomDialog_Listener dialogListener){
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
            case R.id.selfdiagnosis_cancle:
                cancel();
                break;
            case R.id.selfdiagnosis_save:
                String name = nameEt.getText().toString();
                dialogListener.onPositiveClicked(name);
                dismiss();
                break;
        }
    }
}
