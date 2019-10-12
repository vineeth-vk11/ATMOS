package com.example.atmos.ui.campusmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CampusMapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CampusMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is CampusMap fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}