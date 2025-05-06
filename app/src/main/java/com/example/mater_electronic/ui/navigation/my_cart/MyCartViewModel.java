package com.example.mater_electronic.ui.navigation.my_cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCartViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyCartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mycart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}