package com.example.healthcertification.ui.MyActivity_Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class MyActivity extends Fragment implements View.OnClickListener{

    private MyActivity_ViewModel myActivity_viewModel;
    private TextView Address;
    private FloatingActionButton Activity_main_btn, Activity_hospital_btn, Activity_permacy_btn;

    private ProgressBar progressBar;
    private  String lo = "", la = "";
    public double latitude;
    public double longitude;
    private LinearLayout linearLayout;
    private HorizontalCalendar horizontalCalendar;
    private Animation btn_open, btn_close;
    private Context mContext;
    private boolean isFabOpen = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //fragment3_viewModel = ViewModelProviders.of(this).get(Fragment3_ViewModel.class);
        myActivity_viewModel = new ViewModelProvider(requireActivity()).get(MyActivity_ViewModel.class);
        View view = inflater.inflate(R.layout.fragment_myactivity, container, false);

        mContext = getActivity().getApplicationContext();
        btn_open = AnimationUtils.loadAnimation(mContext,R.anim.fab_open);
        btn_close = AnimationUtils.loadAnimation(mContext,R.anim.fab_close);
        Activity_main_btn = (FloatingActionButton)view.findViewById(R.id.activity_main_btn);
        Activity_main_btn.setOnClickListener(this);
        Activity_hospital_btn = (FloatingActionButton)view.findViewById(R.id.activity_hospital_btn);
        Activity_hospital_btn.setOnClickListener(this);
        Activity_permacy_btn = (FloatingActionButton)view.findViewById(R.id.activity_pharmacy_btn);
        Activity_permacy_btn.setOnClickListener(this);

        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        //calendar : https://github.com/Mulham-Raee/Horizontal-Calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.Myactivity_Calender)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        SetMyActivityCalender();

        linearLayout = (LinearLayout)view.findViewById(R.id.map_view);

        //밑에처럼 linearLayout.addView에 지도 넣으면 됨
      /*  tmapview = new TMapView(getActivity());
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);*/



        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.activity_main_btn:
                //프로그레스바 사용법
                //progressBar.setVisibility(View.VISIBLE);
                toggleFab();
                break;
            case R.id.activity_hospital_btn:
                toggleFab();
                break;
            case R.id.activity_pharmacy_btn:
                toggleFab();
                break;

        }
    }



    private void SetMyActivityCalender(){
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {

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

    private void toggleFab() {
        if (isFabOpen) {
            Activity_main_btn.setImageResource(R.drawable.add_btn);
            Activity_hospital_btn.startAnimation(btn_close);
            Activity_permacy_btn.startAnimation(btn_close);
            Activity_hospital_btn.setClickable(false);
            Activity_hospital_btn.setClickable(false);
            isFabOpen = false;

        } else {
            Activity_main_btn.setImageResource(R.drawable.end_btn);
            Activity_hospital_btn.startAnimation(btn_open);
            Activity_permacy_btn.startAnimation(btn_open);
            Activity_hospital_btn.setClickable(true);
            Activity_permacy_btn.setClickable(true);
            isFabOpen = true;
        }
    }

}
