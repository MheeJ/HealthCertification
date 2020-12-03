package com.example.healthcertification.ui.MyHealth_Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.healthcertification.CustomDialog.CustomDialog_Remove;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.CustomDialog.CustonDialog_Test;
import com.example.healthcertification.ListViewSetting.HC_ListViewItem;

import com.example.healthcertification.ListViewSetting.SD_ListViewItem;
import com.example.healthcertification.ListViewSetting.Test_ListVeiwAdapter;
import com.example.healthcertification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
public class MyHealth_SelfDiagnosis extends Fragment implements View.OnClickListener{

    private List<SD_ListViewItem> sd_listViewItems;
    private FloatingActionButton SD_ADD_Btn;
    private ListView SD_ListView;
    private ListView TestListView;
    private TextView GoodWeight_View, Bmi_View, BmiState_View;
    private Test_ListVeiwAdapter test_listVeiwAdapter;
    private Drawable drawable;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference("SelfDiagnosis");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public MyHealth_SelfDiagnosis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myhealth_selfdiagnosis, container, false);

        test_listVeiwAdapter = new Test_ListVeiwAdapter();
        TestListView = (ListView)view.findViewById(R.id.SD_listview1);
        TestListView.setAdapter(test_listVeiwAdapter);
        SD_ADD_Btn = (FloatingActionButton) view.findViewById(R.id.sd_add_btn);
        SD_ADD_Btn.setOnClickListener(this);
        sd_listViewItems = new ArrayList<>();

        onDataChange();
        TestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                CustomDialog_Remove dialog_hc_remove = new CustomDialog_Remove(getContext());
                dialog_hc_remove.HC_Remove_Dialog_Listener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        onDeleteItem(pos);
                        //test_listVeiwAdapter.removeItem(pos);
                        //test_listVeiwAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {

                    }
                });
                dialog_hc_remove.show();
            }
        });




        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sd_add_btn:
                CustonDialog_Test dialog= new CustonDialog_Test(getContext());
                dialog.test_DialogListener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {
                        makeItem(name);
                    }

                    @Override
                    public void onSDClicked(String height, String weight) {
                        //makeItem(height, weight);
                    }
                });
                dialog.show();
                break;
        }
    }
    public void makeItem(String state){
        // 첫 번째 아이템 추가.
        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yy.MM.dd");
        String getTime = simpleDate.format(mDate);
/*        if(name == "good"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_good);
        }
        else if(name == "not bad"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_notbad);
        }
        else if(name == "tired"){
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_tired);
        }
        else{
            drawable = ContextCompat.getDrawable(getContext(),R.drawable.selfdiagnosis_sick);
        }*/

        //drawable 안되면
        //test_listVeiwAdapter.addItem(getTime, name);
        //test_listVeiwAdapter.addItem(getTime, drawable);
        //test_listVeiwAdapter.notifyDataSetChanged();

        pushData(getTime, state);
    }

    private void onDeleteItem(int position){
        final SD_ListViewItem sd_listViewItem = sd_listViewItems.get(position);
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(user.getUid()).child("SelfDiagnosis");

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SD_ListViewItem item = snapshot.getValue(SD_ListViewItem.class);
                    if (sd_listViewItem.getKey().equals(item.getKey())){
                        mReference.child(snapshot.getKey().toString()).removeValue();
                        test_listVeiwAdapter.notifyDataSetInvalidated();
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
        mReference = mDatabase.getReference(user.getUid()).child("SelfDiagnosis");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sd_listViewItems.clear();
                test_listVeiwAdapter.clear();
                for (DataSnapshot namedata : snapshot.getChildren()){
                    sd_listViewItems.add(namedata.getValue(SD_ListViewItem.class));
                }
                for (int i = 0; i<sd_listViewItems.size(); i++){
                    SD_ListViewItem sd_listViewItem = (SD_ListViewItem)sd_listViewItems.get(i);
                    test_listVeiwAdapter.addItem(sd_listViewItem);
                }
                test_listVeiwAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pushData(String getTime, String state){
        mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference(user.getUid()).child("SelfDiagnosis").push();

        SD_ListViewItem sd_listViewItem = new SD_ListViewItem();

        sd_listViewItem.setKey(mReference.getKey());
        sd_listViewItem.setDate(getTime);
        sd_listViewItem.setState(state);
        mReference.setValue(sd_listViewItem);
    }
}
