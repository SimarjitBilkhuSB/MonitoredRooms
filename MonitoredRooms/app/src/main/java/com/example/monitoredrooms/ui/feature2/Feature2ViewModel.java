package com.example.monitoredrooms.ui.feature2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Feature2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Feature2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is feature 2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}