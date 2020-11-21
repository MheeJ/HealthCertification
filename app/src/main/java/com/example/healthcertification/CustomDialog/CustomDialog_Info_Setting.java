package com.example.healthcertification.CustomDialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcertification.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomDialog_Info_Setting extends Dialog implements View.OnClickListener  {
    private CustomDialog_Listener dialog_listener;
    private static final int layout = R.layout.customdialog_information_setting;
    private TextView birthday;
    public TextView mETBirthday;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Context context;
    private EditText NameET;
    private TextView AddBtn, closeBtn;
    private Button BirthBtn;
    private String GenderInfo = "";
    private String BirthInfo = "";
    private String NameInfo = "";
    private String getYear, getMonth,getDate;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner_gender;

    public CustomDialog_Info_Setting(Context context){
        super(context);
        this.context = context;
    }

    public void Info_DialogListener(CustomDialog_Listener dialog_listener){
        this.dialog_listener = dialog_listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        this.InitializeView();
        this.InitializeListener();
        NameET =(EditText)findViewById(R.id.info_name_et);
        BirthBtn = (Button)findViewById(R.id.cdate);
        BirthBtn.setOnClickListener(this);
        AddBtn = (TextView) findViewById(R.id.info_add);
        closeBtn = (TextView) findViewById(R.id.info_close);
        AddBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        mETBirthday = findViewById(R.id.birthday);
        arrayList = new ArrayList<>();
        arrayList.add("여성");
        arrayList.add("남성");
        arrayAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, arrayList);

        spinner_gender = findViewById(R.id.spinner_gender);
        spinner_gender.setAdapter(arrayAdapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GenderInfo = arrayList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_close:
                cancel();
                break;
            case R.id.info_add:
                NameInfo = NameET.getText().toString();
                String data = NameInfo + "#" + BirthInfo;
                dialog_listener.onSDClicked(data,GenderInfo);
                dismiss();
                break;
            case R.id.cdate:
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate1 = new SimpleDateFormat("yyyy");
                SimpleDateFormat simpleDate2 = new SimpleDateFormat("MM");
                SimpleDateFormat simpleDate3 = new SimpleDateFormat("dd");
                getYear = simpleDate1.format(mDate);
                getMonth = simpleDate2.format(mDate);
                getDate = simpleDate3.format(mDate);
                int year = Integer.parseInt(getYear);
                int month = Integer.parseInt(getMonth);
                int date = Integer.parseInt(getDate);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),callbackMethod,year,month,date);
                dialog.show();
                break;
        }
    }

    //생일선택달력
    private void InitializeListener() {
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear+1;
                birthday.setText(year + "년"+monthOfYear+"월"+dayOfMonth+"일");
                String yearStr = Integer.toString(year);
                String monthStr = Integer.toString(monthOfYear);
                String dayStr = Integer.toString(dayOfMonth);
                BirthInfo = yearStr + "." +monthStr + "." + dayStr;
            }
        };
    }


    private void InitializeView() {
        birthday = (TextView)findViewById(R.id.birthday);
    }
}
