package com.example.healthcertification.ui.MyHealth_Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Medicine extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{

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

    public MyHealth_Medicine() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_medicine, container, false);


      /*  //medicine list setting
        ListView listView = (ListView) view.findViewById(R.id.medicine_listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                List_medicine
        );
        listView.setAdapter(listViewAdapter);*/
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
                dialog.setDialogListener(new CustomDialog_Listener() {  // MyDialogListener 를 구현
                    @Override
                    public void onPositiveClicked(String name) {
                        addNotice(name);
                    }

                    @Override
                    public void onNegativeClicked() {
                        Log.d("MyDialogListener","onNegativeClicked");
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

    public void SetCalender(){
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

    public void addNotice(String name){
        if (!name.isEmpty()) {                        // 입력된 text 문자열이 비어있지 않으면
            notice_list.add(name);                          // items 리스트에 입력된 문자열 추가
            notice_adapter.notifyDataSetChanged();// 리스트 목록 갱신
        }
    }

    public void deleteNotice(){
        int pos = listView1.getCheckedItemPosition(); // 현재 선택된 항목의 첨자(위치값) 얻기
        if (pos != ListView.INVALID_POSITION) {// 선택된 항목이 있으면
            notice_list.remove(pos);                       // items 리스트에서 해당 위치의 요소 제거
            listView1.clearChoices();                 // 선택 해제
            notice_adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Object object = (Object)adapterView.getAdapter().getItem(position);
        final String medicine = object.toString();
        NaverSearchTask naverSearchTask = new NaverSearchTask();
        naverSearchTask.execute(medicine);


    }

    private class NaverSearchTask extends AsyncTask<String, String, String > {
        String result;
        String clientId = "TC0_hmN6w_Pu16xgDMFs";
        String clientSecret = "QdYaVALP7l";
        StringBuffer sb = new StringBuffer();

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
                            tag = xpp.getName(); //태그 이름 얻어오기

                            if (tag.equals("item")) ; //첫번째 검색 결과
                            else if (tag.equals("title")) {

                                //sb.append("제목 : ");
                                //xpp.next();
                                //sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                                //sb.append("\n");

                            } else if (tag.equals("description")) {
                                //sb.append("내용 : ");
                                //xpp.next();

                                //sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                                //sb.append("\n");
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
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            medicine_effect.setText(result);
        }
    }
/*
    private String getNaverSearch(String keyword){
        String clientId = "TC0_hmN6w_Pu16xgDMFs";
        String clientSecret = "QdYaVALP7l";
        StringBuffer sb = new StringBuffer();
        int display = 100;
        try{
            String text = URLEncoder.encode(keyword, "UTF-8");
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
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("item")) ; //첫번째 검색 결과
                        else if (tag.equals("title")) {

                            //sb.append("제목 : ");
                            //xpp.next();
                            //sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            //sb.append("\n");

                        } else if (tag.equals("description")) {
                            //sb.append("내용 : ");
                            //xpp.next();

                            //sb.append(xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", ""));
                            //sb.append("\n");
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
        return sb.toString();
    }*/
}
