package com.example.mater_electronic.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mater_electronic.models.account.Account;

@Database(entities = {Account.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class AccountDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "account.db";
    private static  AccountDatabase instance;
    public static synchronized  AccountDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AccountDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract AccountDAO accountDAO();
}
