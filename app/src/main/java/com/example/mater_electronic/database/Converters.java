package com.example.mater_electronic.database;

import androidx.room.TypeConverter;

import com.example.mater_electronic.models.account.Address;
import com.example.mater_electronic.models.account.Avatar;
import com.example.mater_electronic.models.cart.CartItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromAvatar(Avatar avatar) {
        return gson.toJson(avatar);
    }

    @TypeConverter
    public static Avatar toAvatar(String avatarString) {
        return gson.fromJson(avatarString, Avatar.class);
    }

    @TypeConverter
    public static String fromAddressList(List<Address> addressList) {
        return gson.toJson(addressList);
    }

    @TypeConverter
    public static List<Address> toAddressList(String addressListString) {
        Type listType = new TypeToken<List<Address>>() {
        }.getType();
        return gson.fromJson(addressListString, listType);
    }

    //    Date converter
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
    // Cart items list converter (if needed)
    @TypeConverter
    public static String fromCartItemList(List<CartItem> cartItems) {
        return gson.toJson(cartItems);
    }

    @TypeConverter
    public static List<CartItem> toCartItemList(String cartItemsString) {
        Type listType = new TypeToken<List<CartItem>>() {
        }.getType();
        return gson.fromJson(cartItemsString, listType);
    }
}
