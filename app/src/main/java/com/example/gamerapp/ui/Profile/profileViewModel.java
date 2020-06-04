package com.example.gamerapp.ui.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamerapp.Others.Constants;

public class profileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public profileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Welcome,  "+ Constants.CURRENT_USER+"");
    }

    public LiveData<String> getText() {
        return mText;
    }
}