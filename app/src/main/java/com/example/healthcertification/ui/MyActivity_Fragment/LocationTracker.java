package com.example.healthcertification.ui.MyActivity_Fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationTracker{
    private static final String TAG = "GPS ";
    private Context mContext = null;
    private LocationManager mLocationManager = null;
    public double dbLat, dbLng;

    private boolean first_step = false;
    private boolean second_step = false;
    private boolean third_step = false;
    private boolean forth_step = false;
    private boolean fifth_step = false;
    private boolean sixth_step = false;
    private boolean seventh_step = false;
    private String Starttime = null;
    private String Endtime = null;
    private float testCount = 1;

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

        public LocationListener(String provider){
            mProvider = provider;
            mLastLocation = new Location(mProvider);
        }

        // 4 . 현재 자신의 위치가 바뀔 때마다 업데이트 되는 내용. (난 단순히 위,경도를 출력)
        //Called when the location has changed.
        public void onLocationChanged(Location newLocation){
            FileStore fileStore = new FileStore();
            if (newLocation.getLatitude() == 0.0 && newLocation.getLongitude() == 0.0){
                // Hack to filter out 0.0,0.0 locations
                // MainActivity(Main Thread)에서 Context를 넘겨 받았으니, 실행이된다.
                Toast.makeText(mContext, "Try again", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newLocation != null){
                ///if(newLocation.getTime() == 0) newLocation.setTime(System.currentTimeMillis());
                newLocation.setTime(System.currentTimeMillis());

                if(newLocation.getLatitude() != 0.0 && newLocation.getLongitude() != 0.0 ){
                    Toast.makeText(mContext, "위도:"+ String.valueOf(newLocation.getLatitude()) +
                                    " 경도: " + String.valueOf(newLocation.getLongitude() + " 속도:" + String.valueOf(newLocation.getSpeed())),
                            Toast.LENGTH_SHORT).show();
                    fileStore.Writefile(String.valueOf(newLocation.getLatitude()) + "\n" + String.valueOf(newLocation.getLongitude()), CurrentDate(), true);
                }else{
                    strNowLocationLatLngInfo = "Not available";
                    Toast.makeText(mContext, strNowLocationLatLngInfo,
                            Toast.LENGTH_SHORT).show();
                }

                testCount = (float) (testCount+0.5);
                try {
                    Calory(newLocation.getSpeed());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                if(!fileStore.ReadCalory("calory", true).equals(CurrentDate())){
//                    fileStore.Delete("calory");
//                }
                if(testCount == 4)
                    testCount=1;
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


    public void startLocationReceiving(){
        if (this.mLocationManager != null){
            try{
// 5-1. 지정된 provider, 시간, 거리마다 해당 listener에 request됨.(결국, 이 메소드로 위치정보얻기가 실행됨)
                this.mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        5000,
                        20,
                        this.mLocationListeners[1] );
            }catch (java.lang.SecurityException ex){
            }catch (IllegalArgumentException ex){
            }

            try{
                this.mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        5000,
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

    private void Calory(float speed) throws ParseException {
        SimpleDateFormat transFormat = new SimpleDateFormat("a hh:mm:ss");
        long duration = 0;
        long weight = 80;
        long calory = 0;
        FileStore fileStore = new FileStore();

        if(speed<=1.57&&speed>1) {
            first_step = true;
            if(Starttime == null && second_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(second_step || third_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=1.78&&speed>1.57) {
            second_step = true;
            if(Starttime == null && first_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || third_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=2.23&&speed>1.78) {
            third_step = true;
            if(Starttime == null && first_step == false && second_step == false && forth_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || forth_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=2.68&&speed>2.23) {
            forth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && fifth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || fifth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=3.05&&speed>2.68) {
            fifth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && sixth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || sixth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=3.62&&speed>3.05) {
            sixth_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && fifth_step == false && seventh_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || fifth_step || seventh_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(seventh_step){
                    calory = (long) (weight * duration * 14 * 17.5/60000000);
                    seventh_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if(speed<=4.17&&speed>3.62) {
            seventh_step = true;
            if(Starttime == null && first_step == false && second_step == false && third_step == false && forth_step == false && fifth_step == false && sixth_step == false)
                Starttime = CurrentTime();
            else if(first_step || second_step || third_step || forth_step || fifth_step || sixth_step){
                Endtime = CurrentTime();
                duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
                if(first_step){
                    calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                    first_step = false;
                }else if(second_step){
                    calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                    second_step = false;
                }else if(third_step){
                    calory = (long) (weight * duration * 5 * 17.5/60000000);
                    third_step = false;
                }else if(forth_step){
                    calory = (long) (weight * duration * 8 * 17.5/60000000);
                    forth_step = false;
                }else if(fifth_step){
                    calory = (long) (weight * duration * 10 * 17.5/60000000);
                    fifth_step = false;
                }else if(sixth_step){
                    calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                    sixth_step = false;
                }
                if(fileStore.ReadCalory("calory", false).equals(""))
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory),"calory",false);
                else
                    fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
                Endtime = null;
                Starttime = CurrentTime();
            }
        }else if((speed<=1.57||speed>4.17)&&Starttime!=null){
            Endtime = CurrentTime();
            duration = transFormat.parse(Endtime).getTime()-transFormat.parse(Starttime).getTime();
            if(first_step){
                calory = (long) (weight * duration * 2.7 * 17.5/60000000);
                first_step = false;
            }else if(second_step){
                calory = (long) (weight * duration * 3.8 * 17.5/60000000);
                second_step = false;
            }else if(third_step){
                calory = (long) (weight * duration * 5 * 17.5/60000000);
                third_step = false;
            }else if(forth_step){
                calory = (long) (weight * duration * 8 * 17.5/60000000);
                forth_step = false;
            }else if(fifth_step){
                calory = (long) (weight * duration * 10 * 17.5/60000000);
                sixth_step = false;
            }else if(sixth_step){
                calory = (long) (weight * duration * 12.3 * 17.5/60000000);
                sixth_step = false;
            }else if(seventh_step){
                calory = (long) (weight * duration * 14 * 17.5/60000000);
                seventh_step = false;
            }
            fileStore.Writefile(CurrentDate() + "\n" + String.valueOf(calory + Long.parseLong(fileStore.ReadCalory("calory", false))),"calory",false);
            Endtime = null;
            Starttime = null;
        }
    }
}