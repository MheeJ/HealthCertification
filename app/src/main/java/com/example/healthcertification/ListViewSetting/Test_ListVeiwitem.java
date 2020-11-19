package com.example.healthcertification.ListViewSetting;

import android.graphics.drawable.Drawable;

public class Test_ListVeiwitem {

    private String dateStr ;
    private String stateStr;
    private Drawable drawableIcon;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setState(String state){ stateStr = state; }
    public void setIcon(Drawable icon){drawableIcon = icon;}


    public String getDate() {
        return this.dateStr ;
    }
    public String getState(){
        return this.stateStr;
    }
    public Drawable getIcon() {return this.drawableIcon;}
}
