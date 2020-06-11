package com.example.gamerapp.ui.ReviewHistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewHistoryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReviewHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}