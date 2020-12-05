package com.example.healthcertification.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcertification.R;

public class CustomDialog_Pandemic extends Dialog implements View.OnClickListener {
    private CustomDialog_Listener dialogListener;

    private static final int LAYOUT = R.layout.customdialog_activity_view;

    private Context context;
    private ListView Pandemic_ListView;
    private TextView Pandemic_Close_Btn;
    private String[] listlist = {"하나","둘","셋","넷","다섯"};
    private ArrayAdapter Pandemic_ListView_Adapter;


    public CustomDialog_Pandemic(Context context){
        super(context);
        this.context = context;
    }

    public void  Pandemic_Dialog_Listener(CustomDialog_Listener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);


        Pandemic_ListView_Adapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1,listlist);
        Pandemic_ListView = (ListView)findViewById(R.id.pandemic_detail_list);
        Pandemic_ListView.setAdapter(Pandemic_ListView_Adapter);

        Pandemic_Close_Btn = (TextView)findViewById(R.id.activity_close_btn);
        Pandemic_Close_Btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_close_btn:
                cancel();
                break;
        }
    }
}
