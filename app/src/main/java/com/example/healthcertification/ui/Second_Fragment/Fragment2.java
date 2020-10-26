package com.example.healthcertification.ui.Second_Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.healthcertification.R;
import com.google.android.material.tabs.TabLayout;

public class Fragment2 extends Fragment {


    ViewPager viewPager;
    TabLayout tabLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_2, container, false);
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
        SectionPagerAdapter_2 adapter = new SectionPagerAdapter_2(getChildFragmentManager());
        adapter.addFragment(new Fragment2_1(),"체온");
        adapter.addFragment(new Fragment2_2(),"약");
        adapter.addFragment(new Fragment2_3(),"키/몸무게");
        adapter.addFragment(new Fragment2_4(),"자가진단");
        viewPager.setAdapter(adapter);
    }
}
