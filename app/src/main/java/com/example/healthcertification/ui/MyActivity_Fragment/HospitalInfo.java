package com.example.healthcertification.ui.MyActivity_Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class HospitalInfo implements ClusterItem {
    private String Name;
    private String Tel;
    private String StartTime;
    private String EndTime;
    private Double Latitude;
    private Double Longitude;

    public HospitalInfo(String name, String tel, String startTime, String endTime, Double latitude, Double longitude){
        Name = name;
        Tel = tel;
        StartTime = startTime;
        EndTime = endTime;
        Latitude = latitude;
        Longitude = longitude;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return new LatLng(Latitude, Longitude);
    }

    @Nullable
    @Override
    public String getTitle() {
        return Name;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return "전화번호 : " + Tel + "\n진료시간 : " + StartTime + " ~ " + EndTime;
    }
}
