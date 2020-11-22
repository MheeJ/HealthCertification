package com.example.healthcertification.ui.MyInformation_Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcertification.CustomDialog.CustomDialog_Info_Setting;
import com.example.healthcertification.CustomDialog.CustomDialog_Listener;
import com.example.healthcertification.Login.Login;
import com.example.healthcertification.R;
import com.google.firebase.auth.FirebaseAuth;

public class MyInformation extends Fragment implements View.OnClickListener {
    private MyInformation_ViewModel myInformation_viewModel;
    private ImageView Information_Setting_btn,Info_Logout_btn;
    private String Info_Name;
    private String Info_Birth;
    private TextView TextView_Name, TextView_Birth, TextView_Gender;
    private FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myInformation_viewModel =
                ViewModelProviders.of(this).get(MyInformation_ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinformation, container, false);
        TextView_Name = root.findViewById(R.id.info_name);
        TextView_Birth = root.findViewById(R.id.info_birth);
        TextView_Gender = root.findViewById(R.id.info_gender);
        Information_Setting_btn = (ImageView)root.findViewById(R.id.information_setting_btn);
        Information_Setting_btn.setOnClickListener(this);
        Info_Logout_btn = (ImageView)root.findViewById(R.id.info_logout_btn);
        Info_Logout_btn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.information_setting_btn:
                CustomDialog_Info_Setting dialog = new CustomDialog_Info_Setting(getContext());
                dialog.Info_DialogListener(new CustomDialog_Listener() {
                    @Override
                    public void onPositiveClicked(String name) {

                    }

                    @Override
                    public void onSDClicked(String height, String weight) {
                        InfoMakeItem(height, weight);
                    }
                });
                dialog.show();
                break;
            case R.id.info_logout_btn:
                signOut();
                break;
        }
    }
    public void InfoMakeItem(String data, String gender){
        String[] split = data.split("#");
        Info_Name = split[0];
        Info_Birth = split[1];
        TextView_Name.setText(Info_Name);
        TextView_Birth.setText(Info_Birth);
        TextView_Gender.setText(gender);

    }

    private void signOut() {
        // Firebase sign out
        mAuth.getInstance().signOut();
        Intent intent1 = new Intent(getActivity(), Login.class);
        startActivity(intent1);
    }

}
