package com.example.healthcertification.ListViewSetting;

public class HC_ListViewItem {

    private String dateStr ;
    private String heightStr;
    private String weightStr ;
    private String bmiStr ;
    private String stateStr;
    private String key;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setHeight(String height){ heightStr = height; }
    public void setWeight(String weight) {
        weightStr = weight ;
    }
    public void setBmi(String bmi){
        bmiStr = bmi;
    }
    public void setState(String state){
        stateStr = state;
    }
    public void setKey(String key) {this.key = key;}

    public String getDate() {
        return this.dateStr ;
    }
    public String getHeight(){
        return this.heightStr;
    }
    public String getWeight() {
        return this.weightStr ;
    }
    public String getBmi(){
        return this.bmiStr;
    }
    public String getState(){
        return this.stateStr;
    }
    public String getKey() {return key;}

}
