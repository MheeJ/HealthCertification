package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustomDialog_Remove;
import com.example.healthcertification.ListViewSetting.Temp_ListVeiwAdapter;
import com.example.healthcertification.ListViewSetting.Temp_ListViewItem;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_Temperature extends Fragment implements View.OnClickListener {

    private List<Temp_ListViewItem> temp_listViewItems;
    private ListView Temp_ListView;
    private Temp_ListVeiwAdapter temp_listVeiwAdapter;
    private FloatingActionButton Temp_ADD_Btn;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference("Temperature");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


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

    public void temp_add(){
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy.MM.dd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("hh:mm");
        String getDate = simpleDate.format(mDate);
        String getTime = simpleTime.format(mDate);
        double Temp = 38;
        String tempstr = Temp + "℃";
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
        pushdata(getDate, getTime, tempstr, State);
    }

    private void pushdata(String Date, String Time,String Temp, String State){
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
                }
                temp_listVeiwAdapter.notifyDataSetChanged();
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


}
