package com.example.healthcertification.ui.MyActivity_Fragment;

import java.util.ArrayList;

public class EncryptedItem {
    private String uid;
    private String date;
    private ArrayList<String> log;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
