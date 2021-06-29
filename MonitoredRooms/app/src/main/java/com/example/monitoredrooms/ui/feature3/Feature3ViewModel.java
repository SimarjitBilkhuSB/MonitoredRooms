package com.example.monitoredrooms.ui.feature3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Feature3ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Feature3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is feature 3 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}