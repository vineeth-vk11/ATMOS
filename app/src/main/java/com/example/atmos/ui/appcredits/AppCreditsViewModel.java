package com.example.atmos.ui.appcredits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AppCreditsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AppCreditsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AppCredits fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}