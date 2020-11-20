package com.example.healthcertification.ui.MyActivity_Fragment;

import android.content.Context;
import android.os.Environment;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileStore{

    private final static String foldername = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.example.healthcertification";
    private final static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.example.healthcertification/LocationLog.txt";
    private static final String LOCATION_LOG_FILE_NAME = "LocationLog.txt";
    private static final String ENCRYPT_FILE_NAME = "EncryptLog.txt";
    private Context mContext = null;
    ArrayList<LatLng> mark_latlng = new ArrayList<LatLng>();
    ArrayList<String> mark_time = new ArrayList<String>();

    public FileStore(Context context){
        mContext = context;
    }

    public void Writefile(String input_text) {
        if (input_text == null || input_text.equals("")) {
            return;
        }
        try{
            File dir = new File (foldername);
            //디렉토리 폴더가 없으면 생성함
            if(!dir.exists()){
                dir.mkdir();
            }
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+ LOCATION_LOG_FILE_NAME, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(CurrentDate() + "\n" + input_text + "\n" + CurrentDTime() + "\n");
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<LatLng> Readfile(){
        int count = 0;
        ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
        LatLng latLng = null;
        LatLng delay_previous_latLng = null;
        double latitude = 0;
        double longitude = 0;
        LatLng delay_latLng = null;
        String delay_startTime = "측정시작";
        String delay_endTime = "null";
        double delay_latitude = 0;
        double delay_longitude = 0;

        try{
            InputStream is = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                if(count%4 == 1){
                    latitude = Double.parseDouble(line);
                    delay_latitude = Double.parseDouble(String.format("%.3f",latitude));
                }else if(count%4 == 2){
                    longitude = Double.parseDouble(line);
                    delay_longitude = Double.parseDouble(String.format("%.3f",longitude));
                    latLng = new LatLng(latitude, longitude);
                    delay_latLng = new LatLng(delay_latitude, delay_longitude);
                }else if (count%4 ==3){
                    if(delay_previous_latLng == null){
                        delay_previous_latLng = delay_latLng;
                    }else if((delay_previous_latLng.latitude == delay_latLng.latitude) && (delay_previous_latLng.longitude == delay_latLng.longitude) && (delay_startTime.equals("측정시작"))){
                        delay_startTime = line;
                        delay_endTime = "측정끝";
                    }else if((delay_previous_latLng.latitude != delay_latLng.latitude) || (delay_previous_latLng.longitude != delay_latLng.longitude)){
                        if(delay_endTime.equals("측정끝")) {
                            delay_endTime = line;
                            SimpleDateFormat transFormat = new SimpleDateFormat("a hh:mm:ss");
                            Date delay_current_time = transFormat.parse(delay_endTime);
                            Date delay_previous_time = transFormat.parse(delay_startTime);
                            long duration = delay_current_time.getTime() - delay_previous_time.getTime(); // 글이 올라온시간,현재시간비교
                            long min = duration/60000;
                            if(min>=3) {
                                mark_latlng.add(arrayPoints.get((count/4)-2));
                                mark_time.add(transFormat.format(delay_previous_time) + " ~ " + transFormat.format(delay_current_time));
                            }
                        }
                        delay_previous_latLng = null;
                        delay_startTime = "측정시작";
                        delay_endTime = "null";
                    }
                    arrayPoints.add(latLng);
                }
                count++;
            }
            reader.close();
            is.close();
        }catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return arrayPoints;
    }

    public LatLng Recentlocation(){
        int count = 0;
        LatLng recentlocation = null;
        double latitude = 0;
        double longitude = 0;

        try{
            InputStream is = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                if(count%4 == 1){
                    latitude = Double.parseDouble(line);
                }else if(count%4 == 2){
                    longitude = Double.parseDouble(line);
                }else if (count%4 ==3){
                    recentlocation = new LatLng(latitude, longitude);
                }
                count++;
            }
            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return recentlocation;
    }

    public ArrayList<LatLng> makerLocation(){
        return mark_latlng;
    }

    public ArrayList<String> makerTime(){
        return mark_time;
    }

    private String CurrentDate() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        return date.format(today);
    }

    private String CurrentDTime() {
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
        return time.format(today);
    }

    public void Delete(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }
    }

    public void Encryptfile() throws NoSuchAlgorithmException {
        StringBuffer strBuffer = new StringBuffer();
        String encryptString;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try{
            InputStream is = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                md.update(line.getBytes());
                byte byteData[] = md.digest();
                StringBuffer sb = new StringBuffer();
                for(int i = 0; i<byteData.length;i++)
                    sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
                encryptString = sb.toString();

                strBuffer.append(encryptString+"\n");
            }

            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        Delete(foldername+"/"+ ENCRYPT_FILE_NAME);
        try{
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername+"/"+ ENCRYPT_FILE_NAME, true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(strBuffer.toString());
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}