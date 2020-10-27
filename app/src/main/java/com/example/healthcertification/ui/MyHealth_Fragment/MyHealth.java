package com.example.healthcertification.ui.MyHealth_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.healthcertification.R;
import com.google.android.material.tabs.TabLayout;

public class MyHealth extends Fragment {


    ViewPager viewPager;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_myhealth, container, false);
        viewPager = myFragment.findViewById(R.id.view_pager1);
        tabLayout = myFragment.findViewById(R.id.tabs1);
        return myFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewpager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewpager(ViewPager viewPager) {
        MyHealth_SectionPagerAdapter adapter = new MyHealth_SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new MyHealth_Temperature(),"체온");
        adapter.addFragment(new MyHealth_HealthCalculation(),"키/몸무게");
        adapter.addFragment(new MyHealth_Medicine(),"약");
        adapter.addFragment(new MyHealth_SelfDiagnosis(),"자가진단");
        viewPager.setAdapter(adapter);
    }
}
