package com.example.healthcertification.ui.MyHealth_Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcertification.CustomDialog.CustomDialog_Add;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.ListViewSetting.M_ListViewItem;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Medicine extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private List<M_ListViewItem> myHealth_medicine_DTOS;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mchild;
    private Context mContext;
    private FloatingActionButton fab_main, fab_sub1, fab_sub2, fab_sub3;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    private HorizontalCalendar horizontalCalendar;
    private Button BTN;
    private TextView medicine_effect;
    ListView listView1;
    ArrayList<String> notice_list;
    ArrayAdapter<String> notice_adapter;
    String strDate, key;
    //List<Object> Array = new ArrayList<Object>();
    M_ListViewItem myHealth_medicine_dto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_medicine, container, false);

        notice_list = new ArrayList<String>();

        // 어댑터 생성
        notice_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_single_choice, notice_list);

        // 어댑터 설정
        listView1 = (ListView) view.findViewById(R.id.medicine_listview);
        listView1.setAdapter(notice_adapter);
        listView1.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // 하나의 항목만 선택할 수 있도록 설정

        //텍스트뷰
        medicine_effect = (TextView) view.findViewById(R.id.medicine_effect);

        myHealth_medicine_DTOS = new ArrayList<>();


        //actionbtn setting
        mContext = getActivity().getApplicationContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab_main = (FloatingActionButton) view.findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) view.findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) view.findViewById(R.id.fab_sub2);
        fab_sub3 = (FloatingActionButton)view.findViewById(R.id.fab_cal);
        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        fab_sub3.setOnClickListener(this);

        listView1.setOnItemClickListener(this);

        //calendar setting
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.Myactivity_Calender)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        SetCalender();

        //initDatabase();
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(cal.getTime());
        onDataChange();

        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                CustomDialog_Add dialog = new CustomDialog_Add(getContext());
                dialog.setDialogListener(new CustomDialog_Listener() {
                                             @Override
                                             public void onPositiveClicked(String name) {
                                                 addNotice(name);
                                             }

                                             @Override
                                             public void onSDClicked(String height, String weight) {

                                             }
                                         });
                        dialog.show();
                break;
            case R.id.fab_sub2:
                toggleFab();
                deleteNotice();
                //Toast.makeText(this, "Map Open-!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fab_cal:
                toggleFab();
                //Toast.makeText(this, "Map Open-!", Toast.LENGTH_SHORT).show();
                break;
           }
    }



    private void toggleFab() {
        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.add_btn);
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub3.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            fab_sub3.setClickable(false);
            isFabOpen = false;

        } else {
            fab_main.setImageResource(R.drawable.end_btn);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub3.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            fab_sub3.setClickable(true);
            isFabOpen = true;
        }
    }

    public void onDataChange(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Medicine").child("medicine_list").child(strDate);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notice_list.clear();
                myHealth_medicine_DTOS.clear();
                for (DataSnapshot namedata : snapshot.getChildren()) {
                    myHealth_medicine_DTOS.add(namedata.getValue(M_ListViewItem.class));
                }
                for (int i=0;i<myHealth_medicine_DTOS.size();i++){
                    notice_list.add(myHealth_medicine_DTOS.get(i).getMedicine());

                }
                notice_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void SetCalender() {
        notice_adapter.clear();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                notice_adapter.clear();
                myHealth_medicine_DTOS.clear();
                medicine_effect.setText("");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                strDate = sdf.format(date.getTime());
                mReference = mDatabase.getReference("Medicine").child("medicine_list").child(strDate);
                onDataChange();
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

    public void addNotice(String name){
        mReference = mDatabase.getReference("Medicine").child("medicine_list").child(strDate).push();

        if (!name.isEmpty()) {                        // 입력된 text 문자열이 비어있지 않으면
            //notice_list.add(name);                          // items 리스트에 입력된 문자열 추가
            //notice_adapter.notifyDataSetChanged();// 리스트 목록 갱신

            M_ListViewItem myHealth_medicine_dto = new M_ListViewItem();
            myHealth_medicine_dto.setKey(mReference.getKey());
            myHealth_medicine_dto.setMedicine(name);
            mReference.setValue(myHealth_medicine_dto);
        }
    }

    public void onDeleteItem(int position){
        final M_ListViewItem myHealth_medicine_dto = myHealth_medicine_DTOS.get(position);
        mReference = mDatabase.getReference("Medicine").child("medicine_list").child(strDate);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    M_ListViewItem medicine_dto = snapshot.getValue(M_ListViewItem.class);
                    if (myHealth_medicine_dto.getKey().equals(medicine_dto.getKey())){
                        mReference.child(snapshot.getKey().toString()).removeValue();
                        notice_adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void deleteNotice(){
        int pos = listView1.getCheckedItemPosition(); // 현재 선택된 항목의 첨자(위치값) 얻기
        if (pos != ListView.INVALID_POSITION){
            onDeleteItem(pos);
            medicine_effect.setText("");                //텍스트 클리어

        }else{
            Toast.makeText(getContext() ,"ddd",Toast.LENGTH_LONG );
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object object = (Object)adapterView.getAdapter().getItem(position);
        final String medicine = object.toString();
        NaverSearchTask naverSearchTask = new NaverSearchTask();
        naverSearchTask.execute(medicine);
    }
/*
    private void initDatabase(){
        myHealth_medicine_DTOS.clear();
        notice_adapter.clear();
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(cal.getTime());

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Medicine").child("medicine_list").child(strDate);

        mchild = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                myHealth_medicine_dto = snapshot.getValue(MyHealth_Medicine_DTO.class);
                myHealth_medicine_DTOS.add(myHealth_medicine_dto);
                notice_list.add(myHealth_medicine_dto.getMedicine());
                notice_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                notice_list.clear();
                notice_adapter.clear();
                for (int i=0;i<myHealth_medicine_DTOS.size();i++){
                    notice_list.add(myHealth_medicine_DTOS.get(i).getMedicine());

                }
                notice_adapter.notifyDataSetChanged();

                //MyHealth_Medicine_DTO myHealth_medicine_dto = snapshot.getValue(MyHealth_Medicine_DTO.class);
                //myHealth_medicine_DTOS.add(myHealth_medicine_dto);
                //notice_list.add(myHealth_medicine_dto.getMedicine());

                notice_adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mReference.addChildEventListener(mchild);
    }*/


    private class NaverSearchTask extends AsyncTask<String, String, String > {
        String result;
        String clientId = "TC0_hmN6w_Pu16xgDMFs";
        String clientSecret = "QdYaVALP7l";
        StringBuffer sb = new StringBuffer();

        //이부분에서 파싱관련 모든 작업을 함
        @Override
        protected String doInBackground(String... medicine) {

            int display = 10;
            try{
                String text = URLEncoder.encode(medicine[0], "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/encyc.xml?query="+text+"&display"+display+"&";

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                String tag;
                //inputStream으로부터 xml값 받기
               xpp.setInput(new InputStreamReader(con.getInputStream(), "UTF-8"));

                xpp.next();
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            tag = xpp.getName();

                            if (tag.equals("item")) ; //첫번째 검색 결과
                            else if (tag.equals("title")) {

                            } else if (tag.equals("description")) {

                            }
                            else if (tag.equals("link")) {
                                sb.append("link : ");
                                xpp.next();

                                sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                                sb.append("\n");
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                return e.toString();
            }
            //문자열 자르기
            String str = sb.toString();
            String response = str.substring(str.lastIndexOf("https://terms.naver.com/"));
            try {
                Document doc = Jsoup.connect(response).get();
                String effect = doc.select("meta[property=og:description]").get(0).attr("content");
                String target = "[효능효과]";
                int target_num = effect.indexOf(target);
                result = effect.substring(target_num,(effect.substring(target_num).indexOf("[용법용량]")+target_num));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        //파싱결과에서 어떤걸 작업할지
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            medicine_effect.setText(result);
        }
    }
}
