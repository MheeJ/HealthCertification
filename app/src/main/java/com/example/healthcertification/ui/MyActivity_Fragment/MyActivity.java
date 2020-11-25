package com.example.healthcertification.ui.MyActivity_Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthcertification.ListViewSetting.Activity_ListViewAdapter;
import com.example.healthcertification.ListViewSetting.HC_ListViewItem;
import com.example.healthcertification.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;


public class MyActivity extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentLatLng;
    private FileStore fileStore = new FileStore();
    private Marker currentMarker = null;
    private final LatLng mDefaultLocation = new LatLng(37.56, 126.97);
    private MapView mapView = null;
    private boolean mLocationPermissionGranted;
    private static final String TAG = MyActivity.class.getSimpleName();
    private PolylineOptions polylineOptions;
    private ArrayList<LatLng> arraypoints;
    private ClusterManager<HospitalInfo> clusterManager;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<EncryptedItem> encryption_listViewItems = new ArrayList<>();
    private List<CompareItem> compareItems = new ArrayList<>();

    private MyActivity_ViewModel myActivity_viewModel;
    private TextView Address;
    private FloatingActionButton Activity_main_btn, Activity_hospital_btn, Activity_permacy_btn;

    private ProgressBar progressBar;
    private String lo = "", la = "";
    public double latitude;
    public double longitude;
    private LinearLayout linearLayout;
    private HorizontalCalendar horizontalCalendar;
    private Animation btn_open, btn_close;
    private Context mContext;
    private boolean isFabOpen = false;
    private TextView activity_status;
    private TextView activity_statusinfo;
    private ListView pandemic_status, pandemiclistView;
    private Activity_ListViewAdapter activity_listViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //fragment3_viewModel = ViewModelProviders.of(this).get(Fragment3_ViewModel.class);
        myActivity_viewModel = new ViewModelProvider(requireActivity()).get(MyActivity_ViewModel.class);
        View view = inflater.inflate(R.layout.fragment_myactivity, container, false);

        mContext = getActivity().getApplicationContext();
        btn_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        btn_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        activity_status = (TextView)view.findViewById(R.id.activity_status);
        pandemic_status = (ListView) view.findViewById(R.id.medicine_listview);
        //activity_statusinfo = (TextView)view.findViewById(R.id.activity_statusinfo);
        Activity_main_btn = (FloatingActionButton) view.findViewById(R.id.activity_main_btn);
        Activity_main_btn.setOnClickListener(this);
        Activity_hospital_btn = (FloatingActionButton) view.findViewById(R.id.activity_hospital_btn);
        Activity_hospital_btn.setOnClickListener(this);
        Activity_permacy_btn = (FloatingActionButton) view.findViewById(R.id.activity_pharmacy_btn);
        Activity_permacy_btn.setOnClickListener(this);
        activity_listViewAdapter = new Activity_ListViewAdapter();
        pandemiclistView = (ListView)view.findViewById(R.id.pandemic_info);
        pandemiclistView.setAdapter(activity_listViewAdapter);


