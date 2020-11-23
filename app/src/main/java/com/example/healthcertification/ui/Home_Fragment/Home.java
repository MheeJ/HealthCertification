package com.example.healthcertification.ui.Home_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcertification.ListViewSetting.M_ListViewItem;
import com.example.healthcertification.ListViewSetting.Temp_ListViewItem;
import com.example.healthcertification.R;
import com.example.healthcertification.ui.MyActivity_Fragment.CaloryItem;
import com.example.healthcertification.ui.MyActivity_Fragment.FileStore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Home extends Fragment {
    private TextView health_Tip;


    private HorizontalCalendar horizontalCalendar;
    private TextView temptextView, TimetextView, DatetextView, CaloryView;
    private FileStore fileStore = new FileStore();

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Temp_ListViewItem> temp_listViewItems;
    private List<CaloryItem> calory_listViewItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        temptextView = (TextView)root.findViewById(R.id.fragment1_temperature);
        CaloryView = (TextView)root.findViewById(R.id.calory);
        //TimetextView = (TextView)root.findViewById(R.id.fragment1_Time);
        //DatetextView = (TextView)root.findViewById(R.id.fragment1_Date);

        health_Tip = (TextView) root.findViewById(R.id.health_Tip);
        temp_listViewItems = new ArrayList<>();
        calory_listViewItems = new ArrayList<>();

        //calendar : https://github.com/Mulham-Raee/Horizontal-Calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        CaloryView.setText(fileStore.ReadCalory("calory", false));

        horizontalCalendar = new HorizontalCalendar.Builder(root, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        onDataChange();
        SetCalender();
        RandomSetText();

        return root;
    }

    private void RandomSetText(){
        String []arr = getResources().getStringArray(R.array.liver_Tip);
        List<String> healthTipValues;
        healthTipValues = new ArrayList<>(Arrays.asList(arr));

        Random r = new Random();
        String randomValue = healthTipValues.get(r.nextInt(healthTipValues.size()));

        health_Tip.setText(randomValue);
    }

    public void SetCalender(){
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(final Calendar date, int position) {
                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference("Temperature");

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot namedata : snapshot.getChildren()){
                            temp_listViewItems.add(namedata.getValue(Temp_ListViewItem.class));
                        }
                        for (int i =0; i<temp_listViewItems.size(); i++){
                            Temp_ListViewItem temp_listViewItem = (Temp_ListViewItem)temp_listViewItems.get(i);
                            SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
                            String strDate1 = sdf.format(date.getTime());
                            String strDate2 = temp_listViewItem.getDate();
                            if (strDate1.equals(strDate2)){
                                double temp = temp_listViewItem.getTemp();
                                String Time = temp_listViewItem.getTime();
                                String Date = temp_listViewItem.getDate();
                                String year = Date.substring(0,2);
                                String month = Date.substring(3,5);
                                String date = Date.substring(6);
                                String tempstr = String.format("%.1f",temp);
                                temptextView.setText(tempstr+"℃");

                                //TimetextView.setText(Time);
                                //DatetextView.setText("20"+year+"년"+month+"월"+date+"일");

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                mReference = mDatabase.getReference("Calory");

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot namedata : snapshot.getChildren()){
                            calory_listViewItems.add(namedata.getValue(CaloryItem.class));
                        }
                        for (int i =0; i<calory_listViewItems.size(); i++){
                            CaloryItem calory_listViewItem = (CaloryItem)calory_listViewItems.get(i);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String strDate1 = sdf.format(date.getTime());
                            String strDate2 = calory_listViewItem.getDate();
                            if (strDate1.equals(strDate2)){
                                CaloryView.setText(calory_listViewItem.getCalory());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                SimpleDateFormat cal_format = new SimpleDateFormat("yyyy-MM-dd");
                String Date1 = cal_format.format(date.getTime());
                if(Date1.equals(fileStore.ReadCalory("calory", true)))
                    CaloryView.setText(fileStore.ReadCalory("calory", false));
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });
    }

    public void onDataChange(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Temperature");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp_listViewItems.clear();
                for (DataSnapshot namedata : snapshot.getChildren()){
                    temp_listViewItems.add(namedata.getValue(Temp_ListViewItem.class));
                }
                for (int i =0; i<temp_listViewItems.size(); i++){
                    Temp_ListViewItem temp_listViewItem = (Temp_ListViewItem)temp_listViewItems.get(i);

                    double temp = temp_listViewItem.getTemp();
                    String tempstr = String.format("%.1f",temp);
                    String Time = temp_listViewItem.getTime();
                    String Date = temp_listViewItem.getDate();
                    String year = Date.substring(0,2);
                    String month = Date.substring(3,5);
                    String date = Date.substring(6);
                    temptextView.setText(tempstr+"℃");
                    //TimetextView.setText(Time);
                    //DatetextView.setText("20"+year+"년"+month+"월"+date+"일");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
