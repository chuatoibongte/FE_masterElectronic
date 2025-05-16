package com.example.mater_electronic;

import android.app.Application;

import com.example.mater_electronic.localdata.DataLocalManager;

// initialize things that used in entire app
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize DataLocalManager
        DataLocalManager.init(getApplicationContext());
    }
}
