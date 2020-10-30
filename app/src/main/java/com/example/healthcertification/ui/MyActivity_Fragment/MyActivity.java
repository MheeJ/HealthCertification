package com.example.healthcertification.ui.MyActivity_Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthcertification.Background.GPSBackgroundService;
import com.example.healthcertification.R;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class MyActivity extends Fragment implements View.OnClickListener{

    private MyActivity_ViewModel myActivity_viewModel;
    private TextView Address;
    private ImageView Fragment3_Restart_Btn;
    MyReceiver myReceiver;
    private String MyAddress_Longitude, MyAddress_Latitude, ServiceData_LongitudeList, ServiceData_LatitudeList;
    List<String> MyLocation_LongitudeList;
    List<String>  MyLocation_LatitudeList;
    private String mLongtitudeX = "&mapX=", mLatitudeY = "&mapY=", mPositionTail = "&radius=20000&listYN=Y&arrange=A";
    public TMapView tmapview;
    private ProgressBar progressBar;
    private  String lo = "", la = "";
    public double latitude;
    public double longitude;
    private LinearLayout linearLayout;
    private HorizontalCalendar horizontalCalendar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //fragment3_viewModel = ViewModelProviders.of(this).get(Fragment3_ViewModel.class);
        myActivity_viewModel = new ViewModelProvider(requireActivity()).get(MyActivity_ViewModel.class);
        View view = inflater.inflate(R.layout.fragment_myactivity, container, false);
        Address = (TextView)view.findViewById(R.id.fragment3_address);
        Fragment3_Restart_Btn = (ImageView)view.findViewById(R.id.fragment3_restart);
        Fragment3_Restart_Btn.setOnClickListener((View.OnClickListener) this);
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
        tmapview = new TMapView(getActivity());
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);



        return view;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fragment3_restart:
                progressBar.setVisibility(View.VISIBLE);
                prepareBackgroundService();
                getActivity().startService(new Intent(getActivity(), GPSBackgroundService.class));
                //fragment3_viewmodel에 사용자 위도 경도 저장
                myActivity_viewModel.setLogitudeData(longitude);
                myActivity_viewModel.setLatitudeData(latitude);

                break;
             //makeText();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        //fragment3_viewModel = new ViewModelProvider(requireActivity()).get(Fragment3_ViewModel.class);
        myActivity_viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Address.setText(s);
            }
        });
        myActivity_viewModel.getLatitudeData().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                latitude = aDouble;
            }
        });
        myActivity_viewModel.getLogitudeData().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                longitude = aDouble;
            }
        });
    }

    //서비스 시작하기전 준비
    private void prepareBackgroundService(){
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GPSBackgroundService.MY_ACTION1);
        getActivity().registerReceiver(myReceiver, intentFilter);
    }

    //서비스에서 데이터 가져오는 부분
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            //사용자가 이동한 전체 위치
            ServiceData_LongitudeList = arg1.getStringExtra("ServiceData_longitudeList");
            ServiceData_LatitudeList = arg1.getStringExtra("ServiceData_latitudeList");
            longitude = arg1.getDoubleExtra("double_longitude",0);
            latitude = arg1.getDoubleExtra("double_latitude",0);
            getMyLocation();
            getPosition();
            //tmapview에 사용자 위치 표시
            tmapview.setCenterPoint(longitude, latitude); //->현재위치 = centerpoint
            tmapview.setLocationPoint(longitude, latitude);
            //프로그래스바 끝
            progressBar.setVisibility(View.GONE);
            getActivity().stopService(new Intent(getActivity(),GPSBackgroundService.class));
        }
    }

    //사용자의 현재 위치 MyAddress_Longitude, MyAddress_Latitude 에 저장해놓는 구문임.
    public void getMyLocation(){
        MyLocation_LongitudeList = Arrays.asList(ServiceData_LongitudeList.split("#"));
        if(! MyLocation_LongitudeList.isEmpty()){
            MyAddress_Longitude =  MyLocation_LongitudeList.get( MyLocation_LongitudeList.size()-1);
        }
        MyLocation_LatitudeList = Arrays.asList(ServiceData_LatitudeList.split("#"));
        if(! MyLocation_LatitudeList.isEmpty()){
            MyAddress_Latitude =  MyLocation_LatitudeList.get(MyLocation_LatitudeList.size()-1);
        }
    }

    private void getPosition(){
        lo = MyAddress_Longitude;
        la = MyAddress_Latitude;
        mLongtitudeX = mLongtitudeX + lo;
        mLatitudeY = mLatitudeY + la;


        final Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> list = null;
        if(lo != null && la != null){
            try {
                double d1 = Double.parseDouble(lo);
                double d2 = Double.parseDouble(la);
                list = geocoder.getFromLocation(
                        d2, // 위도
                        d1, // 경도
                        2); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (list != null) {
                if (list.size()==0) {
                    Address.setText("해당되는 주소 정보는 없습니다");
                } else {
                    Address.setText(list.get(0).toString());
                    String cut[] = list.get(0).toString().split(" ");
                    for(int i=0; i<cut.length; i++){
                        System.out.println("cut["+i+"] : " + cut[i]);
                    }
                    Address.setText(cut[1] + " " + cut[2] + " " + cut[3] );
                    myActivity_viewModel.setLiveData(cut[1] + " " + cut[2] + " " + cut[3] );
                }
            }
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

}
