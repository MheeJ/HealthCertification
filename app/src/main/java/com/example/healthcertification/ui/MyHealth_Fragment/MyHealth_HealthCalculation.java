package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcertification.CustomDialog.CustomDialog_HC_Add;
import com.example.healthcertification.CustomDialog.CustomDialog_Remove;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.ListViewSetting.HC_ListViewAdapter;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHealth_HealthCalculation extends Fragment implements View.OnClickListener{
    private List<HC_ListViewItem> hc_listViewItems;
    private FloatingActionButton HC_Add_Btn;
    private ListView HC_ListView;
    private TextView GoodWeight_View, Bmi_View, BmiState_View;
    private HC_ListViewAdapter HC_ListView_Adapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference("HealthCalculation");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    String getTime;

    public MyHealth_HealthCalculation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myheallth_healthcalculation, container, false);


        HC_ListView_Adapter = new HC_ListViewAdapter();
        HC_ListView = (ListView)view.findViewById(R.id.fragment3_listview);
        HC_ListView.setAdapter(HC_ListView_Adapter);
        HC_Add_Btn = (FloatingActionButton)view.findViewById(R.id.hc_add_btn);
        HC_Add_Btn.setOnClickListener(this);
        GoodWeight_View = (TextView)view.findViewById(R.id.hc_goodweight_view);
        Bmi_View = (TextView)view.findViewById(R.id.hc_bmi_view);
        BmiState_View = (TextView)view.findViewById(R.id.hc_bmistate_view);
        hc_listViewItems = new ArrayList<>();

        onDataChange();
        //listview 클릭시, 삭제 확인 팝업 및 삭제기능
        HC_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                CustomDialog_Remove dialog_hc_remove = new CustomDialog_Remove(getContext());
                dialog_hc_remove.HC_Remove_Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        onDeleteItem(pos);
                        //HC_ListView_Adapter.removeItem(pos);
                        //HC_ListView_Adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                dialog_hc_remove.show();
            }
        });
        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hc_add_btn:
                CustomDialog_HC_Add dialog = new CustomDialog_HC_Add(getContext());
                dialog.HC_DialogListener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {

                    }

                    @Override
                    public void onSDClicked(String height, String weight) {
                        makeItem(height, weight);
                    }
                });
                dialog.show();
                break;
        }
    }

    public void makeItem(String height, String weight){
        // 첫 번째 아이템 추가.
        float heightFLO = Float.parseFloat(height);
        float weightFLO = Float.parseFloat(weight);
        float Bmi = (weightFLO/(heightFLO*heightFLO))*10000;
        String BmiStr = String.format("%.2f",Bmi);
        String my_state;
        if(Bmi > 25.0){
            my_state = "비만";
        }
        else if(25.0 > Bmi && Bmi > 23.0){
            my_state ="과체중";
        }
        else if(23.0 > Bmi && Bmi > 18.5){
            my_state = "정상";
        }
        else {
            my_state = "저체중";
        }
        double goodweight1 = (18.5*(heightFLO*heightFLO))/10000;
        String goodweight1_Str = String.format("%.2f",goodweight1);
        double goodweight2 = (23.0*(heightFLO*heightFLO))/10000;
        String goodweight2_Str = String.format("%.2f",goodweight2);
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd");
        getTime = simpleDate.format(mDate);

        //HC_ListView_Adapter.addItem(getTime,height, weight, BmiStr, my_state);
        GoodWeight_View.setText(goodweight1_Str+" ~ \n"+goodweight2_Str+" kg");
        Bmi_View.setText(BmiStr);
        BmiState_View.setText("( "+my_state+" )");

        pushData(getTime, height, weight, BmiStr, my_state);

    }

    private void pushData(String getTime, String height, String weight, String bmiStr, String my_state){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("HealthCalculation").push();

               HC_ListViewItem hc_listViewItem = new HC_ListViewItem();
               hc_listViewItem.setKey(mReference.getKey());
               hc_listViewItem.setDate(getTime);
               hc_listViewItem.setHeight(height);
               hc_listViewItem.setWeight(weight);
               hc_listViewItem.setBmi(bmiStr);
               hc_listViewItem.setState(my_state);
               mReference.setValue(hc_listViewItem);
    }

    private void onDataChange(){

        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("HealthCalculation");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hc_listViewItems.clear();
                HC_ListView_Adapter.clear();
                for (DataSnapshot namedata : snapshot.getChildren()) {
                    hc_listViewItems.add(namedata.getValue(HC_ListViewItem.class));
                }
                for (int i=0; i<hc_listViewItems.size(); i++){
                    HC_ListViewItem hc_listViewItem = (HC_ListViewItem)hc_listViewItems.get(i);
                    HC_ListView_Adapter.addItem(hc_listViewItem);
                }
                HC_ListView_Adapter.notifyDataSetChanged();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onDeleteItem(int position){
        final HC_ListViewItem hc_listViewItem = hc_listViewItems.get(position);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference("HealthCalculation");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
                    HC_ListViewItem item = snapshot.getValue(HC_ListViewItem.class);
                    if (hc_listViewItem.getKey().equals(item.getKey())){
                        mReference.child(snapshot.getKey().toString()).removeValue();
                        HC_ListView_Adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
