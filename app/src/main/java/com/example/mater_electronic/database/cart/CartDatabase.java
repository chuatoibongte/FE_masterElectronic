package com.example.mater_electronic.database.cart;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.mater_electronic.database.Converters;
import com.example.mater_electronic.models.cart.CartItem;

@Database(entities = {CartItem.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class CartDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cart.db";
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CartDAO cartDAO();
}