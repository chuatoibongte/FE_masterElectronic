package com.example.mater_electronic.ui.activity.checkout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mater_electronic.database.cart.CartManager;
import com.example.mater_electronic.models.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

public class CheckoutViewModel extends AndroidViewModel {
    private CartManager cartManager;
    private MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }
    public CheckoutViewModel(@NonNull Application application) {
        super(application);
        String userId = getCurrentUserId();
        cartManager = new CartManager(application.getApplicationContext(), userId);
        loadCartItems();
    }
    private void loadCartItems() {
        new Thread(() -> {
            List<CartItem> items = cartManager.getSelectedCartItems();
            cartItems.postValue(items);
        }).start();
    }
    private String getCurrentUserId() {
        SharedPreferences prefs = getApplication().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return prefs.getString("_id", "");
    }
}
