package com.example.healthcertification.ui.MyActivity_Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class LocationTracker{

    private static final String TAG = "googlemap_example";
    private static final int UPDATE_INTERVAL_MS = 10000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 10000; // 0.5초
    private Context mContext = null;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    private CaloryCalculate caloryCalculate = new CaloryCalculate();


    public LocationTracker(Context context) {
        this.mContext = context;

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS)
                .setSmallestDisplacement(20);

        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    public void start() {
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            FileStore fileStore = new FileStore();

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);
                Toast.makeText(mContext, "위도:" + String.valueOf(location.getLatitude()) +
                                " 경도: " + String.valueOf(location.getLongitude() + " 속도:" + String.valueOf(location.getSpeed())),
                        Toast.LENGTH_SHORT).show();
                fileStore.Writefile(String.valueOf(location.getLatitude()) + "\n" + String.valueOf(location.getLongitude()), CurrentDate(), true);

                try {
                    caloryCalculate.Calory(location.getSpeed());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!fileStore.ReadCalory("calory", true).equals(CurrentDate())){
                    fileStore.Writefile(CurrentDate() + "\n" +"0","calory",false);
                }

//                if(CurrentTime())


            }
        }
    };

    private String CurrentTime() {
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
        return time.format(today);
    }

    private String CurrentDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        return date.format(today);
    }
}