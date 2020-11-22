package com.example.healthcertification.ui.MyHealth_Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcertification.BLEMiddleware.BLECommunication;
import com.example.healthcertification.BLEMiddleware.BLEListener;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustomDialog_Remove;
import com.example.healthcertification.ListViewSetting.Temp_ListVeiwAdapter;
import com.example.healthcertification.ListViewSetting.Temp_ListViewItem;
import com.example.healthcertification.MainActivity;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.altbeacon.beacon.Beacon;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Temperature extends Fragment implements View.OnClickListener, BLEListener {

    private List<Temp_ListViewItem> temp_listViewItems;
    private ListView Temp_ListView;
    private Temp_ListVeiwAdapter temp_listVeiwAdapter;
    private FloatingActionButton Temp_ADD_Btn;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    String tempstr;
    private TextView temptextview;

    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    ///////////beacon/////////
    private BLECommunication bleCommunication;

    public MyHealth_Temperature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_temperature, container, false);

        temp_listVeiwAdapter = new Temp_ListVeiwAdapter();
        Temp_ListView = (ListView)view.findViewById(R.id.ht_listview);
        Temp_ListView.setAdapter(temp_listVeiwAdapter);
        Temp_ADD_Btn = (FloatingActionButton)view.findViewById(R.id.temp_add_btn);
        Temp_ADD_Btn.setOnClickListener(this);
        temp_listViewItems = new ArrayList<>();
        temptextview = view.findViewById(R.id.fragment2_bodytemp);

        ////////////////beacon // (1) initialize -> (2)setListener -> (3) create_topic
        bleCommunication = new BLECommunication();
        bleCommunication.initialize(getContext());
        bleCommunication.setBleListener(this);
        bleCommunication.createTopic("tp");
        ///////////////beacon

        onDataChange();

        Temp_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                CustomDialog_Remove dialog_Temp_remove = new CustomDialog_Remove(getContext());
                dialog_Temp_remove.Temp_Remove_Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        onDelteitem(pos);
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                dialog_Temp_remove.show();
            }
        });
        return view;
    }

    ///////////////beacon
    @Override
    public void onBLEDataAvailable(Beacon beacon, String data) {
        temptextview.setText(data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //bleCommunication.unbind(getActivity());
    }

    public void temp_add(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy.MM.dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
        String getDate = simpleDate.format(mDate);
        String getTime = simpleTime.format(mDate);
        double Temp = 38;
        //tempstr = Temp + "℃";
        String State = "정상";

        if (Temp > 36.1 && Temp < 36.8){
            State = "정상";
        }else if(Temp > 36.8 && Temp < 37){
            State = "미열";
        }else if(Temp > 37){
            State = "고열";
        }else{
            State = "저체온";
        }

/*        temp_listVeiwAdapter.Temp_addItem("20.11.16","11:45","36.6"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem("20.11.17","11:30","36.5"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem("20.11.18","11:40","36.6"+"℃","정상");
        temp_listVeiwAdapter.Temp_addItem(getDate,getTime,"36.8"+"℃","정상");*/
        pushdata(getDate, getTime, Temp, State);
    }

    private void pushdata(String Date, String Time,double Temp, String State){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Temperature").push();

        Temp_ListViewItem temp_listViewItem = new Temp_ListViewItem();

        temp_listViewItem.setDate(Date);
        temp_listViewItem.setTime(Time);
        temp_listViewItem.setState(State);
        temp_listViewItem.setTemp(Temp);
        temp_listViewItem.setKey(mReference.getKey());
        mReference.setValue(temp_listViewItem);

    }

    private void onDelteitem(int position){
        final Temp_ListViewItem temp_listViewItem = temp_listViewItems.get(position);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Temperature");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    Temp_ListViewItem item = snapshot.getValue(Temp_ListViewItem.class);
                    if (temp_listViewItem.getKey().equals(item.getKey())){
                        mReference.child(snapshot.getKey().toString()).removeValue();
                        temp_listVeiwAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onDataChange(){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("Temperature");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp_listViewItems.clear();
                temp_listVeiwAdapter.clear();
                for (DataSnapshot namedata : snapshot.getChildren()){
                    temp_listViewItems.add(namedata.getValue(Temp_ListViewItem.class));
                }
                for (int i =0; i<temp_listViewItems.size(); i++){
                    Temp_ListViewItem temp_listViewItem = (Temp_ListViewItem)temp_listViewItems.get(i);
                    temp_listVeiwAdapter.Temp_addItem(temp_listViewItem);
                    //tempstr = temp_listViewItem.getTemp();
                    double temp = temp_listViewItem.getTemp();
                    String tempstr = String.format("%.1f",temp);
                    temptextview.setText(tempstr+"℃");
                    if (temp > 37){
                        NotificationSomethings();
                    }
                }
                temp_listVeiwAdapter.notifyDataSetChanged();

                //Temp_ListViewItem temp_listViewItem = (Temp_ListViewItem)temp_listViewItems.get(temp_listViewItems.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temp_add_btn:
                temp_add();
        }
    }

    public void NotificationSomethings() {


        NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(getContext(), MainActivity.class);
        //notificationIntent.putExtra("notificationId", count); //전달할 값
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), NOTIFICATION_CHANNEL_ID)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_11)) //BitMap 이미지 요구
                .setContentTitle("체온 경고")
                .setContentText("체온이 높습니다 가까운 병원에서 검진받으세요")
                // 더 많은 내용이라서 일부만 보여줘야 하는 경우 아래 주석을 제거하면 setContentText에 있는 문자열 대신 아래 문자열을 보여줌
                //.setStyle(new NotificationCompat.BigTextStyle().bigText("더 많은 내용을 보여줘야 하는 경우..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시 ResultActivity로 이동하도록 설정
                .setAutoCancel(true);

        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.logo_11); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남
            CharSequence channelName  = "노티페케이션 채널";
            String description = "오레오 이상을 위한 것임";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName , importance);
            channel.setDescription(description);

            // 노티피케이션 채널을 시스템에 등록
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }else builder.setSmallIcon(R.drawable.logo_11); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        assert notificationManager != null;
        notificationManager.notify(1234, builder.build()); // 고유숫자로 노티피케이션 동작시킴

    }



}
