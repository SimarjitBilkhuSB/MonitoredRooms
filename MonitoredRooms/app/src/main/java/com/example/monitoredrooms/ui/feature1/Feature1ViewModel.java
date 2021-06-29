package com.example.monitoredrooms.ui.feature1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Feature1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Feature1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is feature 1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}