//        calorystatus(fileStore.ReadCalory("calory", false));
        activity_status.setText(fileStore.ReadCalory("calory", false)+"kcal");


        compareEncryptedLog(Calendar.getInstance().getTime());
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
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

        mapView = (MapView) view.findViewById(R.id.map_view);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_btn:
                //프로그레스바 사용법
                //progressBar.setVisibility(View.VISIBLE);
                toggleFab();
                break;
            case R.id.activity_hospital_btn:
                toggleFab();
                mMap.clear();
                HospitalAPI hospitalAPI = new HospitalAPI();
                for(int i = 1; i<11;i++) {
                    hospitalAPI.connect(currentLatLng.longitude, currentLatLng.latitude, i);
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
                break;
            case R.id.activity_pharmacy_btn:
                toggleFab();
                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference("EncryptedLog").push();
                EncryptedItem encryptedItem = new EncryptedItem();
                fileStore.ReadEncryptionfile(CurrentDate());
                encryptedItem.setDate(CurrentDate());
                encryptedItem.setLog(fileStore.getEncryptline());
                mReference.setValue(encryptedItem);


                break;
        }
    }

    public void setHospitalInfos(HospitalInfo hospitalInfo){
        clusterManager.addItem(hospitalInfo);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setGravity(Gravity.CENTER);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

    private void SetMyActivityCalender() {
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(final Calendar date, int position) {
                mMap.clear();
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                locationlog(sdf.format(date.getTime()));
                mDatabase = FirebaseDatabase.getInstance();
                mReference = mDatabase.getReference("Calory");

                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<CaloryItem> calory_listViewItems = new ArrayList<CaloryItem>();

                        for (DataSnapshot namedata : snapshot.getChildren()){
                            calory_listViewItems.add(namedata.getValue(CaloryItem.class));
                        }
                        for (int i =0; i<calory_listViewItems.size(); i++){
                            CaloryItem calory_listViewItem = (CaloryItem)calory_listViewItems.get(i);
                            String strDate1 = sdf.format(date.getTime());
                            String strDate2 = calory_listViewItem.getDate();
                            if(strDate1.equals(CurrentDate()))
                                activity_status.setText(fileStore.ReadCalory("calory", false)+"kcal");
                            if (strDate1.equals(strDate2)){
                                //calorystatus(calory_listViewItem.getCalory());
                                activity_status.setText(calory_listViewItem.getCalory()+"kcal");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                compareEncryptedLog(date.getTime());


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

    private void compareEncryptedLog(final Date date){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("EncryptedLog");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int number = 0;
                activity_listViewAdapter.clear();
                compareItems.clear();
                encryption_listViewItems.clear();
                for (DataSnapshot namedata : snapshot.getChildren()) {
                    encryption_listViewItems.add(namedata.getValue(EncryptedItem.class));
                }
                for (int i = 0; i < encryption_listViewItems.size(); i++) {
                    EncryptedItem encryption_listViewItem = (EncryptedItem) encryption_listViewItems.get(i);
                    if(sdf.format(date.getTime()).equals(encryption_listViewItem.getDate())) {
                        number++;
                        fileStore.ReadEncryptionfile(encryption_listViewItem.getDate());
                        int count = (encryption_listViewItem.getLog().size() < fileStore.getEncryptline().size()) ? encryption_listViewItem.getLog().size() : fileStore.getEncryptline().size();
                        int compareTime = 0;
                        for (int j = 0; j < count; j++) {
                            if (encryption_listViewItem.getLog().get(j).equals(fileStore.getEncryptline().get(j)))
                                compareTime++;
                        }
                        CompareItem compareItem = new CompareItem();
                        String comparehour = String.valueOf(compareTime / 6);
                        String comparemin = String.valueOf((compareTime % 6) * 10);
                        compareItem.setCompare(comparehour + "시간" + comparemin + "분");
                        compareItem.setConfirmed(number);
                        compareItems.add(compareItem);
                    }
  /*                  pandemic_status.
                    Toast.makeText(mContext, CurrentDate() + "\n" + "확진자와 겹친시간:" + String.valueOf(compareTime/6) + "시간 " + String.valueOf((compareTime%6)*10) + "분", Toast.LENGTH_SHORT).show();*/
                } for (int i =0; i<compareItems.size();i++){
                    CompareItem compareItem = (CompareItem)compareItems.get(i);
                    activity_listViewAdapter.addItem(compareItem);

                }
                activity_listViewAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private String CurrentDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(today);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        currentLatLng = fileStore.Recentlocation(CurrentDate());
        clusterManager = new ClusterManager<HospitalInfo>(getContext(), mMap);
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<HospitalInfo>() {
            @Override
            public boolean onClusterClick(Cluster<HospitalInfo> cluster) {
                LatLngBounds.Builder builder_c = LatLngBounds.builder();
                for (ClusterItem item : cluster.getItems()) {
                    builder_c.include(item.getPosition());
                }
                LatLngBounds bounds_c = builder_c.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds_c, 12));
                float zoom = mMap.getCameraPosition().zoom - 1f;
                mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
                return true;
            }
        });
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        setDefaultLocation();
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setDefaultLocation() {
        if(currentLatLng != null){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            mMap.moveCamera(cameraUpdate);
        }else {
            if (currentMarker != null) currentMarker.remove();

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(mDefaultLocation);
            markerOptions.title("위치정보 가져올 수 없음");
            markerOptions.snippet("위치 퍼미션과 GPS 활성 여부 확인하세요");
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentMarker = mMap.addMarker(markerOptions);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15);
            mMap.moveCamera(cameraUpdate);
        }
    }


    @Override
    public void onStart() { // 유저에게 Fragment가 보이도록 해준다.
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "onStart ");
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() { // 유저에게 Fragment가 보여지고, 유저와 상호작용이 가능하게 되는 부분
        super.onResume();
        mapView.onResume();
        if (mLocationPermissionGranted) {
            Log.d(TAG, "onResume : requestLocationUpdates");
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (mMap != null)
                mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() { // 프래그먼트와 관련된 View 가 제거되는 단계
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        // Destroy 할 때는, 반대로 OnDestroyView에서 View를 제거하고, OnDestroy()를 호출한다.
        super.onDestroy();
        mapView.onDestroy();
    }

    private void locationlog(String input_day){
        polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLUE);
        polylineOptions.width(10);
        arraypoints = fileStore.Readfile(input_day);
        polylineOptions.addAll(arraypoints);
        mMap.addPolyline(polylineOptions);

        for(int i = 0; i<fileStore.makerLocation().size();i++){
            setCurrentLocation(fileStore.makerLocation().get(i), "머무른 시간", fileStore.makerTime().get(i));
        }
    }

    private void setCurrentLocation(LatLng location, String markerTitle, String markerSnippet) {
        LatLng currentLatLng = location;

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        currentMarker = mMap.addMarker(markerOptions);
    }

    public class HospitalAPI /*extends AsyncTask<String, Void, Document>*/ {
        private String TAG = "Parser";

        public void connect(double lon, double lot, int pageNumber) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ArrayList<HospitalInfo> result = new ArrayList<HospitalInfo>();

            try {
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=505qFbb%2BY8QCB7mYhHXMlbKTYOikGxtMygkYjjbVIjz4vCUefKUQRFRoX9DUdL6jhi%2FVqdtAhLKl1D9TXltonw%3D%3D"); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("WGS84_LON","UTF-8") + "="+String.valueOf(lon) +"&"+ URLEncoder.encode("WGS84_LAT", "UTF-8")+"="+String.valueOf(lot)); /**/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8")+"=10" + "&" + URLEncoder.encode("pageNo", "UTF-8")+"="+pageNumber); /**/
                String s_url = urlBuilder.toString();
                URL targetURL = new URL(s_url); //URL을 생성합니다.



                InputStream is = targetURL.openStream(); //open Stream을 사용하여 InputStream을 생성합니다.

                //XmlPullParser를 사용하기 위해서 생성합니다.
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(is, "utf-8"); //euc-kr로 언어를 설정합니다. utf-8로 하니깐 깨지더군요
                is.close(); //inputstream을 닫습니다.
                parseHospital(parser);
//            return result; //받아온 xml을 파싱하여서 리턴해줍니다.

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//        return null;
        }

        private void parseHospital(XmlPullParser parser) throws XmlPullParserException, IOException {
            String tag; //tag를 받기위한 임시 변수입니다.
            int parserEvent = parser.getEventType();
            String Name = null;
            String Tel = null;
            String StartTime = null;
            String EndTime = null;
            Double Latitude = 0.0;
            Double Longitude = 0.0;
            boolean Endparse = true;
            boolean distance = false;
            boolean dutyname = false;
            boolean dutyTel1 = false;
            boolean startTime = false;
            boolean endTime = false;
            boolean latitude = false;
            boolean longitude = false;
            while(parserEvent != XmlPullParser.END_DOCUMENT) {
                switch(parserEvent) {
                    case XmlPullParser.START_DOCUMENT: //xml의 <> 부분을 만나게 되면 실행되게 됩니다.
                        break;
                    case XmlPullParser.START_TAG: //xml의 <> 부분을 만나게 되면 실행되게 됩니다.
                        tag = parser.getName();
                        if(tag.equals("distance")) {
                            distance = true;
                        }else if(tag.equals("dutyName")) {
                            dutyname = true;
                        }else if(tag.equals("dutyTel1")) {
                            dutyTel1 = true;
                        }else if(tag.equals("startTime")) {
                            startTime = true;
                        }else if(tag.equals("endTime")) {
                            endTime = true;
                        }else if(tag.equals("latitude")) {
                            latitude = true;
                        }else if(tag.equals("longitude")) {
                            longitude = true;
                        }
                        break;

                    case XmlPullParser.TEXT: //xml의 <> 부분을 만나게 되면 실행되게 됩니다.
                        if(distance){
                            Double dis = Double.parseDouble(parser.getText());
                            if(dis<0||dis>10)
                                Endparse = false;
                            distance = false;
                        }
                        if(dutyname){
                            Name = parser.getText();
                            dutyname = false;
                        }else if(dutyTel1){
                            Tel = parser.getText();
                            dutyTel1 = false;
                        }else if(startTime){
                            StartTime = parser.getText();
                            startTime = false;
                        }else if(endTime){
                            EndTime = parser.getText();
                            endTime = false;
                        }else if(latitude){
                            Latitude = Double.parseDouble(parser.getText());
                            latitude = false;
                        }else if(longitude){
                            Longitude = Double.parseDouble(parser.getText());
                            longitude = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if(endTag.equals("item")) {
                            setHospitalInfos(new HospitalInfo(Name, Tel, StartTime, EndTime, Latitude, Longitude));
                        }
                        break;
                }
                if(parserEvent == 3&&parser.getName().equals("response"))
                    break;
                else if(!Endparse)
                    break;
                else
                    parserEvent = parser.next(); //다음 태그를 읽어 들입니다.
            }
//        return al_hospitalInfo; //완료되면 arrayList를 리턴합니다.
        }
    }
}
