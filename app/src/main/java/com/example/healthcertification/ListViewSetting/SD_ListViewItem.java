package com.example.healthcertification.ListViewSetting;

public class SD_ListViewItem {
    private String dateStr ;
    private String stateStr;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setState(String state){
        stateStr = state;
    }


    public String getDate() {
        return this.dateStr ;
    }
    public String getState(){
        return this.stateStr;
    }
}