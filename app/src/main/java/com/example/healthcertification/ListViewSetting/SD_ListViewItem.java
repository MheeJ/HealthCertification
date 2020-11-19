package com.example.healthcertification.ListViewSetting;

public class SD_ListViewItem {
    private String dateStr ;
    private String stateStr;


    public void SD_setDate(String date) {
        dateStr = date ;
    }
    public void SD_setState(String state) {
        stateStr = state ;
    }

    public String SD_getDate() {
        return this.dateStr ;
    }
    public String SD_getState() {
        return this.stateStr ;
    }
}
