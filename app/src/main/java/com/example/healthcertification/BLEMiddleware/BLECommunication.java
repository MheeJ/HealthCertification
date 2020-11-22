package com.example.healthcertification.BLEMiddleware;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.healthcertification.MainActivity;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// (1) initialize -> (2)setListener -> (3) create_topic

public class BLECommunication implements BeaconConsumer {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private List<Beacon> beaconList = new ArrayList<>();
    private BLEListener bleListener = null;
    private String strTopic = "";
    private BeaconManager beaconManager;

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            // 비콘이 감지되면 해당 함수가 호출된다. Collection<Beacon> beacons에는 감지된 비콘의 리스트가,
            // region에는 비콘들에 대응하는 Region 객체가 들어온다.
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    beaconList.clear();
                    for (Beacon beacon : beacons) {
                        beaconList.add(beacon);
                    }
                }
            }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    @Override
    public Context getApplicationContext() {
        return null;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return false;
    }

    public void createTopic(String topic){
        strTopic = topic;
    }

    public void setBleListener(BLEListener bleListener){
        this.bleListener = bleListener;
    }

    public void initialize(Context context){
        beaconManager = BeaconManager.getInstanceForApplication(context);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
        getPermission(context);
        handler.sendEmptyMessage(0);
    }

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            for(Beacon beacon : beaconList) {
                int major = beacon.getId2().toInt(); //beacon major
                int minor = beacon.getId3().toInt();// beacon minor
                //beacon 의 식별을 위하여 major값으로 확인
                //이곳에 필요한 기능 구현
                String topic = Integer.toHexString(major);
                //topic = hexToAscii(topic.substring(0, 1)) + hexToAscii(topic.substring(2, 3));
                topic = hexToAscii(topic);
                if (strTopic.equals(topic))
                {
                    String temp = Integer.toHexString(minor);
                    StringBuffer buf = new StringBuffer(temp);
                    buf.insert(2, ".");
                    temp = buf.toString();
                    bleListener.onBLEDataAvailable(beacon, temp);
                }
            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    private void getPermission(Context context){
        //beacon 을 활용하려면 블루투스 권한획득(Andoird M버전 이상)
        final MainActivity ma = (MainActivity) context;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("This app needs location access" );
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok,null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        (ma).requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }
}
