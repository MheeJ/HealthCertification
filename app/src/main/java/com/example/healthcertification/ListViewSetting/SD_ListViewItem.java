package com.example.healthcertification.ListViewSetting;

public class SD_ListViewItem {
    private String dateStr ;
    private String stateStr;
    private String key;

    public void setDate(String date) {
        dateStr = date ;
    }
    public void setState(String state){
        stateStr = state;
    }
    public void setKey(String mykey) {key = mykey;}

    public String getDate() {
        return this.dateStr ;
    }
    public String getState(){
        return this.stateStr;
    }
    public String getKey() {return this.key;}


}
