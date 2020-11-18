package com.example.healthcertification.ListViewSetting;

public class Temp_ListViewItem {

    private String dateStr ;
    private String tempStr;
    private String timeStr;
    private String stateStr ;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setTime(String time){timeStr = time;}
    public void setTemp(String temp){ tempStr = temp; }
    public void setState(String state) {
        stateStr = state ;
    }

    public String getDate() {
        return this.dateStr ;
    }
    public String getTime(){return this.timeStr;}
    public String getTemp(){
        return this.tempStr;
    }
    public String getState() {
        return this.stateStr ;
    }

}
