package com.example.healthcertification.ui.MyActivity_Fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

public class LocationTracker{
    private static final String TAG = "GPS ";
    private Context mContext = null;
    private LocationManager mLocationManager = null;
    public double dbLat, dbLng;

    public LocationTracker(Context context){
        this.mContext = context;
        if(this.mContext != null){
// 1. LocationManager 객체 생성
            this.mLocationManager = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    // 2. LocationListener에 해당 프로바이더 등록(GPS, NETWORK)
    public LocationListener [] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    // 3. LocationListener 리스너 설정
    private class LocationListener implements android.location.LocationListener{
        String strNowLocationLatLngInfo;
        Location mLastLocation;
        boolean mValid = false;
        String mProvider;
        int count = 0;

        public LocationListener(String provider){
            mProvider = provider;
            mLastLocation = new Location(mProvider);
        }

        // 4 . 현재 자신의 위치가 바뀔 때마다 업데이트 되는 내용. (난 단순히 위,경도를 출력)
        //Called when the location has changed.
        public void onLocationChanged(Location newLocation){
            FileStore fileStore = new FileStore(mContext);
            if (newLocation.getLatitude() == 0.0 && newLocation.getLongitude() == 0.0){
                // Hack to filter out 0.0,0.0 locations
                // MainActivity(Main Thread)에서 Context를 넘겨 받았으니, 실행이된다.
                Toast.makeText(mContext, "Try again", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newLocation != null){
                ///if(newLocation.getTime() == 0) newLocation.setTime(System.currentTimeMillis());
                newLocation.setTime(System.currentTimeMillis());

                Toast.makeText(mContext, "위도:"+ String.valueOf(newLocation.getLatitude()) +
                                " 경도: " + String.valueOf(newLocation.getLongitude()),
                        Toast.LENGTH_SHORT).show();
                    fileStore.Writefile(String.valueOf(newLocation.getLatitude()) + "\n" + String.valueOf(newLocation.getLongitude()));

                if(newLocation.getLatitude() != 0.0 && newLocation.getLongitude() != 0.0 ){
                    strNowLocationLatLngInfo = "GPS Lat:" +
                            String.valueOf(newLocation.getLatitude()) + " Lng:" +
                            String.valueOf(newLocation.getLongitude());
                    //MainActivity.dbLatitude = newLocation.getLatitude();
                    dbLat = newLocation.getLatitude();
                    dbLng = newLocation.getLongitude();
                }else{
                    strNowLocationLatLngInfo = "Not available";
                    Toast.makeText(mContext, strNowLocationLatLngInfo,
                            Toast.LENGTH_SHORT).show();
                }
            }
            mLastLocation.set(newLocation);
            mValid = true;
        }

        // Called when the provider is ㄷabled by the user.
        public void onProviderEnabled(String provider) {
        }

        // Called when the provider is disabled by the user.
        public void onProviderDisabled(String provider){
            mValid = false;
        }

        // Called when the provider status changes.
        public void onStatusChanged(String provider, int status, Bundle extras){
            if (status == LocationProvider.OUT_OF_SERVICE){
                mValid = false;
            }
        }

        public Location current(){
            return mValid ? mLastLocation : null;
        }
    };



    public void startLocationReceiving(){
        if (this.mLocationManager != null){
            try{
// 5-1. 지정된 provider, 시간, 거리마다 해당 listener에 request됨.(결국, 이 메소드로 위치정보얻기가 실행됨)
                this.mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        2000,
                        20,
                        this.mLocationListeners[1] );
            }catch (java.lang.SecurityException ex){
            }catch (IllegalArgumentException ex){
            }

            try{
                this.mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        20,
                        this.mLocationListeners[0]);
            }catch (java.lang.SecurityException ex){
            }catch (IllegalArgumentException ex){
            }
        }
    }

    public void stopLocationReceiving() {
        if (this.mLocationManager != null) {
            for (int i = 0; i < this.mLocationListeners.length; i++){
                try{
                    this.mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                }
            }
        }
    }

    public Location getCurrentLocation() {
        Location l = null;
        for (int i = 0; i < this.mLocationListeners.length; i++) {
            l = this.mLocationListeners[i].current();
            if (l != null){
                break;
            }
        }
        return l;
    }

}