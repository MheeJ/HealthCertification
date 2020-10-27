package com.example.healthcertification.ui.MyActivity_Fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyActivity_ViewModel extends ViewModel {

 /*   private MutableLiveData<String> mText;

    public Fragment3_ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
*/
   private final MutableLiveData<String> liveData = new MutableLiveData<>();
   private final MutableLiveData<Double> liveData_latitude = new MutableLiveData<>();
   private final MutableLiveData<Double> liveData_longitude = new MutableLiveData<>();

   public LiveData<String> getLiveData(){
       return liveData;
   }

   public void setLiveData(String str){
       liveData.setValue(str);
   }


    public LiveData<Double> getLatitudeData(){
        return liveData_latitude;
    }

    public void setLatitudeData(Double latitude){
        liveData_latitude.setValue(latitude);
    }

    public LiveData<Double> getLogitudeData(){
        return liveData_latitude;
    }

    public void setLogitudeData(Double logitude){
        liveData_longitude.setValue(logitude);
    }

}