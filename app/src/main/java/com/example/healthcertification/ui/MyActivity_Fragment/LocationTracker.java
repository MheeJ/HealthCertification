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

import com.example.healthcertification.ListViewSetting.HC_ListViewItem;
import com.example.healthcertification.ListViewSetting.M_ListViewItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class LocationTracker{

    private static final String TAG = "googlemap_example";
    private static final int UPDATE_INTERVAL_MS = 15000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 15000; // 0.5초
    private Context mContext = null;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Location location;
    private CaloryCalculate caloryCalculate = new CaloryCalculate();
    private SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public LocationTracker(Context context) {
        this.mContext = context;

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS)
                .setSmallestDisplacement(30);

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
            final FileStore fileStore = new FileStore();
            long countfortoday = 0;
            long countforencryptfile = 0;

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);
//                Toast.makeText(mContext, "위도:" + String.valueOf(location.getLatitude()) +
//                                " 경도: " + String.valueOf(location.getLongitude() + " 속도:" + String.valueOf(location.getSpeed())),
//                        Toast.LENGTH_SHORT).show();
                fileStore.Writefile(String.valueOf(location.getLatitude()) + "\n" + String.valueOf(location.getLongitude()), CurrentDate(), true);

                try {
                    caloryCalculate.Calory(location.getSpeed());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (!CurrentDate().equals(fileStore.ReadCalory("calory", true))) {
                    mDatabase = FirebaseDatabase.getInstance();
                    mReference = mDatabase.getReference(user.getUid()).child("Calory").push();
                    CaloryItem caloryItem = new CaloryItem();
                    caloryItem.setDate(fileStore.ReadCalory("calory", true));
                    caloryItem.setCalory(fileStore.ReadCalory("calory", false));
                    caloryItem.setKey(mReference.getKey());
                    mReference.setValue(caloryItem);
                    fileStore.Writefile(CurrentDate() + "\n" + "0", "calory", false);
                }

                fileStore.ReadEncryptionfile(CurrentDate());
                countforencryptfile = fileStore.getLinenumber();
                try {
                    countfortoday = (time.parse(CurrentTime()).getTime() + 32400000) / 600000;
                    if (countforencryptfile == 0) {
                        fileStore.CreateEncryptionfile(String.format("%.4f", location.getLatitude()) + "\n" + String.format("%.4f", location.getLongitude()), CurrentDate(), true);
                    } else {
                        if ((countfortoday - countforencryptfile) >= 1) {
                            if ((countfortoday - countforencryptfile) != 1) {
                                for (int i = 0; i < (countfortoday - countforencryptfile); i++)
                                    fileStore.CreateEncryptionfile(fileStore.getLastString(), CurrentDate(), false);
                            }
                            fileStore.CreateEncryptionfile(String.format("%.4f", location.getLatitude()) + "\n" + String.format("%.4f", location.getLongitude()), CurrentDate(), true);
                        }
                    }
                } catch (NoSuchAlgorithmException | ParseException e) {
                    e.printStackTrace();
                }


            }
        }


    };

    private String CurrentTime() {
        Date today = new Date();
        return time.format(today);
    }

    private String CurrentDate() {
        Date today = new Date();
        return date.format(today);
    }
}