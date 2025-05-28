package com.example.mater_electronic.localdata;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context mContext;
    public MySharedPreferences(Context context) {
        mContext = context;
    }
    public void putBooleanValue(String key, boolean value) {
        // mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public boolean getBooleanValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
    public void putStringValue(String key, String value) {
        // mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    public void putStringSetValue(String key, Set<String> values) {
        // mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }
    public Set<String> getStringSetValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> valuesDefault = new HashSet<>();
        return sharedPreferences.getStringSet(key, valuesDefault);
    }
}
