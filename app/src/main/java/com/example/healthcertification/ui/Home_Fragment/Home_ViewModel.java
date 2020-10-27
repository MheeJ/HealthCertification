package com.example.healthcertification.ui.Home_Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Home_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Home_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("36.5°C");
    }

    public LiveData<String> getText() {
        return mText;
    }
}