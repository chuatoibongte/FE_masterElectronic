package com.example.mater_electronic.ui.navigation.my_cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mater_electronic.models.ProductItem;
import com.example.mater_electronic.models.cart.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyCartViewModel extends ViewModel {
    private MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());

    public MyCartViewModel() {
        // lấy list các sản phẩm tại đây (thông qua repository)
        List<CartItem> listItems = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            listItems.add(new CartItem("https://res.cloudinary.com/dvtcbryg5/image/upload/v1746154983/ElectronicMaster/ElectronicImages/kt6zkcwyzas9e7nrcvem.jpg", "Sản phẩm " + i, 100.0 * (i + 1), 1, "main category"));
        }
        cartItems.setValue(listItems);
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }
}