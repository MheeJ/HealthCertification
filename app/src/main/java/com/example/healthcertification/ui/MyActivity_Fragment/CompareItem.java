package com.example.healthcertification.ui.MyActivity_Fragment;

import java.util.ArrayList;

public class CompareItem {
    private int confirmed;
    private String compare;
    private ArrayList<String> detailCompare;

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirm) {
        confirmed = confirm;
    }

    public ArrayList<String> getDetailCompare() {
        return detailCompare;
    }

    public void setDetailCompare(ArrayList<String> detailCompare) {
        this.detailCompare = detailCompare;
    }
}
