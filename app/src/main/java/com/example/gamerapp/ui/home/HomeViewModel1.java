package com.example.gamerapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel1 extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel1() {
        mText = new MutableLiveData<>();
        mText.setValue("* "+"List Of Games"+" *");
    }

    public LiveData<String> getText() {
        return mText;
    }
}