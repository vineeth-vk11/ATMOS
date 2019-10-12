package com.example.atmos.ui.reachus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReachUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReachUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ReachUs fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}