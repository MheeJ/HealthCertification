package com.example.healthcertification.ListViewSetting;

public class Temp_ListViewItem {

    private String dateStr ;
    private String tempStr;
    private String timeStr;
    private String stateStr ;
    private String Key;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setTime(String time){timeStr = time ; }
    public void setTemp(String temp){ tempStr = temp; }
    public void setState(String state) {
        stateStr = state ;
    }
    public void setKey(String mykey){Key = mykey ; }

    public String getDate() {return this.dateStr ; }
    public String getTime(){return this.timeStr ; }
    public String getTemp(){return this.tempStr ; }
    public String getState() {return this.stateStr ; }
    public String getKey(){return  this.Key ; }

}
