package com.example.mater_electronic.localdata;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataLocalManager {
    private static final String PREF_FIRST_INSTALL = "PREF_FIRST_INSTALL";
    private static final String SEARCH_HISTORY = "SEARCH_HISTORY";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance() {
        if (instance == null) instance = new DataLocalManager();
        return instance;
    }
    public static void setFirstInstalled(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBooleanValue(PREF_FIRST_INSTALL, value);
    }

    public static boolean getFirstInstalled() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(PREF_FIRST_INSTALL);
    }

    public static void setSearchHistory(String value) {
        Set<String> setStringHistory = DataLocalManager.getInstance().mySharedPreferences.getStringSetValue(SEARCH_HISTORY);
        if (setStringHistory == null)
        {
            setStringHistory = new HashSet<>();
        }
        setStringHistory.add(value);
        DataLocalManager.getInstance().mySharedPreferences.putStringSetValue(SEARCH_HISTORY, setStringHistory);
    }

    public static List<String> getSearchHistory() {
        Set<String> setStringHistory = DataLocalManager.getInstance().mySharedPreferences.getStringSetValue(SEARCH_HISTORY);
        if (setStringHistory == null)
        {
            setStringHistory = new HashSet<>();
        }
        return new ArrayList<>(setStringHistory);
    }
    public static void removeSearchHistory(int position) {
        Set<String> setStringHistory = DataLocalManager.getInstance().mySharedPreferences.getStringSetValue(SEARCH_HISTORY);
        if (setStringHistory == null)
        {
            setStringHistory = new HashSet<>();
        }
        List<String> listStringHistory = new ArrayList<>(setStringHistory);
        listStringHistory.remove(position);
        setStringHistory = new HashSet<>(listStringHistory);
        DataLocalManager.getInstance().mySharedPreferences.putStringSetValue(SEARCH_HISTORY, setStringHistory);
    }
}
