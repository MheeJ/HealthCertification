package com.example.healthcertification.ui.First_Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Fragment1_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Fragment1_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("36.5°C");
    }

    public LiveData<String> getText() {
        return mText;
    }
}