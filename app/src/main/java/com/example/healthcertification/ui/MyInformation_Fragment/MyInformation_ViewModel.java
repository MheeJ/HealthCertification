package com.example.healthcertification.ui.MyInformation_Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyInformation_ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyInformation_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("36°C");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
