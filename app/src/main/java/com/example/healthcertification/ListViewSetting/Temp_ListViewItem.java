package com.example.healthcertification.ListViewSetting;

public class Temp_ListViewItem {

    private String dateStr ;
    //private String tempStr;
    private double temp;
    private String timeStr;
    private String stateStr ;
    private String Key;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setTime(String time){timeStr = time ; }
    public void setTemp(double tempdouble){ temp = tempdouble; }
    public void setState(String state) {
        stateStr = state ;
    }
    public void setKey(String mykey){Key = mykey ; }

    public String getDate() {return this.dateStr ; }
    public String getTime(){return this.timeStr ; }
    public double getTemp(){return this.temp ; }
    public String getState() {return this.stateStr ; }
    public String getKey(){return  this.Key ; }

}
