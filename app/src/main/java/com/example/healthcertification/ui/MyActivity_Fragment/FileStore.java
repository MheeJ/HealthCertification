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
    private ArrayList<LatLng> mark_latlng = new ArrayList<LatLng>();
    private ArrayList<String> mark_time = new ArrayList<String>();
    private long linenumber;
    private String lastString;
    private ArrayList<String> encryptline = new ArrayList<String>();

    public void Writefile(String input_text, String filename, boolean save) {
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
            FileOutputStream fos = new FileOutputStream(foldername+"/"+ filename + ".txt", save);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            if(save)
                writer.write(input_text + "\n" + CurrentTime() + "\n");
            else
                writer.write(input_text);
            writer.flush();

            writer.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<LatLng> Readfile(String input_day){
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
        mark_latlng = new ArrayList<LatLng>();
        mark_time = new ArrayList<String>();

        try{
            InputStream is = new FileInputStream(foldername + "/" + input_day + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null) {
                if (count % 3 == 0) {
                    latitude = Double.parseDouble(line);
                    delay_latitude = Double.parseDouble(String.format("%.4f", latitude));
                } else if (count % 3 == 1) {
                    longitude = Double.parseDouble(line);
                    delay_longitude = Double.parseDouble(String.format("%.4f", longitude));
                    latLng = new LatLng(latitude, longitude);
                    delay_latLng = new LatLng(delay_latitude, delay_longitude);
                } else if (count % 3 == 2) {
                    if (delay_previous_latLng == null) {
                        delay_previous_latLng = delay_latLng;
                    } else if ((delay_previous_latLng.latitude == delay_latLng.latitude) && (delay_previous_latLng.longitude == delay_latLng.longitude) && (delay_startTime.equals("측정시작"))) {
                        delay_startTime = line;
                        delay_endTime = "측정끝";
                    } else if ((delay_previous_latLng.latitude != delay_latLng.latitude) || (delay_previous_latLng.longitude != delay_latLng.longitude)) {
                        if (delay_endTime.equals("측정끝")) {
                            delay_endTime = line;
                            SimpleDateFormat transFormat = new SimpleDateFormat("a hh:mm:ss");
                            Date delay_current_time = transFormat.parse(delay_endTime);
                            Date delay_previous_time = transFormat.parse(delay_startTime);
                            long duration = delay_current_time.getTime() - delay_previous_time.getTime(); // 글이 올라온시간,현재시간비교
                            long min = duration / 60000;
                            if (min >= 3) {
                                mark_latlng.add(arrayPoints.get((count / 3)-2));
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

    public LatLng Recentlocation(String filename){
        int count = 0;
        LatLng recentlocation = null;
        double latitude = 0;
        double longitude = 0;

        try{
            InputStream is = new FileInputStream(foldername + "/" + filename + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                if(count%3 == 0){
                    latitude = Double.parseDouble(line);
                }else if(count%3 == 1){
                    longitude = Double.parseDouble(line);
                }else if (count%3 ==2){
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

    public String ReadCalory(String filename, boolean day){
        String result = null;
        try{
            InputStream is = new FileInputStream(foldername + "/" + filename + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                if(day)
                    return line;
                result = line;
            }
            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public void Delete(String filename){
        File file = new File(foldername + "/" + filename + ".txt");
        if(file.exists()){
            file.delete();
        }
    }

    public void CreateEncryptionfile(String input_data, String date, boolean encrypt) throws NoSuchAlgorithmException {
        String encryptString;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input_data.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        encryptString = sb.toString();
        try {
            //파일 output stream 생성
            FileOutputStream fos = new FileOutputStream(foldername + "/EncrytionLog" + date+".txt", true);
            //파일쓰기
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            if(encrypt)
                writer.write(encryptString+"\n");
            else
                writer.write(input_data+"\n");
            writer.flush();

            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadEncryptionfile(String date){
        try{
            encryptline = new ArrayList<String>();
            linenumber = 0;
            lastString = null;
            InputStream is = new FileInputStream(foldername + "/EncrytionLog" + date+".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line="";
            while((line=reader.readLine())!=null){
                linenumber++;
                lastString = line;
                encryptline.add(line);
            }
            reader.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getEncryptline(){
        return encryptline;
    }

//    public int ComapareLocation(String date){
//        int count = 0;
//        int comparetime = 0;
//        ArrayList<String> otherencryptline = new ArrayList<String>();
//        ReadEncryptionfile(date);
//        try{
//            InputStream is = new FileInputStream(foldername + "/OtherEncrytionLog" + date+".txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            String line="";
//            while((line=reader.readLine())!=null){
//                otherencryptline.add(line);
//            }
//            reader.close();
//            is.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        count = (encryptline.size()<otherencryptline.size())?encryptline.size():otherencryptline.size();
//        for(int i = 0; i < count ; i++){
//            if(encryptline.get(i).equals(otherencryptline.get(i))){
//                comparetime++;
//            }
//        }
//        return comparetime;
//    }



    public long getLinenumber() {
        return linenumber;
    }

    public String getLastString(){
        return lastString;
    }

    public ArrayList<LatLng> makerLocation(){
        return mark_latlng;
    }

    public ArrayList<String> makerTime(){
        return mark_time;
    }

    private String CurrentTime() {
        Date today = new Date();
        SimpleDateFormat time = new SimpleDateFormat("a hh:mm:ss");
        return time.format(today);
    }
}